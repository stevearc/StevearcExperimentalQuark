DataStore {
  var uniqueSymbol;

  setState { |key, val|
    var oldstate;
    if (key.isFunction) {
      oldstate = this.copy;
      key.value;
    } {
      if (this.perform(key) == val) {
        ^false;
      };
      oldstate = this.copy;
      this.instVarPut(key, val);
    };
    this.onPostStateChange(oldstate);
    this.changed(uniqueSymbol, oldstate);
    ^true;
  }

  forceUpdate {
    this.onPostStateChange(this);
    this.changed(uniqueSymbol, this);
  }

  prAddSetters { |fields|
    fields.do { |field|
      this.addUniqueMethod((field ++ '_').asSymbol, { |instance, newval|
        this.setState(field, newval);
      });
    }
  }

  onPostStateChange { }
}

