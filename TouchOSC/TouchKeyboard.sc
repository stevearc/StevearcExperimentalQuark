KeyboardDataStore {
  classvar <keysPerKeyboard=20;
  var <octave=4, <octaveLabel="C4", <pitchBend=0, <keysPlaying;
  *new {
    ^super.new.init;
  }
  init {
    keysPlaying = Array.fill(keysPerKeyboard, false);
  }
  octave_ { |newval|
    var newOctave = newval.round(1).asInteger;
    if (octave != newOctave) {
      octave = newOctave;
      this.process;
    }
  }
  pitchBend_ { |newval|
    pitchBend = newval;
    this.process;
  }
  setKey { |idx, playing|
    if (playing != keysPlaying[idx]) {
      keysPlaying[idx] = playing;
      this.process;
    }
  }
  process {
    octaveLabel = "C%".format(octave);
    this.changed(\keyboard);
  }
}

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
    store.process;
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
  addChildrenImpl {
    this.prAddChild(TouchControlRange(store, \pitchBend, prefix ++ 'pitchBend', onTouchEnd: {
      store.pitchBend = 0;
    }));
    this.prAddChild(TouchControlRange(store, \octave, prefix ++ 'octave', [1,7]));
    this.prAddChild(TouchControlLabel(store, \octaveLabel, prefix ++ 'octave/label'));
    KeyboardDataStore.keysPerKeyboard.do { |i|
      this.prAddChild(TouchControlButton(store, \_, prefix ++ (i+1), { |down|
        store.setKey(i, down);
      }));
    };
  }
}
