M {
  add {|idx|
    ^Mixer(idx);
  }
}

Mixer {
  classvar allMixers, <>defaultFadetime = 0.1;
  var <index, <>name, <synth, <>fadeTime, <bus;

  *initClass {
    allMixers = Array();
  }

  *new {|idx|
    var mixer, newSize;
    if (idx.isString) {
      ^allMixers.select({|m| m.name == idx})[0];
    };
    mixer = allMixers[idx];
    newSize = idx + 1;
    if (mixer.notNil) {
      ^mixer;
    };
    if (newSize > allMixers.size) {
      allMixers = allMixers.extend(newSize, nil);
    };
    mixer = super.new.init(idx);
    allMixers[idx] = mixer;
    ^mixer;
  }

  init {|idx|
    var addTarget;
    name = idx.asString;
    index = idx;
    fadeTime = defaultFadetime;
    bus = Bus.audio(numChannels: 2);
    if (SynthDescLib.global.synthDescs.at(\mixer).isNil) {
      Task({
        SynthDef(\mixer, {
          // Assumes 2 channels
          var sig = In.ar(\bus.kr(9), 2);

          sig = BLowShelf.ar(sig, \eqLoHz.kr(200), 1, \eqLo.kr(0));
          sig = BPeakEQ.ar(sig, \eqMidHz.kr(600), 1, \eqMid.kr(0));
          sig = BHiShelf.ar(sig, \eqHiHz.kr(1200), 1, \eqHi.kr(0));
          sig = sig * \amp.kr(1);

          Out.ar(\out.kr(0), sig);
        }, metadata: (
          specs: (
            out: \audiobus,
            bus: \audiobus,
            amp: ControlSpec(0, 1),
            eqLo: ControlSpec(-45, 20, step:1),
            eqLoHz: ControlSpec(20, 2000, \exp, 1),
            eqMid: ControlSpec(-45, 20, step:1),
            eqMidHz: ControlSpec(200, 800, \exp, 1),
            eqHi: ControlSpec(-45, 20, step:1),
            eqHiHz: ControlSpec(200, 16000, \exp, 1),
          )
        )).add;
        Server.default.sync;
        this.prAddSynth;
      }).start;
    } {
      this.prAddSynth;
    };
  }

  prAddSynth {
    var addTarget = this.prGetTarget;
    synth = Synth(\mixer,
      [\bus, bus],
      target: addTarget[0],
      addAction: addTarget[1]
    );
    synth.onFree({
      this.prCleanup;
    });
  }

  prGetTarget {
    var action = \addAfter;
    var target = Master.synth;
    allMixers.do {|m, i|
      if (i == index) {
        action = \addBefore;
      } {
        if (m.notNil) {
          ^[m.synth, action];
        };
      };
    };
    if (Master.synth.notNil) {
      ^[Master.synth, \addBefore];
    } {
      ^[RootNode(Server.default), \addToTail];
    };
  }

  *freeAll { |fadeTime = nil|
    allMixers.do({|m|
      if (m.notNil) {
        m.free(fadeTime);
      }
    });
  }

  gui {
    if (synth.isNil) {
      Task({
        1.wait;
        MixerGUI.makeGui("Mixer " ++ name, \mixer, synth);
      }).start(AppClock);
    } {
      MixerGUI.makeGui("Mixer " ++ name, \mixer, synth);
    }
  }

  free {|fadeTime = nil|
    if (fadeTime.isNil) {
      fadeTime = defaultFadetime;
    };
    if (fadeTime == 0) {
      synth.free;
      bus.free;
    } {
      synth.animate(\amp, nil, 0, fadeTime);
      Task({
        fadeTime.wait;
        synth.free();
        bus.free();
      }).start;
    };
  }

  prCleanup {
    synth = nil;
    bus = nil;
    allMixers[index] = nil;
  }

  value {
    ^bus;
  }

  solo {|muteOthers=true, fadeTime=0|
    allMixers.do {|m, i|
      if (i != index) {
        if (m.notNil) {
          m.mute(muteOthers, fadeTime);
        };
      };
    };
  }

  set {|...args|
    synth.set(*args);
  }

  mute {|shouldMute=true, fadeTime=0|
    var amp = 0;
    if (shouldMute.asBoolean.not) {
      amp = 1;
    };
    if (fadeTime > 0) {
      synth.animate(\amp, nil, amp, fadeTime);
    } {
      synth.set(\amp, amp);
    };
  }
}

+ ProxySpace {
  playM {|index|
    this.play(out: Mixer(index).bus);
  }
}

+ BusPlug {
  playM {|index|
    this.play(out: Mixer(index).bus);
  }
}
