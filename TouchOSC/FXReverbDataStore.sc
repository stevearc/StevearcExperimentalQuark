FXReverbDataStore {
  var <enabled, <wet, <room;

  *new { |enabled=false, wet=0.3, room=0.5|
    ^super.newCopyArgs(enabled, wet, room);
  }
  storeArgs { ^[enabled, wet, room] }

  enabled_ { |newval|
    if (newval != enabled) {
      enabled = newval;
      this.markChanged;
    }
  }
  wet_ { |newval|
    wet = newval;
    this.markChanged;
  }
  room_ { |newval|
    room = newval;
    this.markChanged;
  }

  markChanged {
    this.changed(\fxreverb);
  }
}
