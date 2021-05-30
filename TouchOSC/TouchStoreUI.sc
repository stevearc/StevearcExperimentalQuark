TouchStoreUI : TouchOSCResponder {
  var <store;

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
      if (this.isListening) {
        this.sync;
      };
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
