// TODO I think I can refactor this out
TouchStoreUI : TouchOSCResponder {
  var <store;
  *new { |store|
    ^super.new.initUI(store);
  }
  initUI { |store|
    this.store = store;
  }

  store_ { |theStore|
    if (store == theStore) {
      ^this;
    };
    if (store.notNil) {
      store.removeDependant(this);
    };
    store = theStore;
    if (store.isNil) {
      this.prClearChildren;
    } {
      store.addDependant(this);
      this.addChildrenImpl;
    }
  }

  addChildrenImpl { }

  update { |store, what|
    this.sync;
  }

  stop {
    super.stop;
    this.store = nil;
  }
}
