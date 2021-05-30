FXReverbDataStore {
  var <enabled=false, <wet=0.3, <room=0.5;

  enabled_ { |newval|
    if (newval != enabled) {
      enabled = newval;
      this.process;
    }
  }
  wet_ { |newval|
    wet = newval;
    this.process;
  }
  room_ { |newval|
    room = newval;
    this.process;
  }

  process {
    this.changed(\fxreverb);
  }
}
