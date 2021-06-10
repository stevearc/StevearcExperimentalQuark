FXDistortionDataStore : FXDataStore {
  var <wet, <distortion, <gain;

  *new { |enabled=false, wet=1, distortion=0.5, gain=0|
    ^super.newCopyArgs(\fxdistortion, enabled, wet, distortion, gain).init;
  }
  init {
    this.prAddSetters([\wet, \distortion, \gain]);
  }
  storeArgs { ^[enabled, wet, distortion, gain] }
}
