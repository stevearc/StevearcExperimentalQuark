SynthDef.new(\keys, {
	|out=0, amp=0.2, sustain=1, pan=0, freq=440, fmod=0, rate=0|
	var osc, env;

	freq = [freq, freq+fmod];
	freq = freq * [1, 1.005];

	osc = SinOsc.ar(freq + (rate * 100) * XFade2.ar(LFPar.ar(freq * (1/2)), LFTri.ar(freq * 1)), phase: 0.2, mul: 0.8);
	osc = RHPF.ar(osc, 400, 3);
	env = EnvGen.ar(Env([0, 1, 0.9, 0.5, 0],[0.01, 0, 0.5 * sustain, 1 * sustain]), doneAction: Done.freeSelf);
	osc = Mix(osc) * env * amp;
	osc = Pan2.ar(osc, pan);
	Out.ar(out, osc);
}).add;
