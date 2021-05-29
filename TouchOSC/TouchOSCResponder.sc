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
  }

  sync {
    children.do { |c|
      c.sync(clientAddr);
    };
    if (clientAddr.notNil) {
      this.syncImpl;
    };
  }
  syncImpl { }
  listen {
    if (this.isListening) {
      this.free;
    };
    this.prAddFunc('/ping', { |msg, time, addr, port|
      this.sync;
    });
    children.do { |c|
      c.listen;
    };
    isListening = true;
  }
  free {
    children.do { |c|
      c.free;
    };
    oscfuncs.do { |f|
      f.free;
    };
    oscfuncs = Array.new;
    isListening = false;
  }
}
