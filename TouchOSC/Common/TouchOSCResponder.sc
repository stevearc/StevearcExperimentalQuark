TouchOSCResponder {
  // FIXME this is kind of gross.
  classvar clientAddr;
  var oscfuncs, children, <isListening=false;
  *new {
    ^super.new.initResponder;
  }
  initResponder {
    oscfuncs = Array.new;
    children = Array.new;
  }
  prAddFunc { |path, action|
    oscfuncs = oscfuncs.add(OSCFunc({
      |msg, time, addr, port|
      clientAddr = addr;
      action.value(msg, time, addr, port);
    }, path));
  }
  prAddChild { |responder|
    children = children.add(responder);
    if (isListening) {
      responder.start;
    };
  }
  prClearChildren {
    children.do { |c|
      c.stop;
    };
    children = Array.new;
  }

  sync { |recurse=true, forceUpdate=false|
    if (recurse) {
      children.do { |c|
        c.sync(recurse, forceUpdate);
      };
    };
    if (clientAddr.notNil) {
      this.syncImpl(forceUpdate);
    };
  }
  syncImpl { |forceUpdate=false| }
  start {
    if (this.isListening) {
      this.stop;
    };
    this.prAddFunc('/ping', { |msg, time, addr, port|
      var isStartup = msg[1].asBoolean;
      this.sync(false, isStartup);
    });
    children.do { |c|
      c.start;
    };
    isListening = true;
  }
  stop {
    children.do { |c|
      c.stop;
    };
    oscfuncs.do { |f|
      f.free;
    };
    oscfuncs = Array.new;
    isListening = false;
  }
}
