TouchUber : TouchOSCResponder {
  classvar <>default, numKeyboards=3;
  var keys, fxboard, pads;
  *initClass {
    default = this.new;
  }
  *new {
    ^super.new.init;
  }
  init {
    keys = Array.fill(numKeyboards, {|i| TouchKeyboard.new(i+1)});
    keys.do { |keyboard|
      this.prAddChild(keyboard);
    };
    fxboard = TouchFXBoard.new;
    this.prAddChild(fxboard);
    pads = TouchPads.new;
    this.prAddChild(pads);
  }

  *setPadSynth { |row, col, touchSynth| ^this.default.setPadSynth(row,col,touchSynth) }
  setPadSynth { |row, col, touchSynth|
    pads.store.setPadSynth(row, col, touchSynth);
  }

  *start { ^this.default.start }
  start {
    if (isListening) {
      ^this;
    };
    super.start;
    fxboard.attach(pads.selectedSynth);
    keys.do { |keyboard|
      keyboard.touchSynth = pads.selectedSynth;
    };
    CmdPeriod.doOnce { this.stop };
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
}
