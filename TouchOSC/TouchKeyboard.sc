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
  var <id, <>touchSynth, store, synths;

  *initClass {
    rootNote = -7;
  }
  *new { |id, touchSynth|
    ^super.new.init(id, touchSynth);
  }
  init { |kid, tSynth|
    id = kid;
    touchSynth = tSynth;
    synths = Array.fill(KeyboardDataStore.keysPerKeyboard);
    store = KeyboardDataStore.new;
    store.addDependant(this);
    this.prAddChild(TouchControlRange(store, \pitchBend, this.prefix ++ 'pitchBend', onTouchEnd: {
      store.pitchBend = 0;
    }));
    this.prAddChild(TouchControlRange(store, \octave, this.prefix ++ 'octave', [1,7]));
    this.prAddChild(TouchControlLabel(store, \octaveLabel, this.prefix ++ 'octave/label'));
    KeyboardDataStore.keysPerKeyboard.do { |i|
      this.prAddChild(TouchControlButton(store, \_, this.prefix ++ (i+1), { |down|
        store.setKey(i, down);
      }));
    };
  }

  prefix { ^"/keys%/".format(id) }

  update { |store, what|
    KeyboardDataStore.keysPerKeyboard.do { |i|
      synths[i] = touchSynth.updateSynth(synths[i], store.keysPlaying[i],
        \freq, this.midinote(i).midicps);
    };
  }

  midinote { |index|
    ^(12 + (store.octave * 12) + rootNote + index + store.pitchBend);
  }

  free {
    super.free;
    KeyboardDataStore.keysPerKeyboard.do { |i|
      store.keysPlaying[i] = false;
    };
    store.process;
  }
}
