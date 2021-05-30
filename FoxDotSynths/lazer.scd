SynthDef.new(\lazer,
	{|out=0, amp=0.1, sus=1, pan=0, freq=440, fmod=0, rate=0, blur=1|
	var osc, env;
	sus = sus * blur;
	freq = [freq, freq+fmod];
	freq=(freq * [1, 1.005]);
	osc=(VarSaw.ar(freq, width: ((rate - 1) / 4)) + LFSaw.ar(LFNoise0.ar((rate * 20), add: (freq * Pulse.ar(((rate - 2) + 0.1), add: 1)), mul: 0.5)));
	env=EnvGen.ar(Env.perc(attackTime: 0.1,releaseTime: sus,level: amp,curve: 0), doneAction: Done.freeSelf);
	osc=(osc * env);
	osc = Mix(osc) * 0.5;
	osc = Pan2.ar(osc, pan);
	Out.ar(out, osc);
}).add;