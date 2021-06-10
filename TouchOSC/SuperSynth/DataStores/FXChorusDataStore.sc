FXChorusDataStore : FXDataStore {
  var <speed, <depth, <predelay;

  *new { |enabled=false, speed=1, depth=0.001, predelay=0.001|
    ^super.newCopyArgs(\fxchorus, enabled, speed, depth, predelay).init;
  }
  init {
    this.prAddSetters([\speed, \depth, \predelay]);
  }
  storeArgs { ^[enabled, speed, depth, predelay] }
}
