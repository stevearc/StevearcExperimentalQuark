(
SynthDef(\sawbass, {
  |out=0, amp=0.1, pan=0, freq=90, atk=0.01, decay=0.1, rel=0.2, sustainLevel=0.5, cAtk=(-2), cDec=(-2), cRel=(-4), gate=1,
  detune=0.001, lpfscale=2, freqFade=0, lpfscaleFade=0|
  var sig, env;
  freq = freq.varlag(freqFade);
  env = EnvGen.kr(
    Env.adsr(atk, decay, sustainLevel, rel, curve: [cAtk,cDec,cRel]),
    gate: gate,
    doneAction: Done.freeSelf);

  // Detuned saws one octave lower
  sig = Saw.ar(8.collect {|i|
    (freq/2) + (freq*detune*(i-4))
  });
  // Detuned saws
  sig = sig + (0.5 * Saw.ar(8.collect {|i|
    freq + (freq*detune*(i-4))
  }));
  // Detuned saws at the fifth
  sig = sig + (0.3 * Saw.ar(8.collect {|i|
    (3*freq/2) + (freq*detune*(i-4))
  }));

  sig = sig + Greyhole.ar(sig,
    delayTime: 0.4,
    feedback: 0.8,
    diff: 0.4,
    damp: 0.1,
    modDepth: 0.9,
    modFreq: 0.3,
  );

  sig = Splay.ar(sig * env, 0.7);
  sig = RLPF.ar(sig, lpfscale.varlag(lpfscaleFade)*freq*Linen.kr(gate,atk,1,rel,0),0.4);
  sig = Balance2.ar(sig[0], sig[1], pan, amp);
  Out.ar(out, sig);
}).add;
)
