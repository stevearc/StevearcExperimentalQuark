(
SynthDef.new(\pads,
	{|out=0, amp=1, sus=1, pan=0, freq=440, fmod=0, rate=0, atk=0.1|
		var osc, env, filter;
		freq = [freq, freq+fmod];
		amp=(amp * 2);
		freq = freq * [1, 1.01];

		osc = SinOsc.ar(Line.kr(freq * 4, freq, 0.01) + (Line.kr(10, (freq * 2), sus) * Pulse.ar(freq * rate, width: SinOsc.kr(0.225/sus))), mul:0.5);

		env = EnvGen.ar(Env([0, 0.8, 0.5, 0],[atk, 0.5 * sus, 0.7 * sus]), doneAction: Done.freeSelf);

		osc = RHPF.ar(osc, [Line.kr(400, 1000, sus), Line.kr(4000,900,sus)], 0.5);

		osc = osc * env * amp * Line.ar(0.001,1,sus * 0.05);

		osc = Mix(osc) * 0.5;
		osc = Pan2.ar(osc, pan);
		Out.ar(out, osc)}).add;
)