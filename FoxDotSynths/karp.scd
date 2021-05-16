SynthDef.new(\karp,
	{|out=0, amp=1, sustain=1, pan=0, freq=440, fmod=0, rate=0|
	var osc, env;
	freq = [freq, freq+fmod];
	amp=(amp * 0.75);
	osc=LFNoise0.ar((400 + (400 * rate)), amp);
	osc=(osc * XLine.ar(1, 1e-06, (sustain * 0.1)));
	freq=((265 / (freq * 0.666)) * 0.005);
	osc=CombL.ar(osc, delaytime: freq, maxdelaytime: 2);
	env=EnvGen.ar(Env(times: [sustain],levels: [(amp * 1), (amp * 1)],curve: 'step'), doneAction: Done.freeSelf);
	osc=(osc * env);
	osc = Mix(osc) * 0.5;
	osc = Pan2.ar(osc, pan);
	Out.ar(out, osc);
}).add;
