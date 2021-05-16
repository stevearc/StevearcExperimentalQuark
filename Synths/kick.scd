// From http://cs.wellesley.edu/~cs203/pages/topics.html
SynthDef(\kick, {
  arg out = 0, amp = 0.9, pan = 0;
  var ampEnv, freqSweep, env1m, sig;

  ampEnv =  EnvGen.kr(Env([0, 1, 0.5, 0], [0.004, 0.065, 0.26], [-3, -2, -4]), doneAction:2);
  freqSweep = EnvGen.kr(Env.new([4000, 50, 30], [0.005, 0.29], [-4, -5]));

  // Use two oscillators for the bass drum: square wave and sine wave
  // Square wave needs to pass through LPF to get thud
  // Could also add some white noise if desired
  sig = LFPulse.ar(freqSweep);
  sig = LPF.ar(sig, freqSweep*1.5, ampEnv);
  sig = sig + SinOsc.ar(freqSweep, 0.5, ampEnv);

  // Distort it some by clipping it.
  sig = sig * 1.2;
  sig = sig.clip2(1) * amp;

  sig = Pan2.ar(sig, pan);

  Out.ar(out, sig);
}).add;
