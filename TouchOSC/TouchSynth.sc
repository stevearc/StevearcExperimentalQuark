// SynthDataStore {
//   var <name=\default, <out=0, <>defaultArgs=#[],
//     <delayStore, <filterStore, <reverbStore;
//   *new {
//     ^super.new.init;
//   }
//   init {
//     name = theName;
//     defaultArgs = theArgs;
//     out = theOut;
//     delayStore = FXDelayDataStore.new;
//     filterStore = FXFilterDataStore.new;
//     reverbStore = FXReverbDataStore.new;
//   }
// }

TouchSynth {
  var <name, <synthName, <out, <>defaultArgs=#[], <group, <bus, <isRunning=false,
    <delayStore, <filterStore, <reverbStore,
    delaySynth, filterSynth, reverbSynth, monitorSynth;

  *new { |name=\default, synthName=\default, args=#[], out=0|
    ^super.new.init(name, synthName, args, out);
  }
  init { |theName, theSynthName, theArgs, theOut|
    name = theName;
    synthName = theSynthName;
    defaultArgs = theArgs;
    out = theOut;
    delayStore = FXDelayDataStore.new;
    delayStore.addDependant(this);
    filterStore = FXFilterDataStore.new;
    filterStore.addDependant(this);
    reverbStore = FXReverbDataStore.new;
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
      }
    );
  }

  makeSynth { |args|
    ^Synth(synthName, [\out, bus] ++ defaultArgs ++ args, group, \addToHead);
  }

  updateSynth { |synth, enabled ...args|
    if (enabled) {
      if (synth.isNil) {
        synth = Synth(synthName, [\out, bus] ++ defaultArgs ++ args, group, \addToHead);
        synth.register(true);
        this.changed(\note_start, synth.nodeID, defaultArgs ++ args);
      } {
        if (synth.isRunning and: args.notEmpty) {
          this.changed(\note_set, synth.nodeID, args);
          synth.set(*args);
        }
      }
      ^synth;
    } {
      if (synth.notNil and: {synth.isRunning}) {
        // Cut off after 100ms to avoid nodeID race conditions. If user plays a
        // note fast enough we could end up setting the gate to 0 before it
        // starts playing. In that case, the node will hang around and not free
        // itself.
        synth.set(\gate, -1.1);
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
      group = Group.new(Server.default, \addToTail);
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
      delayStore.process;
      filterStore.process;
      reverbStore.process;
    }).start;
  }

  stop {
    if (isRunning.not) {
      ^this;
    };
    [group, delaySynth, filterSynth, reverbSynth, monitorSynth].do { |node|
      if (node.notNil) {
        node.free;
      };
    };
    bus = nil;
    group = nil;
    delaySynth = nil;
    filterSynth = nil;
    reverbSynth = nil;
    monitorSynth = nil;
    isRunning = false;
  }
}
