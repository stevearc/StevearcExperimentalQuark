SA {
  *setup { |callback|
    var s = Server.default;
    var p;
    if (currentEnvironment.class != ProxySpace) {
      p = ProxySpace.push(s);
      p.quant = 1;
    };
    SA.boot;
    s.waitForBoot({
      SA.load;
      Master.enable;
      if (callback.notNil) {
        Routine({
          s.sync;
          Routine(callback).play(AppClock);
        }).play(AppClock);
      };
    });
  }

  *load {
    if (SynthDescLib.global.synthDescs.at(\blip).isNil) {
      SA.loadMySynths;
    };
    if (SCLOrkSynths.isLoaded.not, {SCLOrkSynths.load});
    SAKit.default;
    SA.addDefaultSpecs;
  }

  *loadMySynths {
    var synthPaths;
    var folderPath = SA.getThisDir;
    synthPaths = PathName.new(folderPath ++ "/FoxDotSynths").files
      ++ PathName.new(folderPath ++ "/Synths").files;
    synthPaths.do {|p|
      p.fileName.postln;
      p.fullPath.asString.load;
    };
  }

  *boot {
    var s = Server.default;
    var memSize = 2 * (2**20); // 2 gigs
    if (s.serverRunning and: (s.options.memSize == memSize)) {
      ^nil;
    };
    s.options.memSize = memSize;
    s.options.numBuffers = 1024 * 16;
    if (s.serverRunning) {
      s.reboot;
    } {
      s.boot;
    }
  }

  *getThisDir {
    Quarks.installedPaths.do({ |p|
      var path = PathName.new(p);
      if( path.fileName.asString == "StevearcExperimentalQuark", {
        ^path.fullPath;
      })
    });
    ^nil;
  }

  *addDefaultSpecs {
    Spec.specs[\wet1] = ControlSpec(0, 1);
    Spec.specs[\wet2] = Spec.specs[\wet1];
    Spec.specs[\wet3] = Spec.specs[\wet1];
    Spec.specs[\wet4] = Spec.specs[\wet1];
    Spec.specs[\wet5] = Spec.specs[\wet1];
    Spec.specs[\fmod] = ControlSpec(0.001, 1, \exp);
    Spec.specs[\atk] = ControlSpec(0.001, 4, \exp);
    Spec.specs[\att] = Spec.specs[\atk];
    Spec.specs[\decay] = ControlSpec(0.001, 2, \exp);
    Spec.specs[\dec] = Spec.specs[\decay];
    Spec.specs[\sustain] = ControlSpec(0, 4);
    Spec.specs[\sus] = Spec.specs[\sustain];
    Spec.specs[\rel] = ControlSpec(0.001, 4, \exp);
    Spec.specs[\curve] = ControlSpec(-8, 8, \lin, 1);
    Spec.specs[\cAtk] = Spec.specs[\curve];
    Spec.specs[\cDec] = Spec.specs[\curve];
    Spec.specs[\cRel] = Spec.specs[\curve];

    // bitcrush FX
    Spec.specs[\bits] = ControlSpec(1, 24);
    Spec.specs[\crush] = ControlSpec(0, 44100, 2);
    // delay FX
    Spec.specs[\delay] = ControlSpec(0.001, 1, \exp);
    Spec.specs[\delaycount] = ControlSpec(2, 128, \exp, 1);
    Spec.specs[\delaydecay] = ControlSpec(0.1, 1);
    // reverb FX
    Spec.specs[\room] = ControlSpec(0, 1);
    Spec.specs[\roomdamp] = ControlSpec(0, 1);
    // tremolo FX
    Spec.specs[\tremolo] = ControlSpec(1, 128, \exp);
    // vibrato FX
    Spec.specs[\vibrato] = ControlSpec(1, 32);
    Spec.specs[\vibratodepth] = ControlSpec(0.02, 10, \exp);
    // filter FX
    Spec.specs[\hpf] = Spec.specs[\freq];
    Spec.specs[\lpf] = Spec.specs[\freq];
    Spec.specs[\bpf] = Spec.specs[\freq];
    Spec.specs[\bpfrq] = ControlSpec(0.1, 20);
    // formant FX
    Spec.specs[\formant] = ControlSpec(0, 8);
    // distortion FX
    Spec.specs[\dist] = ControlSpec(0, 1);
    Spec.specs[\distortion] = ControlSpec(0, 1);
    Spec.specs[\gain] = ControlSpec(0, 1);
    // wa-wa FX
    Spec.specs[\waCenter] = ControlSpec(40, 10000, \exp);
    Spec.specs[\waWidth] = ControlSpec(0, 0.8);
    Spec.specs[\waRate] = ControlSpec(0.1, 32, \exp);
    Spec.specs[\waRq] = ControlSpec(0.1, 5);
    // pan FX
    Spec.specs[\fxpan] = ControlSpec(0, 1);
    Spec.specs[\fxpanrate] = ControlSpec(0.1, 32, \exp);
    // flanger FX
    Spec.specs[\flangehz] = ControlSpec(0, 0.5);
    Spec.specs[\flangefb] = ControlSpec(0, 0.8);
    // Greyhole FX
    Spec.specs[\ghDelay] = ControlSpec(0.1, 60, \exp);
    Spec.specs[\ghFB] = ControlSpec(0, 1);
    Spec.specs[\ghDiff] = ControlSpec(0, 1);
    Spec.specs[\ghDamp] = ControlSpec(0, 5);
    Spec.specs[\ghMDepth] = ControlSpec(0, 1);
    Spec.specs[\ghMFreq] = ControlSpec(0, 10000, 2);
    // NHHall FX
    Spec.specs[\nhDecay] = ControlSpec(0.1, 60, \exp);
    Spec.specs[\nhStereo] = ControlSpec(0, 1);
    Spec.specs[\nhLowFreq] = ControlSpec(20, 2000, \exp);
    Spec.specs[\nhLowRatio] = ControlSpec(0.1, 5);
    Spec.specs[\nhHiFreq] = ControlSpec(200, 10000, \exp);
    Spec.specs[\nhHiRatio] = ControlSpec(0.1, 5);
    Spec.specs[\nhEarlyDiffusion] = ControlSpec(0, 1);
    Spec.specs[\nhLateDiffusion] = ControlSpec(0, 1);
    Spec.specs[\nhModRate] = ControlSpec(0, 1);
    Spec.specs[\nhModDepth] = ControlSpec(0, 1);
    // EQ FX
    Spec.specs[\eqLo] = ControlSpec(-45, 20, step:1);
    Spec.specs[\eqLoHz] = ControlSpec(20, 300, \exp);
    Spec.specs[\eqMid] = ControlSpec(-45, 20, step:1);
    Spec.specs[\eqMidHz] = ControlSpec(200, 800, \exp);
    Spec.specs[\eqHi] = ControlSpec(-45, 20, step:1);
    Spec.specs[\eqHiHz] = ControlSpec(600, 16000, \exp);
    // Phaser FX
    Spec.specs[\phMaxdelay] = ControlSpec(0.01, 1, \exp);
    Spec.specs[\phMindelay] = ControlSpec(0.001, 1, \exp);
    Spec.specs[\phDecay] = ControlSpec(0.1, 5, \exp);
    Spec.specs[\phRate] = ControlSpec(0.1, 100, \exp);
    // Chorus FX
    Spec.specs[\chPhase] = ControlSpec(0, 2pi);
    Spec.specs[\chSpeed] = ControlSpec(0.001, 10, \exp);
    Spec.specs[\chDepth] = ControlSpec(0.0001, 0.25, \exp);
    Spec.specs[\chPredelay] = ControlSpec(0.0001, 0.2, \exp, 0, 0.001);
    // Filtered Chorus FX
    Spec.specs[\chCrossover] = Spec.specs[\freq];
    Spec.specs[\chXfade] = ControlSpec(-1, 1);

  }

  *quit {
    Routine({
      if (currentEnvironment.class == ProxySpace) {
        currentEnvironment.clear(1);
        1.wait;
        currentEnvironment.pop;
      };
      2.wait;
      Server.default.quit;
    }).play;
  }
}
