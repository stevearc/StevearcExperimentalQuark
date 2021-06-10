PadsDataStore : DataStore {
  var <enabled=false, <padsDown, <padTouchSynths, <selectedSynth;
  *new { |numPads|
    ^super.newCopyArgs(\pads).init(numPads);
  }
  init { |numPads|
    padsDown = Array.fill(numPads, false);
    padTouchSynths = Array.fill(numPads, {TouchSynth.new});
    selectedSynth = padTouchSynths[0];
    this.prAddSetters([\enabled]);
  }
  getSynth { |name|
    padTouchSynths.do { |synth|
      if (synth.name == name) {
        ^synth;
      };
    };
    ^nil;
  }
  selectedSynthName { ^selectedSynth.name }
  setPadSynth { |i, touchSynth|
    if (enabled) {
      padTouchSynths[i].stop;
    };
    if (touchSynth.class == Symbol) {
      touchSynth = TouchSynth(touchSynth, touchSynth);
    };
    padTouchSynths[i] = touchSynth;
    this.forceUpdate;
  }
  setPadState { |i, down|
    padsDown[i] = down;
    this.forceUpdate;
  }
  onPostStateChange {
    if (enabled) {
      padTouchSynths.do { |touchSynth|
        touchSynth.start;
      };
      padsDown.do { |down, i|
        if (down) {
          selectedSynth = padTouchSynths[i];
        };
      };
    } {
      padsDown.size.do { |i|
        padsDown[i] = false;
      };
      padTouchSynths.do { |touchSynth|
        touchSynth.stop;
      };
    };
  }
}
