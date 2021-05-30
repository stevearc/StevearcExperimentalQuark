LoopDataStore {
  var <tempoString, <shouldRecord=false, <recording=false, <>recordStartTime=nil,
    <recordTimeRemaining=nil, <recordBars=2, <playing=false, <quantize=true;
  *new {
    ^super.new.init;
  }
  init {
    this.process;
  }
  beatsPerBar {
    ^TempoClock.beatsPerBar;
  }
  playing_ { |newval|
    if (playing != newval) {
      playing = newval;
      this.process;
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
    this.process;
  }
  shouldRecord_ { |newval|
    if (shouldRecord != newval) {
      shouldRecord = newval;
      this.process;
    };
  }
  recording_ { |newval|
    if (recording != newval) {
      recording = newval;
      this.process;
    };
  }
  recordTimeRemaining_ { |newval|
    recordTimeRemaining = newval;
    this.process;
  }
  recordCountdown {
    if (recordTimeRemaining.isNil) {
      ^"";
    } {
      ^recordTimeRemaining.abs;
    };
  }
  bpm {
    ^(TempoClock.tempo * 60).asInteger;
  }
  bpm_ { |bpm|
    TempoClock.tempo = bpm.asInteger / 60;
    this.process;
  }
  process {
    tempoString = case
      { this.bpm <= 60 } { "largo" }
      { this.bpm <= 66 } { "larghetto" }
      { this.bpm <= 76 } { "adagio" }
      { this.bpm <= 108 } { "andante" }
      { this.bpm <= 120 } { "moderato" }
      { this.bpm <= 168 } { "allegro" }
      { this.bpm <= 200 } { "presto" }
      { this.bpm > 200 } { "prestissimo" };
    this.changed(\loop);
  }
}
