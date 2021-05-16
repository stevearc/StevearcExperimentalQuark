SynthDef.new(\charm,
	{|out=0, amp=1, sustain=1, pan=0, freq=440, fmod=0, rate=0|
	var osc, env;
	freq = [freq, freq+fmod];
	freq=(freq + [0, 2]);
	freq=(freq * [1, 2]);
	osc=(SinOsc.ar(freq, mul: (amp / 4)) + VarSaw.ar((freq * 8), 10, mul: (amp / 8)));
	osc=LPF.ar(osc, SinOsc.ar(Line.ar(1, (rate * 4), (sustain / 8)), 0, (freq * 2), ((freq * 2) + 10)));
	env=EnvGen.ar(Env.perc(attackTime: 0.01,releaseTime: sustain,level: amp,curve: 0), doneAction: Done.freeSelf);
	osc=(osc * env);
	osc = Mix(osc) * 0.5;
	osc = Pan2.ar(osc, pan);
	Out.ar(out, osc);
}).add;
