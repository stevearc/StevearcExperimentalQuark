PadsDataStore {
  var <enabled=false, <padsDown, <padTouchSynths, <selectedSynth;
  *new { |numRows, numCols|
    ^super.new.init(numRows, numCols);
  }
  init { |numRows, numCols|
    var numPads = numRows * numCols;
    padsDown = Array2D.fromArray(numRows, numCols, Array.fill(numPads, false));
    padTouchSynths = Array2D.fromArray(numRows, numCols, Array.fill(numPads, {TouchSynth.new}));
    selectedSynth = padTouchSynths.at(0,0);
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
  setPadSynth { |row, col, touchSynth|
    if (enabled) {
      padTouchSynths.at(row, col).stop;
    };
    padTouchSynths.put(row, col, touchSynth);
    this.markChanged;
  }
  enabled_ { |newval|
    if (newval != enabled) {
      enabled = newval;
      this.markChanged;
    };
  }
  markChanged {
    if (enabled) {
      padTouchSynths.do { |touchSynth|
        touchSynth.start;
      };
      padsDown.rowsDo { |row, i|
        row.do { |down, j|
          if (down) {
            selectedSynth = padTouchSynths.at(i, j);
          };
        };
      };
    } {
      padsDown.rowsDo { |row|
        row.size.do { |i|
          row[i] = false;
        };
      };
      padTouchSynths.do { |touchSynth|
        touchSynth.stop;
      };
    };
    this.changed(\pads);
  }
}
