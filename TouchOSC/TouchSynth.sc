TouchSynth {
  classvar masterGroup;
  var <name, <out=0, <group, <bus, <isRunning=false,
    <store, <delayStore, <filterStore, <reverbStore,
    synths, delaySynth, filterSynth, reverbSynth, monitorSynth;

  *hydrate { |name, store, delayStore, filterStore, reverbStore|
    ^super.new.init(name, store, delayStore, filterStore, reverbStore);
  }
  *new { |name=\default, synthName=\default, args=nil|
    ^super.new.init(name, SynthDataStore(synthName, args ? []), nil, nil, nil);
  }
  init { |theName, theStore, theDelayStore, theFilterStore, theReverbStore|
    synths = IdentityDictionary.new;
    name = theName;
    store = theStore;
    store.addDependant(this);
    delayStore = theDelayStore ?? {FXDelayDataStore.new};
    delayStore.addDependant(this);
    filterStore = theFilterStore ?? {FXFilterDataStore.new};
    filterStore.addDependant(this);
    reverbStore = theReverbStore ?? {FXReverbDataStore.new};
    reverbStore.addDependant(this);
  }

  out_ { |theOut|
    out = theOut;
    if (monitorSynth.notNil) {
      monitorSynth.set(\out, out);
    };
  }

  update { |store, what|
    if (isRunning.not) {
      ^this;
    };
    switch (what,
      \fxdelay, {
        delaySynth = this.updateFXSynth(delaySynth,
          \touchOSCDelay, store.enabled,
          \delayTime, store.delayTime / TempoClock.tempo,
          \decayTime, store.decayTime / TempoClock.tempo,
          \decayMul, store.decayMul,
        );
      },
      \fxfilter, {
        filterSynth = this.updateFXSynth(filterSynth,
          \touchOSCFilter, store.enabled,
          \wet, store.wet,
          \freq, store.freq,
          \width, store.width,
        );
      },
      \fxreverb, {
        reverbSynth = this.updateFXSynth(reverbSynth,
          \touchOSCReverb, store.enabled,
          \wet, store.wet,
          \room, store.room,
        );
      },
      \synthData, {
        if (store.args.notEmpty) {
          synths.do { |synth|
            synth.set(*store.args);
          };
        };
      }
    );
  }

  makeSynth { |args|
    ^Synth(store.synthName, [\out, bus] ++ store.args ++ args, group, \addToHead);
  }

  updateSynth { |synth, enabled ...args|
    if (enabled) {
      if (synth.isNil) {
        synth = Synth(store.synthName, [\out, bus] ++ store.args ++ args, group, \addToHead);
        synth.register(true);
        synths[synth.nodeID] = synth;
        synth.onFree {
          synths[synth.nodeID] = nil;
        };
        this.changed(\note_start, synth.nodeID, store.args ++ args);
      } {
        if (synth.isPlaying and: args.notEmpty) {
          this.changed(\note_set, synth.nodeID, args);
          synth.set(*args);
        }
      }
      ^synth;
    } {
      if (synth.notNil and: {synth.isPlaying}) {
        synth.set(\gate, 0);
        this.changed(\note_end, synth.nodeID);
      }
      ^nil;
    }
  }

  updateFXSynth { |synth, name, enabled ...args|
    if (enabled) {
      if (synth.isNil) {
        // TODO: will need to figure out fx ordering at some point
        ^Synth(name, [\out, bus] ++ args, group, \addToHead);
      } {
        synth.set(*args);
        ^synth;
      };
    } {
      if (synth.notNil) {
        synth.free;
      }
      ^nil;
    };
  }

  start {
    if (isRunning) {
      ^this;
    };
    isRunning = true;
    Task({
      if (masterGroup.isNil) {
        masterGroup = Group.new(Server.default, \addAfter);
        Server.default.sync;
        CmdPeriod.doOnce {
          masterGroup.free;
          masterGroup = nil;
        };
      };
      group = Group.new(masterGroup, \addToTail);
      bus = Bus.audio(Server.default, 2);
      SynthDef(\touchOSCMonitor, {
        var sig = In.ar(\bus.kr(0), 2);
        Out.ar(\out.kr(0), sig);
      }).add;
      SynthDef(\touchOSCReverb, {
        var sig = In.ar(\out.kr(0), 2);
        sig = FreeVerb.ar(sig,
          \wet.kr,
          \room.kr,
          \roomdamp.kr(0.5), // HF dampening from 0-1
        );
        ReplaceOut.ar(\out.kr(0), sig);
      }).add;
      SynthDef(\touchOSCFilter, {
        var sig = In.ar(\out.kr(0), 2);
        // Boost the volume on the lows when we're doing a LPF
        var amp = \freq.kr.lincurve(60,440,1.5,1,-1);
        var filter = BPF.ar(sig, \freq.kr, \width.kr, amp);
        sig = XFade2.ar(sig, filter, \wet.kr.linlin(0,1,-1,1));
        ReplaceOut.ar(\out.kr(0), sig);
      }).add;
      SynthDef(\touchOSCDelay, {
        var sig = In.ar(\out.kr(0), 2);
        sig = sig + CombL.ar(
          sig,
          1,
          \delayTime.kr,
          \decayTime.kr,
          \decayMul.kr,
        );
        ReplaceOut.ar(\out.kr(0), sig);
      }).add;
      Server.default.sync;
      monitorSynth = Synth(\touchOSCMonitor, [\bus, bus, \out, out], group, \addToTail);
      store.markChanged;
      delayStore.markChanged;
      filterStore.markChanged;
      reverbStore.markChanged;
    }).start;
  }

  stop {
    if (isRunning.not) {
      ^this;
    };
    group.free;
    bus.free;
    bus = nil;
    group = nil;
    delaySynth = nil;
    filterSynth = nil;
    reverbSynth = nil;
    monitorSynth = nil;
    isRunning = false;
  }

  storeOn { |stream|
    stream << "TouchSynth.hydrate(" <<< name << ",";
    stream <<< store << ",";
    stream <<< delayStore << ",";
    stream <<< filterStore << ",";
    stream <<< reverbStore;
    stream << ")";
  }

  asP {
    ^Pn(this.asEvent);
  }

  asEvent { |includeFreq=false|
    var ev = Event.default;
    var synthDesc;
    ev[\instrument] = store.synthName;
    synthDesc = SynthDescLib.global[store.synthName];
    if (synthDesc.isNil) {
      Error("Could not find synth %".format(store.synthName)).throw;
    };
    if (synthDesc.controlDict[\amp].notNil) {
      ev[\amp] = synthDesc.controlDict[\amp].defaultValue;
    };
    if (includeFreq and: synthDesc.controlDict[\freq].notNil) {
      ev[\freq] = synthDesc.controlDict[\freq].defaultValue;
    };
    store.args.pairsDo { |key, value|
      if (key != \freq or: includeFreq) {
        ev[key] = value;
      };
    };
    ^ev;
  }
}
