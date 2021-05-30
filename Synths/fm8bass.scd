// From grirgz https://sccode.org/1-57S
SynthDef(\fm8bass, { arg out=0, amp=0.1, gate=1, pan=0, freq=200;
  var sig;
  var sig1, sig2, sig3, sig4, sig5, sig6, sig7, sig8;
  var env1, env2, env3, env4, env5, env6, env7, env8;
  freq = freq / 4;
  freq = freq * ((0..1)/1 - 0.5 * 0.0007 + 1);
  env1 = EnvGen.kr(Env([0,1,0.051,0],[0.001,0.01,0.8], [4,-8]), 1);
  env2 = EnvGen.kr(Env([0,1,0.051,0],[0.005,0.5,1.5], [0,-8], releaseNode:2), 1);
  env3 = EnvGen.kr(Env([0,1,1,0],[0.01,0.01,0.2], [0,0,-4], releaseNode:2), gate);
  env4 = EnvGen.kr(Env([0,1,0],[0.002,2.8], [0,-4]), 1);
  env5 = EnvGen.kr(Env([0,1,1,0],[0.001,0.1,0.8], [4,0,-4], releaseNode:2), gate);
  env6 = EnvGen.kr(Env([0,1,0],[0.001,3.0], [0,-4]), 1);
  //freq = freq * EnvGen.kr(Env([1,1.002,0.998,1],[0.1,0.8]), 1);
  sig1 = SinOsc.ar(freq * 11 + 0) * env1;
  sig2 = SinOsc.ar(freq * 6 * ( sig1 * 2.5 + 1 )) * env2;
  sig3 = SinOsc.ar(freq * 2 * 1 + 0) * env3;
  sig4 = SinOsc.ar(freq * 1 * ( sig3 * 2.5 + 1 ) + 0) * env4;
  sig5 = SinOsc.ar(freq * 1 * ( sig2 * 2.5 + 1 ) * (sig4 * 2.5 + 1)) * env5;
  sig6 = SinOsc.ar(freq * 2) * env6;
  //sig = sig2 + sig3 + sig4 + sig5 + sig6;
  sig = [sig1, sig2, sig3, sig4, sig5, sig6] * DC.ar([0.0, 0.0, 0.0,  0.0, 0.5, 0.5]);
  sig = sig /2;
  sig = sig.flop.sum;
  sig = sig * EnvGen.ar(\adsr.kr( Env.adsr(0.001,0,1,0.01, 1,-1) ),gate,doneAction:2);
  sig = sig * AmpComp.kr(freq);
  sig = Pan2.ar(sig, pan + [ 0, 0, 0, 0, 0, 0], amp).sum;
  Out.ar(out, sig);
}).add;