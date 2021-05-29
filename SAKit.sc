// x: snare
// X: snare ghost note
// o: kick
// -: close hi-hat
// =: open hi-hat
// 1-4: toms (ascending)

SAKit {
  *default {
    Pkit.set($o, \instrument, 'kick_oto309', \rel, 0.5, \curve, -4);
    Pkit.set($-, \instrument, 'hihat1', \rel, 0.13);
    Pkit.set($=, \instrument, 'hihat1', \rel, 0.4);
    Pkit.set($x, \instrument, 'clapOto309', \rel, 0.4);
    Pkit.set($X, \instrument, 'clapOto309', \amp, 0.05, \pan, Pwhite(-0.1,0.1));
    Pkit.set($1, \instrument, 'sosTom', \freq, 53.midicps);
    Pkit.set($2, \instrument, 'sosTom', \freq, 55.midicps);
    Pkit.set($3, \instrument, 'sosTom', \freq, 57.midicps);
    Pkit.set($4, \instrument, 'sosTom', \freq, 59.midicps);
  }
}
