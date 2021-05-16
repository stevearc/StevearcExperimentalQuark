C {
  *new {|idx, numNotes=3, invert=0, voice=#[], drop=#[]|
    ^Chord(idx, numNotes, invert, voice, drop);
  }
}

Chord {
  var <root, <numNotes, invert, voice, drop, <>scaleSize = 7;

  *new {|root, numNotes=3, invert=0, voice=#[], drop=#[]|
    if (invert == 0) {
      invert = ((root - root.round).abs) / 0.1;
      root = root.round;
    };
    if (voice.isNumber) {
      voice = [voice];
    };
    if (drop.isNumber) {
      drop = [drop];
    };
		^super.newCopyArgs(root, numNotes, invert, voice, drop);
  }
	storeArgs { ^[root, numNotes, invert, voice, drop, scaleSize] }

  invert {|level|
    ^Chord(root, numNotes, level, voice, drop);
  }

  voice {|level|
    ^Chord(root, numNotes, invert, level, drop);
  }

  drop {|level|
    ^Chord(root, numNotes, invert, voice, level);
  }

  degrees {
    var base = Array.series(numNotes, root - 1, 2);
    var baseSize = base.size;

    invert.do {|i|
      base[i] = base[i] + scaleSize;
    };

    // For each element in the voice array, bump that element up an octave. If
    // the element is outside the array, double (add the note an octave up) the
    // note at the mod index
    voice.do {|i|
      i = i - 1;
      if (i >= baseSize) {
        i = i % baseSize;
        base = base.add(base[i] + scaleSize);
      } {
        base[i] = base[i] + scaleSize;
      };
    };

    drop.do {|i|
      i = i - 1;
      if (i >= baseSize) {
        i = i % baseSize;
        base = base.add(base[i] - scaleSize);
      } {
        base[i] = base[i] - scaleSize;
      };
    };
    base.sort;
    ^base;
  }

  + {|other|
    ^(other + this.degrees);
  }

	printOn { |stream|
    stream << "Chord(" << this.degrees << ")";
	}
}

+ Array {
  chords {|numNotes=3, voice=#[], drop=#[]|
    ^this.collect {|item, idx|
      if (item.class == Chord) {
        item;
      } {
        Chord(item, numNotes, 0, voice, drop);
      };
    };
  }
}
