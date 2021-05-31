FXReverbDataStore {
  var <enabled=false, <wet=0.3, <room=0.5;

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
