LoopController : TouchOSCResponder {
  classvar numChannels=4;
  var channels, <store;
  *new { |padsStore|
    ^super.new.init(padsStore);
  }
  init { |padsStore|
    store = LoopDataStore.new(numChannels);
    store.addDependant(this);
    channels = Array.fill(numChannels, {|i| LoopChannel(padsStore, store, store.channelStores[i], i+1)});
    channels.do { |channel|
      this.prAddChild(channel);
    };
    this.prAddChild(LoopUI.new(store));
  }

  serializeLoops {
    ^channels.collect(_.recording);
  }

  setLoop { |i, recording|
    channels[i].recording = recording;
  }
}

LoopChannel : TouchOSCResponder {
  var padsStore, loopStore, store, <recording, playing=false;
  *new { |padsStore, loopStore, store, index|
    ^super.new.init(padsStore, loopStore, store, index);
  }
  init { |thePadsStore, theLoopStore, theStore, index|
    padsStore = thePadsStore;
    loopStore = theLoopStore;
    store = theStore;
    store.addDependant(this);
    recording = LoopRecording.empty(loopStore);
    this.prAddChild(LoopChannelUI.new(store, index));
  }
  recording_ { |theRecording|
    recording.clear;
    recording.stop;
    recording = theRecording;
    recording.store = loopStore;
  }
  play {
    var startBeat = TempoClock.nextTimeOnGrid(recording.duration);
    var synths = IdentityDictionary.new;
    // Loop playback
    TempoClock.schedAbs(startBeat + recording.duration, {
      if (store.playing) {
        this.play;
      } {
        synths.do { |synth|
          if (synth.isRunning) {
            synth.set(\gate, 0);
          };
        };
      };
    });
    recording.events.do { |event|
      var target = startBeat + event.beat;
      TempoClock.schedAbs(target, {
        var touchSynth = padsStore.getSynth(event.synthName);
        if (touchSynth.notNil) {
          var synth = synths[event.id];
          switch(event.type,
            \note_start, {
              synth = touchSynth.makeSynth(event.args);
              synth.register(true);
              synths[event.id] = synth;
            },
            \note_set, {
              if (synth.notNil and: {synth.isRunning}) {
                synth.set(*event.args);
              };
            },
            \note_end, {
              if (synth.notNil and: {synth.isRunning}) {
                synths[event.id].set(\gate, 0);
                synths[event.id] = nil;
              };
            },
          );
        };
      });
    };
  }

  start {
    super.start;
    SynthDef(\touchOSCMetronome, {
      var sig = SinOsc.ar(\freq.ar(440)!2);
      var env = EnvGen.kr(Env.perc(0.01, 0.2), doneAction: Done.freeSelf);
      Out.ar(0, sig * env * \amp.kr(0.1));
    }).add;
  }

  startRecording {
    var beatsPerBar = loopStore.beatsPerBar;
    var totalBeats = beatsPerBar * loopStore.recordBars;
    store.recordStartTime = TempoClock.nextTimeOnGrid(totalBeats, -1 * beatsPerBar)
      + beatsPerBar;

    // actually start recording riiiight before the first bar, to catch tiny
    // imperfections in playing
    TempoClock.schedAbs(store.recordStartTime - (1/16), {
      if (store.shouldRecord) {
        store.recording = true;
      };
    });

    TempoClock.schedAbs(TempoClock.nextTimeOnGrid(1), {
      if (store.shouldRecord.not or: (store.recordTimeRemaining == 1)) {
        store.finishRecording;
        nil;
      } {
        if (TempoClock.beats < store.recordStartTime) {
          store.recordTimeRemaining = TempoClock.beats - store.recordStartTime;
        } {
          store.recordTimeRemaining = totalBeats - (TempoClock.beats - store.recordStartTime);
        };
        // Play the metronome for one bar before we start recording and while recording
        if (store.recordTimeRemaining >= (-1 * beatsPerBar)) {
          var amp = 0.1;
          if (TempoClock.beats % beatsPerBar == 0) {
            amp = 0.3;
          };
          Synth(\touchOSCMetronome, [\amp, amp]);
        };
        1;
      };
    });
  }

  update { |store, what|
    if (store.shouldRecord and: store.recordStartTime.isNil) {
      this.startRecording;
    };

    if (store.recording) {
      if (recording.isRecording.not) {
        if (store.playing.not) {
          recording.clear;
        };
        recording.duration = loopStore.recordBars * loopStore.beatsPerBar;
        padsStore.padTouchSynths.do { |synth|
          recording.record(synth);
        };
      };
    } {
      if (recording.isRecording) {
        recording.stop;
      };
    };

    if (store.playing) {
      if (playing.not) {
        this.play;
        playing = true;
      };
    } {
      if (playing) {
        playing = false;
      };
    };

    this.sync;
  }
}

LoopUI : TouchStoreUI {
  getChildren {
    ^[
      TouchControlLabel.fromStore('/looper/tempo/label', store, \bpm),
      TouchControlLabel.fromStore('/looper/tempo/name', store, \tempoString),
      TouchControlRange.fromStore('/looper/tempo', store, \bpm, [40, 220]),
      TouchControlRange.fromStore('/looper/numBars', store, \recordBars, [1, 8]),
      TouchControlLabel.fromStore('/looper/numBars/label', store, \barsLabel),
      TouchControlToggle.fromStore('/looper/quantize', store, \quantize),
      TouchControlLabel.fromStore('/looper/countdown', store, \countdown),
      TouchControlLabel.fromStore('/looper/recording', store, \recording),
    ];
  }
}

LoopChannelUI : TouchStoreUI {
  var index;
  *new { |store, index|
    ^super.new(nil).init(store, index);
  }
  init { |theStore, theIndex|
    index = theIndex;
    this.store =theStore;
  }
  getChildren {
    ^[
      TouchControlToggle.fromStore("/looper/record/%".format(index), store, \shouldRecord),
      TouchControlToggle.fromStore("/looper/play/%".format(index), store, \playing),
    ];
  }
}
