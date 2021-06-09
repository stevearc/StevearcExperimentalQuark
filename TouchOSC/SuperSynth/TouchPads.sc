TouchPads : TouchOSCResponder {
  classvar numPads=16;
  var ui, synths, <store, <loopCtl;
  *new {
    ^super.new.init;
  }
  init {
    store = PadsDataStore.new(numPads);
    store.addDependant(this);
    synths = Array.fill(numPads);
    ui = TouchPadsUI.new;
    ui.store = store;
    this.prAddChild(ui);
    loopCtl = LoopController.new(store);
    this.prAddChild(loopCtl);
  }

  selectedSynth {
    ^store.selectedSynth;
  }

  recordings {
    ^loopCtl.recordings;
  }

  update { |store, what|
    store.padsDown.do { |down, i|
      var touchSynth = store.padTouchSynths[i];
      if (touchSynth.notNil) {
        synths[i] = touchSynth.updateSynth(synths[i], down);
      }
    };
  }

  start {
    super.start;
    store.enabled = true;
  }

  stop {
    super.stop;
    store.enabled = false;
  }
}

TouchPadsUI : TouchStoreUI {
  getChildren {
    var children = [
      TouchControlLabel.fromStore('/synthName', store, \selectedSynthName),
      TouchControlGrid.d1("/pads/%", 16, {|path, i|
        TouchControlButton(path, store, {|down| store.setPadState(i, down) });
      }),
      TouchControlGrid.d1("/pads/label/%", 16, {|path, i|
        TouchControlLabel(path, store, {|store| store.padTouchSynths[i].name});
      });
    ];
    ^children;
  }
}
