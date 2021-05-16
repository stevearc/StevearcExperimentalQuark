SynthDef(\fm, {
  |out=0, freq=440, mRatio=1, cRatio=1, harmonics=1, hScale=5, atk=0.1, rel=0.3, cAtk=4, cRel=(-4), pan=0, amp=0.1|
  var sig, mod;
  var env = EnvGen.kr(Env.perc(atk, rel, curve:[cAtk,cRel]), doneAction: Done.freeSelf);
  var hEnv = EnvGen.kr(Env([harmonics,harmonics*hScale,harmonics], [atk,rel], [cAtk,cRel]));
  mod = SinOsc.ar(freq * mRatio, mul:freq*mRatio*hEnv);
  sig = amp * env * SinOsc.ar(freq * cRatio + mod);
  sig = Pan2.ar(sig, pan);
  Out.ar(out, sig);
}).add;
