SynthDef.new(\soprano,
	{|out=0, amp=1, sustain=1, pan=0, freq=440, fmod=0, rate=0|
	var osc, env;
	freq = [freq, freq+fmod];
	amp=(amp / 2);
	freq=Vibrato.kr(freq, (rate + 4));
	osc=(SinOsc.ar((freq * 3), mul: amp) + SinOscFB.ar((freq * 3), mul: (amp / 2)));
	env=EnvGen.ar(Env(times: [(sustain / 2), (sustain / 2)],levels: [0, amp, 0],curve: 'lin'), doneAction: Done.freeSelf);
	osc=(osc * env);
	osc = Mix(osc) * 0.5;
	osc = Pan2.ar(osc, pan);
	Out.ar(out, osc);
}).add;
