// From https://sccode.org/1-5cS
SynthDef(\wobble, {
  arg out=0, wflo=1, wfhi=6, decay=0, gate=1, wfmax=8500, freq, iphase;
  var env = Linen.kr(gate, releaseTime: 0.01, doneAction: Done.freeSelf);
  var son = MoogVCF.ar(
    in: (
      Pulse.ar([freq * 0.98, freq], mul:0.5) +
      PinkNoise.ar(LFNoise0.ar(2).range(0, 1.0)) +
      Saw.ar([freq, freq * 1.025], mul:2)
    ).clip2(0.5),
    fco: LFCub.kr(
      freq:LFPulse.kr(0.25, iphase, width: 0.25).range(
        wflo, wfhi) ).exprange(40, wfmax),
    res: 0.4,
    mul:2
  );
  Out.ar(out, son * env);
}).add;
