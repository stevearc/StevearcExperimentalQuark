SynthDef.new(\piano,
	{|out=0, amp=0.2, sustain=1, pan=0, freq=440, fmod=0|
	var osc, env;
	freq = [freq, freq+fmod];
	osc=MdaPiano.ar((freq[0]), vel: (40 + (amp * 60)), decay: (sustain / 4));
	env=EnvGen.ar(Env(times: [sustain],levels: [(amp * 1), (amp * 1)],curve: 'step'), doneAction: Done.freeSelf);
	osc=(osc * env);
	osc = Mix(osc) * 0.5;
	osc = Pan2.ar(osc, pan);
	Out.ar(out, osc)
}).add;
