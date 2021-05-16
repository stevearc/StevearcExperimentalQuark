SynthDef.new(\spark,
	{|out=0, amp=1, sustain=1, pan=0, freq=440, fmod=0|
	var osc;
	freq = [freq, freq+fmod];
	amp=(amp + 1e-05);
	freq=(freq + [0, LFNoise2.ar(50).range(-2, 2)]);
	osc=((LFSaw.ar((freq * 1.002), iphase: Saw.ar(0.1)) * 0.3) + (LFSaw.ar(freq, iphase: Saw.ar(0.1)) * 0.3));
	osc=(((osc * Line.ar(amp, (amp / 10000), (sustain))) * 0.3) * Line.ar(0.01, 1, (sustain * 0.033)));
	osc = Mix(osc) * 0.5;
	osc = Pan2.ar(osc, pan);
	DetectSilence.ar(osc, doneAction: Done.freeSelf);
	Out.ar(out, osc);
}).add;
