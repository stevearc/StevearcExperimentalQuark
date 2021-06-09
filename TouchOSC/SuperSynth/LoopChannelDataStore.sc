LoopChannelDataStore {
  var <shouldRecord=false, <recording=false, <>recordStartTime=nil,
    <recordTimeRemaining=nil, <playing=false;
  playing_ { |newval|
    if (playing != newval) {
      playing = newval;
      this.markChanged;
    };
  }
  finishRecording {
    if (this.shouldRecord) {
      playing = true;
    };
    shouldRecord = false;
    recording = false;
    recordTimeRemaining = nil;
    recordStartTime = nil;
    this.markChanged;
  }
  shouldRecord_ { |newval|
    if (shouldRecord != newval) {
      shouldRecord = newval;
      this.markChanged;
    };
  }
  recording_ { |newval|
    if (recording != newval) {
      recording = newval;
      this.markChanged;
    };
  }
  recordTimeRemaining_ { |newval|
    recordTimeRemaining = newval;
    this.markChanged;
  }
  markChanged {
    this.changed(\loopchannel);
  }
}
