SynthDef(\fdpluck,
	{|out=0, amp=1, sustain=1, pan=0, freq=440, fmod=0|
	var osc, env;
	freq = [freq, freq+fmod];
	amp=(amp + 1e-05);
	freq=(freq + [0, LFNoise2.ar(50).range(-2, 2)]);
	osc=((SinOsc.ar((freq * 1.002), phase: VarSaw.ar(freq, width: Line.ar(1, 0.2, 2))) * 0.3) + (SinOsc.ar(freq, phase: VarSaw.ar(freq, width: Line.ar(1, 0.2, 2))) * 0.3));
	osc=((osc * XLine.kr(amp, (amp / 10000), sustain, doneAction: Done.freeSelf)) * 0.3);
	osc = Mix(osc) * 0.5;
	osc = Pan2.ar(osc, pan);
	Out.ar(out, osc);
}).add;
