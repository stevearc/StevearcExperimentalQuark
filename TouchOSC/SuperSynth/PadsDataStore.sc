PadsDataStore {
  var <enabled=false, <padsDown, <padTouchSynths, <selectedSynth;
  *new { |numPads|
    ^super.new.init(numPads);
  }
  init { |numPads|
    padsDown = Array.fill(numPads, false);
    padTouchSynths = Array.fill(numPads, {TouchSynth.new});
    selectedSynth = padTouchSynths[0];
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
    this.markChanged;
  }
  enabled_ { |newval|
    if (newval != enabled) {
      enabled = newval;
      this.markChanged;
    };
  }
  setPadState { |i, down|
    padsDown[i] = down;
    this.markChanged;
  }
  markChanged {
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
    this.changed(\pads);
  }
}
