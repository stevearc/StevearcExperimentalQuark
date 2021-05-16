SynthDef.new(\blip,
  {|out=0, amp=1, sustain=1, pan=0, freq=440, fmod=0, rate=0|
  var osc;
  freq = [freq, freq+fmod];
  amp=(amp + 1e-05);
  freq=(freq + [0, LFNoise2.ar(50).range(-2, 2)]);
  freq=(freq * 2);
  osc=((LFCub.ar((freq * 1.002), iphase: 1.5) + (LFTri.ar(freq, iphase: Line.ar(2, 0, 0, 2)) * 0.3)) * Blip.ar((freq / 2), rate));
  osc=((osc * XLine.ar(amp, (amp / 10000), (sustain * 2))) * 0.3);
  osc = Mix(osc) * 0.5;
  osc = Pan2.ar(osc, pan);
  DetectSilence.ar(osc, doneAction: Done.freeSelf);
  Out.ar(out, osc);
}).add;
