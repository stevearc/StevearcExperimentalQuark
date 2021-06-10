FXFilterDataStore : FXDataStore {
  var <wet, <freq, <width;

  *new { |enabled=false, wet=1, freq=0, width=1|
    ^super.newCopyArgs(\fxfilter, enabled, wet, freq, width).init;
  }
  init {
    this.prAddSetters([\wet, \freq, \width]);
  }
  storeArgs { ^[enabled, wet, freq, width] }
}
