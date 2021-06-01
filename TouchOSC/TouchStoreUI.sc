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
    this.prClearChildren;
    if (store.notNil) {
      store.addDependant(this);
      this.getChildren.do { |child|
        this.prAddChild(child);
      };
    }
  }

  getChildren { ^[] }

  update { |store, what|
    this.sync;
  }
}
