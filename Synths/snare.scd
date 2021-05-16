// From http://cs.wellesley.edu/~cs203/pages/topics.html
SynthDef(\snare, {
  arg out=0, amp=0.8, pan=0.3;
  var ampEnv, freqSweep, noiseEnv, sine, noise, sig;

  // Envelopes
  ampEnv = EnvGen.kr(Env.new([0.5, 1, 0.5, 0], [0.005, 0.03, 0.10], [-4, -2, -4]));
  freqSweep = EnvGen.kr(Env.new([4000, 200, 180], [0.007, 0.1], [-3, -4]));
  noiseEnv = EnvGen.kr(Env.new([0, 1, 0.4, 0], [0.002, 0.05, 0.13], [-2, -2]), doneAction:2); // Noise lasts the longest so it frees synth

  // Snare thud
  sine =  SinOsc.ar(freqSweep, 0.8, ampEnv);

  // Simulates snare rattle
  noise = WhiteNoise.ar(0.2);
  noise = HPF.ar(noise, 200, 2);
  noise = BPF.ar(noise, 6900, 0.6, 3) + noise;
  noise = noise * noiseEnv;

  // Combine rattle and thud and distort slightly by clipping.
  sig = sine + noise;
  sig = sig.clip2(1) * amp;

  sig = Pan2.ar(sig, pan);

  Out.ar(out, sig);
}).add;
