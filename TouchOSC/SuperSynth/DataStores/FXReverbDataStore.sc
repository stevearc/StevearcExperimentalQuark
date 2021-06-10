FXReverbDataStore : FXDataStore {
  var <wet, <room;

  *new { |enabled=false, wet=0.3, room=0.5|
    ^super.newCopyArgs(\fxreverb, enabled, wet, room).init;
  }
  init {
    this.prAddSetters([\wet, \room]);
  }
  storeArgs { ^[enabled, wet, room] }
}
