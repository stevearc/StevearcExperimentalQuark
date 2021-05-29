TouchControl : TouchOSCResponder {
  var <store, <key, <path, <>onChange, <>onTouchStart, <>onTouchEnd, <isTouching=false;
  *new { |store, key, path, onChange, onTouchStart, onTouchEnd|
    ^super.new.initTouchControl(store, key, path, onChange, onTouchStart, onTouchEnd);
  }
  initTouchControl { |theStore, theKey, thePath, onChange, onTouchStart, onTouchEnd|
    store = theStore;
    store.addDependant(this);
    key = theKey;
    path = thePath;
    this.onChange = onChange;
    this.onTouchStart = onTouchStart;
    this.onTouchEnd = onTouchEnd;
  }
  preprocess { |value| ^value }
  postprocess { |value| ^value }
  listen {
    super.listen;
    this.prAddFunc(path, { |msg|
      var newval = this.preprocess(msg[1]);
      if (this.onChange.notNil) {
        this.onChange.value(newval);
      } {
        store.perform((key ++ '_').asSymbol, newval);
      };
    });
    this.prAddFunc(path ++ "/z", { |msg|
      var newTouch = msg[1] != 0;
      if (isTouching == newTouch) {
        ^nil;
      };
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
    });
  }
  syncImpl {
    // Only update the control when we're not touching it
    if (isTouching.not) {
      clientAddr.sendMsg(path, this.postprocess(store.perform(key)));
    };
  }

  update { |store, what|
    this.sync;
  }
}

TouchControlLabel : TouchOSCResponder {
  var <store, <key, <path;
  *new { |store, key, path|
    ^super.new.init(store, key, path);
  }
  init { |theStore, theKey, thePath|
    store = theStore;
    store.addDependant(this);
    key = theKey;
    path = thePath;
  }
  syncImpl {
    clientAddr.sendMsg(path, store.perform(key));
  }
  update { |store, what|
    this.sync;
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
