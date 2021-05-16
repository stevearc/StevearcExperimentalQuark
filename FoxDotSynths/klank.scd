SynthDef.new(\klank, {|out=0, amp=1, sustain=1, pan=0, freq=440, fmod=0|
	var osc, env;
	freq = [freq, freq+fmod];
	osc=Klank.ar(`[[1, 2, 3, 4], [1, 1, 1, 1], [2, 2, 2, 2]], ClipNoise.ar(0.0005).dup, freq);
	env=EnvGen.ar(Env(times: sustain/2,levels: [0, amp, 0],curve: 'lin'), doneAction: Done.freeSelf);
	osc=(osc * env);
	osc = Mix(osc) * 0.5;
	osc = Pan2.ar(osc, pan);
	Out.ar(out, osc)
}).add;
