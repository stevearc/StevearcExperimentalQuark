TouchSynth {
  classvar masterGroup;
  var <name, <out=0, <group, <bus, <isRunning=false,
    <store, <delayStore, <filterStore, <reverbStore, <chorusStore, <distStore,
    synths, delaySynth, filterSynth, reverbSynth, chorusSynth, distSynth, monitorSynth;

  *hydrate { |name, store, delayStore, filterStore, reverbStore, chorusStore, distStore|
    ^super.new.init(name, store, delayStore, filterStore, reverbStore, chorusStore, distStore);
  }
  *new { |name=\default, synthName=nil, args=nil|
    if (synthName.isNil) {
      synthName = name;
    };
    ^super.new.init(name, SynthDataStore(synthName, args ? []));
  }
  init { |theName, theStore, theDelayStore, theFilterStore, theReverbStore, theChorusStore, theDistStore|
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
    chorusStore = theChorusStore ?? {FXChorusDataStore.new};
    chorusStore.addDependant(this);
    distStore = theDistStore ?? {FXDistortionDataStore.new};
    distStore.addDependant(this);
  }

  out_ { |theOut|
    out = theOut;
    if (monitorSynth.notNil) {
      monitorSynth.set(\out, out);
    };
  }

  update { |store, what, oldstore|
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
      \fxchorus, {
        chorusSynth = this.updateFXSynth(chorusSynth,
          \touchOSCChorus, store.enabled,
          \speed, store.speed,
          \depth, store.depth,
          \predelay, store.predelay,
        );
      },
      \fxdistortion, {
        distSynth = this.replaceSynth(distSynth,
          \touchOSCDistortion, store.enabled,
          \distortion, store.distortion,
          \wet, store.wet,
          \gain, store.gain,
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

  replaceSynth { |synth, name, enabled ...args|
    if (enabled) {
      if (synth.isNil) {
        // TODO: will need to figure out fx ordering at some point
        ^Synth(name, [\out, bus] ++ args, group, \addToHead);
      } {
        ^Synth.replace(synth, name, [\out, bus] ++ args, true);
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
        var freq = \freq.kr(0); // ranges from -1,1
        var filter = SelectX.ar(
          freq.linlin(-0.01,0.01, 0,2),
          [
            RLPF.ar(sig, freq.lincurve(-1,0, 50,9500,8), \width.kr(1)),
            sig,
            RHPF.ar(sig, freq.lincurve(0,1, 50,9500,8), \width.kr(1)),
          ]);
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
      SynthDef(\touchOSCChorus, {
        var sig = In.ar(\out.kr(0), 2);
        var numDelays = 8;
        var numOutChan = 2;
        var mods = { |i|
            FSinOsc.kr(\speed.kr(1) * rrand(0.9, 1.1),
              \phase.kr(0) * i,
              \depth.kr(0.001),
              \predelay.kr(0.001));
        } ! (numDelays * numOutChan);
        sig = DelayL.ar(sig, 0.5, mods);
        sig = Mix(sig.clump(numOutChan)) / numDelays;
        ReplaceOut.ar(\out.kr(0), sig);
      }).add;
      SynthDef(\touchOSCDistortion, {
        var sig = In.ar(\out.kr(0), 2);
        var gain = \gain.kr(0).linlin(0,1,1,0.01);
        var distortion = \distortion.kr(0).linlin(0,1,-0.9,1);
        var dist = Select.ar(distortion > 0,
          // Bring the clipping plane closer to 0 as distortion approaches -1
          [sig.clip2(max(gain+distortion*gain, 0.01)),
          // For distortion > 0, only affect the negative side of the signal
          Select.ar(sig > 0, [
            // Gradually map negative signal values to abs value
            sig.linlin(-1,0,distortion.linlin(0,1,-1,1),0).clip2(gain),
            sig,
          ])
        ]);
        sig = XFade2.ar(sig, dist, \wet.kr(1).linlin(0,1,-1,1));
        ReplaceOut.ar(\out.kr(0), sig);
      }).add;
      Server.default.sync;
      monitorSynth = Synth(\touchOSCMonitor, [\bus, bus, \out, out], group, \addToTail);
      store.forceUpdate;
      delayStore.forceUpdate;
      filterStore.forceUpdate;
      reverbStore.forceUpdate;
      chorusStore.forceUpdate;
      distStore.forceUpdate;
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
    chorusSynth = nil;
    distSynth = nil;
    monitorSynth = nil;
    isRunning = false;
  }

  storeOn { |stream|
    stream << "TouchSynth.hydrate(" <<< name << ",";
    stream <<< store << ",";
    stream <<< delayStore << ",";
    stream <<< filterStore << ",";
    stream <<< reverbStore << ",";
    stream <<< chorusStore << ",";
    stream <<< distStore;
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
