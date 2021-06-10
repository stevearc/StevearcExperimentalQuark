LoopChannelDataStore : DataStore {
  var <shouldRecord=false, <recording=false, <>recordStartTime=nil,
    <recordTimeRemaining=nil, <playing=false;

  *new {
    ^super.newCopyArgs(\loopchannel).init;
  }
  init {
    this.prAddSetters([\playing, \shouldRecord, \recording, \recordTimeRemaining]);
  }

  finishRecording {
    if (this.shouldRecord) {
      playing = true;
    };
    shouldRecord = false;
    recording = false;
    recordTimeRemaining = nil;
    recordStartTime = nil;
    this.forceUpdate;
  }
}
