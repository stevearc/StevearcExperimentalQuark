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

  recordings {
    ^loopCtl.recordings;
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
      TouchControlGrid.d2("/pads/%/%", [4,4], {|path, row, col|
        TouchControlButton(path, store, {|down| store.setPadState(row, col, down) });
      }),
      TouchControlGrid.d2("/pads/label/%/%", [4,4], {|path, row, col|
        TouchControlLabel(path, store, {|store| store.padTouchSynths.at(row, col).name});
      });
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
