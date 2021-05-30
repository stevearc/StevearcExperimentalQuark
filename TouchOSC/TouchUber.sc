TouchUber : TouchOSCResponder {
  classvar <>default, numKeyboards=3;
  var <keyboardSynth, keys, fxboard, pads;
  *initClass {
    default = this.new;
  }
  *new {
    ^super.new.init;
  }
  init {
    keyboardSynth = TouchSynth.new;
    keys = Array.fill(numKeyboards, {|i| TouchKeyboard.new(i+1)});
    fxboard = TouchFXBoard.new;
    pads = TouchPads.new;
  }

  *setPadSynth { |x, y, touchSynth| ^this.default.setPadSynth(x,y,touchSynth) }
  setPadSynth { |x, y, touchSynth|
    pads.store.setPadSynth(x, y, touchSynth);
  }

  keyboardSynth_ { |newSynth|
    keyboardSynth.stop;
    keyboardSynth = newSynth;
    keyboardSynth.start;
    keys.do { |keyboard| keyboard.touchSynth = keyboardSynth };
  }

  *start { ^this.default.start }
  start {
    var selectSynth;
    if (isListening) {
      ^this;
    };
    super.start;
    keyboardSynth.start;
    fxboard.attach(keyboardSynth);
    fxboard.start;
    pads.start;
    keys.do { |keyboard|
      keyboard.touchSynth = pads.selectedSynth;
      keyboard.start;
    };
    selectSynth = {
      keys.do { |keyboard|
        keyboard.touchSynth = pads.selectedSynth;
      };
      fxboard.attach(pads.selectedSynth);
    };
    this.prAddFunc('/keys', selectSynth);
    this.prAddFunc('/fx', selectSynth);
  }

  *stop { ^this.default.stop }
  stop {
    if (isListening.not) {
      ^this;
    };
    super.stop;
    keyboardSynth.stop;
    fxboard.stop;
    pads.stop;
    keys.do { |keyboard|
      keyboard.stop;
    };
  }
}
