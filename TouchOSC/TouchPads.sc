TouchPads : TouchOSCResponder {
  classvar numRows=4, numCols=4;
  var ui, synths, <store, <loopCtl;
  *new {
    ^super.new.init;
  }
  init {
    store = PadsDataStore.new(numRows, numCols);
    store.addDependant(this);
    synths = Array2D.new(numRows, numCols);
    ui = TouchPadsUI.new;
    ui.store = store;
    this.prAddChild(ui);
    loopCtl = LoopController.new(store);
    this.prAddChild(loopCtl);
  }

  selectedSynth {
    ^store.selectedSynth;
  }

  serializeLoops {
    ^loopCtl.serializeLoops;
  }

  update { |store, what|
    store.padsDown.rowsDo { |row, i|
      row.do { |down, j|
        var touchSynth = store.padTouchSynths.at(i, j);
        if (touchSynth.notNil) {
          synths.put(i, j, touchSynth.updateSynth(synths.at(i,j), down));
        }
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
      TouchControlMultiButton.fromStore('/pads', store, \padsDown, [4,4]),
    ];
    store.padTouchSynths.rowsDo { |row, i|
      row.size.do { |j|
        children = children.add(TouchControlLabel(
          "/pads/%/%/label".format(i+1,j+1),
          store,
          {|store| store.padTouchSynths.at(i, j).name},
        ));
      }
    };
    ^children;
  }
}
