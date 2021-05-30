PadsDataStore {
  var <enabled=false, <padsDown, <padTouchSynths, <selectedSynth=0;
  *new { |numPads|
    ^super.new.init(numPads);
  }
  init { |numPads|
    padsDown = Array.fill(numPads, false);
    padTouchSynths = Array.fill(numPads, {TouchSynth.new});
  }
  getSynth { |name|
    padTouchSynths.do { |synth|
      if (synth.name == name) {
        ^synth;
      };
    };
    ^nil;
  }
  setPadSynth { |x, y, touchSynth|
    var idx = y*4 + x;
    if (enabled) {
      padTouchSynths[idx].stop;
    };
    padTouchSynths[idx] = touchSynth;
    this.process;
  }
  setPad { |idx, down|
    if (down != padsDown[idx]) {
      padsDown[idx] = down;
      this.process;
    }
  }
  enabled_ { |newval|
    if (newval != enabled) {
      enabled = newval;
      this.process;
    };
  }
  process {
    if (enabled) {
      padTouchSynths.do { |touchSynth|
        touchSynth.start;
      };
      padsDown.size.do { |i|
        if (padsDown[i]) {
          selectedSynth = i;
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
