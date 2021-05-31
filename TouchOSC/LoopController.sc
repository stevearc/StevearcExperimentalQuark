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
}

LoopChannel : TouchOSCResponder {
  var padsStore, loopStore, store, recording, playing=false;
  *new { |padsStore, loopStore, store, index|
    ^super.new.init(padsStore, loopStore, store, index);
  }
  init { |thePadsStore, theLoopStore, theStore, index|
    padsStore = thePadsStore;
    loopStore = theLoopStore;
    store = theStore;
    store.addDependant(this);
    recording = LoopRecording.new(loopStore);
    this.prAddChild(LoopChannelUI.new(store, index));
  }
  play {
    var startBeat = TempoClock.nextTimeOnGrid(recording.duration);
    var synths = IdentityDictionary.new;
    TempoClock.schedAbs(startBeat + recording.duration, {
      if (store.playing) {
        this.play;
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
                synths[event.id].set(\gate, -1.1);
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
    TempoClock.schedAbs(TempoClock.nextTimeOnGrid(1), {
      if (store.shouldRecord.not or: (store.recordTimeRemaining == 1)) {
        store.finishRecording;
        nil;
      } {
        if (TempoClock.beats < store.recordStartTime) {
          store.recordTimeRemaining = TempoClock.beats - store.recordStartTime;
        } {
          store.recording = true;
          store.recordTimeRemaining = totalBeats - (TempoClock.beats - store.recordStartTime);
        };
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
  addChildrenImpl {
    this.prAddChild(TouchControlLabel.fromStore('/looper/tempo/label', store, \bpm));
    this.prAddChild(TouchControlLabel.fromStore('/looper/tempo/name', store, \tempoString));
    this.prAddChild(TouchControlRange.fromStore('/looper/tempo', store, \bpm, [40, 220]));
    this.prAddChild(TouchControlToggle.fromStore('/looper/quantize', store, \quantize));
    this.prAddChild(TouchControlLabel.fromStore('/looper/countdown', store, \countdown));
    this.prAddChild(TouchControlLabel.fromStore('/looper/recording', store, \recording));
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
  addChildrenImpl {
    this.prAddChild(TouchControlToggle.fromStore("/looper/record/%".format(index), store, \shouldRecord));
    this.prAddChild(TouchControlToggle.fromStore("/looper/play/%".format(index), store, \playing));
  }
}
