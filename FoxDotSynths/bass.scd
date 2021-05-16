SynthDef.new(\bass,
	{|out=0, amp=1, sus=1, pan=0, freq=440, fmod=0, rate=8, blur=1|
	var osc, env;
	sus = sus * blur;
	freq = [freq, freq+fmod];
	freq=(freq / 4);
	amp=(amp * 0.8);
	osc=((LFTri.ar(freq, mul: amp) + VarSaw.ar(freq, width: (rate / 10), mul: amp)) + SinOscFB.ar(freq, mul: (amp / 2)));
	env=EnvGen.ar(Env.perc(attackTime: 0.02,releaseTime: sus,level: amp,curve: 'lin'), doneAction: Done.freeSelf);
	osc=(osc * env);
	osc = Mix(osc) * 0.5;
	osc = Pan2.ar(osc, pan);
  Out.ar(out, osc);
}).add;
