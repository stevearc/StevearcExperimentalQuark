TouchUber : TouchOSCResponder {
  classvar <>default, numKeyboards=3;
  var <isEnabled=false, <touchSynth, keys;
  *initClass {
    default = this.new;
  }
  *new {
    ^super.new.init;
  }
  init {
    touchSynth = TouchSynth.new;
    keys = Array.fill(numKeyboards, {|i| TouchKeyboard.new(i+1, touchSynth)});
  }

  touchSynth_ { |newSynth|
    touchSynth = newSynth;
    keys.do { |keyboard| keyboard.touchSynth = touchSynth };
  }

  *enable { ^this.default.enable }
  enable {
    if (isEnabled) {
      ^this;
    };
    touchSynth.listen;
    keys.do { |keyboard| keyboard.listen };
    isEnabled = true;
  }

  *disable { ^this.default.disable }
  disable {
    this.free;
    touchSynth.free;
    keys.do { |keyboard|
      keyboard.free;
    };
    isEnabled = false;
  }
}
