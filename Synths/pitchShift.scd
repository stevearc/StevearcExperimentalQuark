SynthDef(\pitch1, {
  var sig = \in.ar(0);
  var tones = [\shift.kr(0).midiratio];
  sig = PitchShift.ar(sig, \window.kr(0.02), tones, 0, \pitchDispersion.kr(0));
  sig = Pan2.ar(sig, \pan.kr(0));
  Out.ar(\out.kr(0), \amp.kr(1)*sig);
}, metadata: (
  specs: (
    \shift: ControlSpec(-12, 12, \lin, 1),
    \window: ControlSpec(0.01, 1, \exp),
    \pitchDispersion: ControlSpec(0, 1, 2),
  )
)).add;

SynthDef(\pitch3, {
  var sig = \in.ar(0);
  var tones = [\harmonic0.kr(-3).midiratio,\harmonic1.kr(0).midiratio,\harmonic2.kr(9).midiratio];
  var weights = [\weight0.kr(1),\weight1.kr(1),\weight2.kr(1)].normalizeSum;
  sig = PitchShift.ar(sig, \window.kr(0.02), tones, 0, \pitchDispersion.kr(0));
  sig = Mix(weights * sig) / tones.size;
  sig = Pan2.ar(sig, \pan.kr(0));
  Out.ar(\out.kr(0), \amp.kr(1)*sig);
}, metadata: (
  specs: (
    \harmonic0: ControlSpec(-12, 12, \lin, 1),
    \harmonic1: ControlSpec(-12, 12, \lin, 1),
    \harmonic2: ControlSpec(-12, 12, \lin, 1),
    \weight0: ControlSpec(1, 10, \lin, 1),
    \weight1: ControlSpec(1, 10, \lin, 1),
    \weight2: ControlSpec(1, 10, \lin, 1),
    \window: ControlSpec(0.01, 1, \exp),
    \pitchDispersion: ControlSpec(0, 1, 2),
  )
)).add;
