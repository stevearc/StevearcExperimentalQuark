FXFilterDataStore {
  var <enabled=false, <wet=1, <freq=440, <width=1;

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
  freq_ { |newval|
    freq = newval;
    this.markChanged;
  }
  width_ { |newval|
    width = newval;
    this.markChanged;
  }

  markChanged {
    this.changed(\fxfilter);
  }
}
