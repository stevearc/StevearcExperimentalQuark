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
      var newTouch = msg[1] != 0;
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
          this.sync;
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

TouchControlMultiButton : TouchControl {
  var <>dimensions;
  *new { |path, store, dimensions, onChange, onTouchStart, onTouchEnd|
    ^super.new(path, store, nil, onChange, onTouchStart, onTouchEnd).init(dimensions);
  }
  *fromStore { |path, store, key, dimensions, onTouchStart, onTouchEnd|
    var onChange = { |val, row, col|
      var arr2d = store.perform(key);
      arr2d.put(row, col, val);
      store.markChanged;
    };
    ^super.new(path, store, nil, onChange, onTouchStart, onTouchEnd).init(dimensions);
  }
  init { |dimensions|
    this.dimensions = dimensions;
  }
  convertFromClient { |value|
    ^(value != 0);
  }
  convertToClient { |value|
    ^value.asInteger;
  }
  // Don't send any info to the client
  syncImpl { }
  startImpl {
    this.prAddTouchListener;
    this.dimensions[0].do { |row|
      this.dimensions[1].do { |col|
        this.prAddFunc("%/%/%".format(path,row+1,col+1), { |msg|
          var newval = this.convertFromClient(msg[1]);
          this.onChange.value(newval, row, col);
        });
      };
    };
  }
}

TouchControlRange : TouchControl {
  var <>range;
  *new { |path, store, readValue, range, onChange, onTouchStart, onTouchEnd|
    ^super.new(path, store, readValue, onChange, onTouchStart, onTouchEnd).init(range);
  }
  *fromStore { |path, store, key, range, onTouchStart, onTouchEnd|
    ^super.fromStore(path, store, key, onTouchStart, onTouchEnd).init(range);
  }
  init { |range|
    this.range = range;
  }
  convertFromClient { |value|
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
  convertToClient { |value|
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
