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
    this.prAddFunc('/keys', {this.onPageChange('/keys')});
    this.prAddFunc('/fx', {this.onPageChange('/fx')});
    this.prAddFunc('/pads', {this.onPageChange('/pads')});
  }

  onPageChange { |page|
    keys.do { |keyboard|
      keyboard.touchSynth = pads.selectedSynth;
      keyboard.sync(true, true);
    };
    switch(page,
      '/keys', {
      },
      '/fx', {
        fxboard.attach(pads.selectedSynth);
        fxboard.sync(true, true);
      },
      '/pads', {
        pads.sync(true, true);
      },
    );
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
