TouchControl : TouchOSCResponder {
  var <store, <>readValue, <path, <>onChange, <>onTouchStart, <>onTouchEnd, <isTouching=false, clientValue;
  *new { |path, store, readValue, onChange, onTouchStart, onTouchEnd|
    ^super.new.initTouchControl(store, readValue, path, onChange, onTouchStart, onTouchEnd);
  }
  *fromStore { |path, store, key, onTouchStart, onTouchEnd|
    var readValue = {store.perform(key)};
    var onChange = {|val| store.perform((key ++ '_').asSymbol, val)};
    ^super.new.initTouchControl(store, readValue, path, onChange, onTouchStart, onTouchEnd);
  }
  initTouchControl {
    |theStore, theReadValue, thePath, onChange, onTouchStart, onTouchEnd|
    store = theStore;
    if (store.notNil) {
      store.addDependant(this);
    };
    readValue = theReadValue;
    path = thePath;
    this.onChange = onChange;
    this.onTouchStart = onTouchStart;
    this.onTouchEnd = onTouchEnd;
  }
  convertFromClient { |value| ^value }
  convertToClient { |value| ^value }
  start {
    super.start;
    this.startImpl;
  }
  startImpl {
    this.prAddFunc(path, { |msg|
      var newval = this.convertFromClient(msg[1]);
      clientValue = newval;
      if (this.onChange.notNil) {
        this.onChange.value(newval);
      };
    });
    this.prAddTouchListener;
    this.sync;
  }
  prAddTouchListener {
    this.prAddFunc(path ++ "/z", { |msg|
      var newTouch = msg[1].asBoolean;
      if (isTouching != newTouch) {
        isTouching = newTouch;
        if (isTouching) {
          if (onTouchStart.notNil) {
            onTouchStart.value;
          };
        } {
          if (onTouchEnd.notNil) {
            onTouchEnd.value;
          };
          this.sync(false, true);
        };
      };
    });
  }
  syncImpl { |forceUpdate|
    // Only update the control when we're not touching it
    if (isTouching.not) {
      var value = readValue.value(store, path);
      if (value != clientValue or: forceUpdate) {
        clientValue = value;
        clientAddr.sendMsg(path, this.convertToClient(value));
      };
    };
  }
  stop {
    super.stop;
    clientValue = nil;
  }
  update { |store, what|
    this.sync(false);
  }
}

TouchControlGrid : TouchOSCResponder {
  *d1 { |path, size, factory|
    var instance = super.new;
    size.do { |i|
      instance.prAddChild(factory.value(path.format(i+1), i));
    };
    ^instance;
  }

  *d2 { |path, dimensions, factory|
    var instance = super.new;
    dimensions[0].do { |row|
      dimensions[1].do { |col|
        instance.prAddChild(factory.value(path.format(row+1, col+1), row, col));
      };
    };
    ^instance;
  }
}

TouchControlLabel : TouchOSCResponder {
  var <store, <readValue, <path, clientValue;
  *new { |path, store, readValue|
    ^super.new.init(path, store, readValue);
  }
  *fromStore { |path, store, key|
    ^super.new.init(path, store, {store.perform(key)});
  }
  init { |thePath, theStore, theReadValue|
    path = thePath;
    store = theStore;
    store.addDependant(this);
    readValue = theReadValue;
  }
  syncImpl { |forceUpdate|
    var value = readValue.value(store, path);
    if (value != clientValue or: forceUpdate) {
      clientValue = value;
      clientAddr.sendMsg(path, value);
    }
  }
  update { |store, what|
    this.sync;
  }
  stop {
    super.stop;
    clientValue = nil;
  }
}

TouchControlToggle : TouchControl {
  convertFromClient { |value|
    ^(value != 0);
  }
  convertToClient { |value|
    ^value.asInteger;
  }
}

TouchControlButton : TouchControl {
  *new { |path, store, onChange, onTouchStart, onTouchEnd|
    ^super.new(path, store, nil, onChange, onTouchStart, onTouchEnd);
  }
  convertFromClient { |value|
    ^(value != 0);
  }
  convertToClient { |value|
    ^value.asInteger;
  }
  // Don't send any info to the client
  syncImpl { }
}

TouchControlRange : TouchControl {
  var <>spec;
  *new { |path, store, readValue, spec, onChange, onTouchStart, onTouchEnd|
    ^super.new(path, store, readValue, onChange, onTouchStart, onTouchEnd).init(spec);
  }
  *fromStore { |path, store, key, spec, onTouchStart, onTouchEnd|
    ^super.fromStore(path, store, key, onTouchStart, onTouchEnd).init(spec);
  }
  init { |spec|
    if (spec.isArray) {
      spec = ControlSpec(*spec);
    };
    this.spec = spec;
  }
  convertFromClient { |value|
    if (spec.isNil) {
      ^value;
    } {
      ^spec.map(value);
    }
  }
  convertToClient { |value|
    if (spec.isNil) {
      ^value;
    } {
      ^spec.unmap(value);
    }
  }
}
