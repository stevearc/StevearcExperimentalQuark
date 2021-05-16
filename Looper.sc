Looper {
  classvar <default;
  var <>tracks, <>synths;
  *initClass {
    default = this.new;
  }
  *new {
    ^super.new.init;
  }
  init {
    tracks = IdentityDictionary.new;
    synths = IdentityDictionary.new;
  }

  *loadSynthDef { Looper.default.loadSynthDef }
  loadSynthdef {
    if (SynthDescLib.global.synthDescs.at(\loopRecord).isNil) {
      SynthDef(\loopRecord, { |in=0, out=0, buf=0, run=0|
        RecordBuf.ar(In.ar(in, 2), buf, run: run, doneAction: Done.freeSelf, loop: 0);
      }).add;
      SynthDef(\loopPlay,
        {|out=0, buf=0, amp=1.0, loop=0|
          var sig = PlayBuf.ar(2, buf, BufRateScale.ir(buf), loop: loop, doneAction:2);
          Out.ar(out, sig * amp);
      }).add;
    }
  }

  *buf {|key| ^Looper.default.buf(key)}
  buf {|key|
    ^tracks[key];
  }

  *play {|key, loop=1| ^Looper.default.play(key, loop)}
  play {|key, loop=1|
    if (synths[key].isNil or: synths[key].isPlaying.not) {
      synths[key] = Synth(\loopPlay, [\buf, tracks[key], \loop, loop]);
      NodeWatcher.register(synths[key]);
    };
    ^synths[key];
  }

  *stop {|key| Looper.default.stop(key)}
  stop {|key|
    if (synths[key].notNil and: synths[key].isPlaying) {
      synths[key].set(\loop, 0);
      synths[key] = nil;
    }
  }

  *start {|key, beats=4, quant=nil, bus=0, server=nil, clock=nil, callback=nil|
    Looper.default.start(key, beats, quant, bus, server, clock, callback)}
  start {|key, beats=4, quant=nil, bus=0, server=nil, clock=nil, callback=nil|
    this.record(key, beats, quant, bus, server, clock, {
      this.play(key);
      if (callback.notNil) {
        callback.value;
      };
    });
  }

  *record {|key, beats=4, quant=nil, bus=0, server=nil, clock=nil|
    Looper.default.record(key, beats, quant, bus, server, clock)}
  record {|key, beats=4, quant=nil, bus=0, server=nil, clock=nil, callback=nil|
    var buf, time, synth;
    if (server.isNil) {
      server = Server.default;
    };
    if (clock.isNil) {
      clock = TempoClock.default;
    };
    if (quant.isNil) {
      quant = beats;
    };
    if (tracks[key].notNil) {
      this.free(key);
    };
    time = beats / clock.tempo;
    buf = Buffer.alloc(server, server.sampleRate * time, 2);
    tracks[key] = buf;
    Task({
      var recTime, countdownTime;
      this.loadSynthdef;
      server.sync;
      synth = Synth(\loopRecord, [\in, bus, \buf, buf], target: RootNode(server), addAction: \addToTail);
      server.sync;
      countdownTime = clock.nextTimeOnGrid(quant, -5);
      clock.schedAbs(countdownTime, {
        Task({
          5.do {|i|
            (5 - i).postln;
            1.wait;
          };
          beats.wait;
          "Done".postln;
        }).start;
      });
      clock.schedAbs(countdownTime + 5, {
        synth.set(\run, 1);
        "Recording".postln;
        if (callback.notNil) {
          clock.sched(beats, callback);
        }
      });
    }).start;
  }

  *free {|key| Looper.default.free}
  free {|key|
    if (synths[key].notNil) {
      synths[key].free;
    };
    tracks[key].free;
    tracks[key] = nil;
  }
}

+ NodeProxy {
  loop {|key, beats=4, quant=nil, server=nil, clock=nil|
    Looper.start(key, beats, quant, this.bus, server, clock, {
      this.stop(0);
    });
  }

  record {|key, beats=4, quant=nil, server=nil, clock=nil|
    Looper.record(key, beats, quant, this.bus, server, clock);
  }
}
