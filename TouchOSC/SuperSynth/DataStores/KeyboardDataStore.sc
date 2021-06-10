KeyboardDataStore : DataStore {
  classvar <keysPerKeyboard=20;
  var <octave=4, <octaveLabel="C4", <pitchBend=0, <keysPlaying;
  *new {
    ^super.newCopyArgs(\keyboard).init;
  }
  init {
    keysPlaying = Array.fill(keysPerKeyboard, false);
    this.prAddSetters([\pitchBend]);
  }
  octave_ { |newval|
    this.setState(\octave, newval.round(1).asInteger);
  }
  setKey { |idx, playing|
    if (playing != keysPlaying[idx]) {
      keysPlaying[idx] = playing;
      this.forceUpdate;
    }
  }
  onPostStateChange {
    octaveLabel = "C%".format(octave);
  }
}


