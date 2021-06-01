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
    Spec.specs[\wet2] = ControlSpec(0, 1);
    Spec.specs[\wet3] = ControlSpec(0, 1);
    Spec.specs[\wet4] = ControlSpec(0, 1);
    Spec.specs[\wet5] = ControlSpec(0, 1);
    Spec.specs[\amp] = ControlSpec(0, 1);
    Spec.specs[\detune] = ControlSpec(0.001, 0.1, \exp);
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
    Spec.specs[\hpf] = ControlSpec(20, 20000, \exp, 1);
    Spec.specs[\lpf] = ControlSpec(20, 20000, \exp, 1);
    Spec.specs[\bpf] = ControlSpec(20, 10000, \exp, 1);
    Spec.specs[\bpfrq] = ControlSpec(0.1, 20);
    // formant FX
    Spec.specs[\formant] = ControlSpec(0, 8);
    // distortion FX
    Spec.specs[\dist] = ControlSpec(0, 1);
    // overdrive distortion FX
    Spec.specs[\drive] = ControlSpec(0, 5);
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
