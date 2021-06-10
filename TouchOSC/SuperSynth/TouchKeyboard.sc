TouchKeyboard : TouchOSCResponder {
  classvar rootNote;
  var <id, <>touchSynth, store, ui, synths;

  *initClass {
    rootNote = -7;
  }
  *new { |id|
    ^super.new.init(id);
  }
  init { |theId|
    id = theId;
    synths = Array.fill(KeyboardDataStore.keysPerKeyboard);
    store = KeyboardDataStore.new;
    store.addDependant(this);
    ui = TouchKeyboardUI.new("/keys%/".format(id));
    ui.store = store;
    this.prAddChild(ui);
  }

  update { |store, what|
    KeyboardDataStore.keysPerKeyboard.do { |i|
      synths[i] = touchSynth.updateSynth(synths[i], store.keysPlaying[i],
        \freq, this.midinote(i).midicps);
    };
  }

  midinote { |index|
    ^(12 + (store.octave * 12) + rootNote + index + store.pitchBend);
  }

  stop {
    super.stop;
    KeyboardDataStore.keysPerKeyboard.do { |i|
      store.keysPlaying[i] = false;
    };
    store.forceUpdate;
  }
}

TouchKeyboardUI : TouchStoreUI {
  var <prefix;
  *new { |prefix|
    ^super.new.init(prefix);
  }
  init { |thePrefix|
    prefix = thePrefix;
  }
  getChildren {
    var children = [
      TouchControlRange.fromStore(prefix ++ 'pitchBend', store, \pitchBend, onTouchEnd: {
        store.pitchBend = 0;
      }),
      TouchControlRange.fromStore(prefix ++ 'octave', store, \octave, [1,7]),
      TouchControlLabel.fromStore(prefix ++ 'octave/label', store, \octaveLabel),
    ];
    KeyboardDataStore.keysPerKeyboard.do { |i|
      children = children.add(TouchControlButton(prefix ++ (i+1), store, { |down|
        store.setKey(i, down);
      }));
    };
    ^children;
  }
}
