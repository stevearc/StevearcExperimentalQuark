TouchControl : TouchOSCResponder {
  var <store, <key, <path, <>onChange, <>onTouchStart, <>onTouchEnd, <isTouching=false, clientValue;
  *new { |store, key, path, onChange, onTouchStart, onTouchEnd|
    ^super.new.initTouchControl(store, key, path, onChange, onTouchStart, onTouchEnd);
  }
  initTouchControl { |theStore, theKey, thePath, onChange, onTouchStart, onTouchEnd|
    store = theStore;
    if (store.notNil) {
      store.addDependant(this);
    };
    key = theKey;
    path = thePath;
    this.onChange = onChange;
    this.onTouchStart = onTouchStart;
    this.onTouchEnd = onTouchEnd;
  }
  preprocess { |value| ^value }
  postprocess { |value| ^value }
  start {
    super.start;
    this.startImpl;
  }
  startImpl {
    this.prAddFunc(path, { |msg|
      var newval = this.preprocess(msg[1]);
      clientValue = newval;
      if (this.onChange.notNil) {
        this.onChange.value(newval);
      } {
        store.perform((key ++ '_').asSymbol, newval);
      };
    });
    this.prAddTouchListener;
  }
  prAddTouchListener {
    this.prAddFunc(path ++ "/z", { |msg|
      var newTouch = msg[1] != 0;
      if (isTouching != newTouch) {
        isTouching = newTouch;
        if (isTouching) {
          if (onTouchStart.notNil) {
            onTouchStart.value();
          };
        } {
          if (onTouchEnd.notNil) {
            onTouchEnd.value();
          };
          this.sync;
        };
      };
    });
  }
  syncImpl { |forceUpdate|
    // Only update the control when we're not touching it
    if (isTouching.not) {
      var value = store.perform(key);
      if (value != clientValue or: forceUpdate) {
        clientValue = value;
        clientAddr.sendMsg(path, this.postprocess(value));
      };
    };
  }
  stop {
    super.stop;
    clientValue = nil;
  }
  update { |store, what|
    this.sync;
  }
}

TouchControlLabel : TouchOSCResponder {
  var <store, <key, <path, clientValue;
  *new { |store, key, path|
    ^super.new.init(store, key, path);
  }
  init { |theStore, theKey, thePath|
    store = theStore;
    store.addDependant(this);
    key = theKey;
    path = thePath;
  }
  syncImpl { |forceUpdate|
    var value;
    if (key.isFunction) {
      value = key.value(store);
    } {
      value = store.perform(key);
    };
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
  preprocess { |value|
    ^(value != 0);
  }
  postprocess { |value|
    ^value.asInteger;
  }
}

TouchControlButton : TouchControl {
  preprocess { |value|
    ^(value != 0);
  }
  postprocess { |value|
    ^value.asInteger;
  }
  // Don't send any info to the client
  syncImpl { }
}

TouchControlMultiButton : TouchControl {
  var <>dimensions;
  *new { |path, dimensions, onChange, onTouchStart, onTouchEnd|
    ^super.new(nil, nil, path, onChange, onTouchStart, onTouchEnd).init(dimensions);
  }
  init { |dimensions|
    this.dimensions = dimensions;
  }
  preprocess { |value|
    ^(value != 0);
  }
  postprocess { |value|
    ^value.asInteger;
  }
  // Don't send any info to the client
  syncImpl { }
  startImpl {
    this.prAddTouchListener;
    this.dimensions[0].do { |x|
      this.dimensions[1].do { |y|
        this.prAddFunc("%/%/%".format(path,y+1,x+1), { |msg|
          var newval = this.preprocess(msg[1]);
          this.onChange.value(newval, x, y);
        });
      };
    };
  }
}

TouchControlRange : TouchControl {
  var <>range;
  *new { |store, key, path, range, onChange, onTouchStart, onTouchEnd|
    ^super.new(store, key, path, onChange, onTouchStart, onTouchEnd).init(range);
  }
  init { |range|
    this.range = range;
  }
  preprocess { |value|
    if (range.isNil) {
      ^value;
    } {
      if (range.size == 3) {
        ^value.lincurve(0,1,range[0],range[1],range[2]);
      } {
        ^value.linlin(0,1,range[0],range[1]);
      }
    }
  }
  postprocess { |value|
    if (range.isNil) {
      ^value;
    } {
      if (range.size == 3) {
        ^value.curvelin(range[0],range[1],0,1,range[2]);
      } {
        ^value.linlin(range[0],range[1],0,1);
      }
    }
  }
}