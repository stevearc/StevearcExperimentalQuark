// x: snare
// X: snare ghost note
// o: kick
// -: close hi-hat
// =: open hi-hat
// 1-4: toms (ascending)

SAKit {
  *default {
    Pkit.default.set($o, \instrument, 'kick_oto309', \rel, 0.5, \curve, -4);
    Pkit.default.set($-, \instrument, 'hihat1', \rel, 0.13);
    Pkit.default.set($=, \instrument, 'hihat1', \rel, 0.4);
    Pkit.default.set($x, \instrument, 'clapOto309', \rel, 0.4);
    Pkit.default.set($X, \instrument, 'clapOto309', \amp, 0.05, \pan, Pwhite(-0.1,0.1));
    Pkit.default.set($1, \instrument, 'sosTom', \freq, 53.midicps);
    Pkit.default.set($2, \instrument, 'sosTom', \freq, 55.midicps);
    Pkit.default.set($3, \instrument, 'sosTom', \freq, 57.midicps);
    Pkit.default.set($4, \instrument, 'sosTom', \freq, 59.midicps);
  }
}
