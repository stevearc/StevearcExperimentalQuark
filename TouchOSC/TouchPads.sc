TouchPads : TouchOSCResponder {
  classvar numPads=16;
  var ui, synths, <store, loopCtl;
  *new {
    ^super.new.init;
  }
  init {
    store = PadsDataStore.new(numPads);
    store.addDependant(this);
    synths = Array.fill(numPads, nil);
    ui = TouchPadsUI.new;
    ui.store = store;
    this.prAddChild(ui);
    loopCtl = LoopController.new(store);
  }

  selectedSynth {
    ^store.padTouchSynths[store.selectedSynth];
  }

  update { |store, what|
    store.padsDown.size.do { |i|
      var touchSynth = store.padTouchSynths[i];
      if (touchSynth.notNil) {
        synths[i] = touchSynth.updateSynth(synths[i], store.padsDown[i]);
      }
    };
  }

  start {
    super.start;
    loopCtl.start;
    store.enabled = true;
  }

  stop {
    super.stop;
    loopCtl.stop;
    store.enabled = false;
  }
}

TouchPadsUI : TouchStoreUI {
  addChildrenImpl {
    this.prAddChild(TouchControlMultiButton('/pads', [4,4], { |down, x, y|
      var i = y*4 + x;
      store.setPad(i, down);
    }));
    store.padTouchSynths.size.do { |i|
      var x = i % 4;
      var y = (i / 4).asInteger;
      this.prAddChild(TouchControlLabel(store,
          {|store| store.padTouchSynths[i].name},
          "/pads/%/%/label".format(y+1,x+1)));
    };
  }
}
