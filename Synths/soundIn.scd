SynthDef(\soundIn, {
  |out=0, amp=1, pan=0, in=0|
  var sig = SoundIn.ar(in);
  sig = Compander.ar(sig, sig);
  sig = Pan2.ar(sig, pan);
  Out.ar(out, sig*amp);
}).add;

SynthDef(\soundIn1, {
  |out=0, amp=1, in=0|
  var sig = SoundIn.ar(in);
  sig = Compander.ar(sig, sig);
  Out.ar(out, sig*amp);
}, metadata: (
  proxyChannels: 1,
)).add;

// For getting the amplitude of a mic signal for later processing
SynthDef(\smoothAmp, {
  |out=0, threshold=0.1, smooth=1.0, lowCurve=4|
  // Default to 1.0 to smooth this out, otherwise the oscillations in this
  // amplitude itself will cause noise in things downstream
  var amp = Amplitude.kr(\in.ar(0), smooth, smooth);
  var ampenv = Env([0,threshold,1], [threshold,1-threshold], [lowCurve,0]);
  // This is to crush small amplitude values closer to 0, to avoid noisy
  // background when there's no mic input
  amp = IEnvGen.kr(ampenv, amp);
  Out.kr(out, amp);
}, metadata: (
  proxyRate: \control,
  specs: (
    \threshold: ControlSpec(0.01, 0.99),
    \smooth: ControlSpec(0.01, 2),
    \lowCurve: ControlSpec(0, 8, step: 1),
  )
)).add;

// Detects the frequency of the input signal. Only updates when amp is above
// threshold, and ignores frequencies outside the bounds.
SynthDef(\smoothFreq, {
  |out=0, amp=0, freqMin=60, freqMax=1000, threshold=0.1|
  var freq, hasFreq;
  # freq, hasFreq = Tartini.kr(\in.ar(0));
  freq = Gate.kr(freq, hasFreq * (freq < freqMax) * (freq > freqMin) * (amp > threshold));
  // Gate outputs 0 until it's triggered at least once, but that can cause
  // problems downstream. This will make sure we never output a 0 freq.
  freq = Select.kr(freq > 0, [38.midicps, freq]);
  Out.kr(out, freq);
}, metadata: (
  proxyRate: \control,
  specs: (
    \freqMin: ControlSpec(20, 20000, \exp, 1),
    \freqMax: ControlSpec(20, 20000, \exp, 1),
    \threshold: ControlSpec(0.01, 0.99),
  ),
)).add;
