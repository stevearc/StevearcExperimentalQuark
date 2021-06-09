FXDataStore : DataStore {
  var <enabled;

  enabled_ { |newval|
    this.setState(\enabled, newval);
  }

  storeOn { |stream|
    if (enabled) {
      super.storeOn(stream);
    } {
      stream <<< nil;
    }
  }
}
