FXFilterDataStore {
  var <enabled=false, <wet=1, <freq=440, <width=1;

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
  freq_ { |newval|
    freq = newval;
    this.process;
  }
  width_ { |newval|
    width = newval;
    this.process;
  }

  process {
    this.changed(\fxfilter);
  }
}
