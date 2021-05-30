LoopController : TouchOSCResponder {
  var padsStore, ui, <store, recording, playing=false;
  *new { |padsStore|
    ^super.new.init(padsStore);
  }
  init { |thePadsStore|
    padsStore = thePadsStore;
    store = LoopDataStore.new;
    store.addDependant(this);
    recording = LoopRecording.new(store);
    ui = LoopUI.new;
    ui.store = store;
    this.prAddChild(ui);
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
      var target;
      if (store.quantize and: (event.type != \note_set)) {
        if (store.bpm > 100) {
          target = startBeat + event.beat.round(1/4);
        } {
          target = startBeat + event.beat.round(1/8);
        };
      } {
        target = startBeat + event.beat
      };
      TempoClock.schedAbs(target, {
        var touchSynth = padsStore.getSynth(event.synthName);
        if (touchSynth.notNil) {
          switch(event.type,
            \note_start, {
              synths[event.id] = touchSynth.makeSynth(event.args);
            },
            \note_set, {
              synths[event.id].set(*event.args);
            },
            \note_end, {
              synths[event.id].set(\gate, -1.1);
              synths[event.id] = nil;
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

  update { |store, what|
    if (store.shouldRecord and: store.recordStartTime.isNil) {
      var totalBeats = store.beatsPerBar * store.recordBars;
      store.recordStartTime = TempoClock.nextTimeOnGrid(totalBeats, -1 * store.beatsPerBar)
        + store.beatsPerBar;
      TempoClock.schedAbs(TempoClock.nextTimeOnGrid(1), {
        var amp = 0.1;
        if (TempoClock.beats % store.beatsPerBar == 0) {
          amp = 0.3;
        };
        Synth(\touchOSCMetronome, [\amp, amp]);
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
          1;
        };
      });
    };

    if (store.recording) {
      if (recording.isRecording.not) {
        if (store.playing.not) {
          recording.clear;
        };
        recording.duration = store.recordBars * store.beatsPerBar;
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
    this.prAddChild(TouchControlLabel(store, \bpm, '/looper/tempo/label'));
    this.prAddChild(TouchControlLabel(store, \tempoString, '/looper/tempo/name'));
    this.prAddChild(TouchControlRange(store, \bpm, '/looper/tempo', [40, 220]));

    this.prAddChild(TouchControlLabel(store, \recordCountdown, '/looper/countdown'));
    this.prAddChild(TouchControlToggle(store, \shouldRecord, '/looper/record'));
    this.prAddChild(TouchControlLabel(store, \recording, '/looper/recording'));

    this.prAddChild(TouchControlToggle(store, \playing, '/looper/play'));
  }
}
