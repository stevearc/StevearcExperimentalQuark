FXChorusDataStore {
  var <enabled, <speed, <depth, <predelay;

  *new { |enabled=false, speed=1, depth=0.001, predelay=0.001|
    ^super.newCopyArgs(enabled, speed, depth, predelay);
  }
  storeArgs { ^[enabled, speed, depth, predelay] }
  storeOn { |stream|
    if (enabled) {
      super.storeOn(stream);
    } {
      stream <<< nil;
    }
  }

  enabled_ { |newval|
    if (newval != enabled) {
      enabled = newval;
      this.markChanged;
    }
  }
  speed_ { |newval|
    speed = newval;
    this.markChanged;
  }
  depth_ { |newval|
    depth = newval;
    this.markChanged;
  }
  predelay_ { |newval|
    predelay = newval;
    this.markChanged;
  }

  markChanged {
    this.changed(\fxchorus);
  }
}
