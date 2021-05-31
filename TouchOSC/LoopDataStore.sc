LoopDataStore {
  var <tempoString, <recordBars=2, <>quantize=true,
    <channelStores;
  *new { |numChannels|
    ^super.new.init(numChannels);
  }
  init { |numChannels|
    channelStores = Array.fill(numChannels, {LoopChannelDataStore.new});
    channelStores.do { |channelStore|
      channelStore.addDependant(this);
    };
    this.markChanged;
  }
  update { |store, what|
    this.markChanged;
  }
  beatsPerBar {
    ^TempoClock.beatsPerBar;
  }
  bpm {
    ^(TempoClock.tempo * 60).asInteger;
  }
  bpm_ { |bpm|
    TempoClock.tempo = bpm.asInteger / 60;
    this.markChanged;
  }
  recording {
    ^channelStores.any { |channelStore| channelStore.recording };
  }
  countdown {
    channelStores.do { |channelStore|
      if (channelStore.recordTimeRemaining.notNil) {
        ^channelStore.recordTimeRemaining.abs;
      };
    };
    ^"";
  }
  markChanged {
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
