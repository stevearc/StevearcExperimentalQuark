TouchBuiltinKeys {
  classvar <>default;
  var <>mixerID, <oscfuncs, <>synth, synths;
  *initClass {
    default = this.new(0);
  }
  *new { |mixerID|
    ^super.new.init(mixerID);
  }
  init { |mixerID, synth=\default|
    oscfuncs = Array.new;
    this.mixerID = mixerID;
    this.synth = synth;
    synths = IdentityDictionary.new;
  }

  *enable { ^this.default.enable }
  enable {
    var func;
    if (oscfuncs.notEmpty) {
      ^this;
    };
    12.do { |i|
      func = OSCFunc({
        |msg, time, addr, port|
        var gate = msg[1];
        if (synths[i].notNil) {
          // Cut off after 100ms to avoid race conditions. If user plays a note
          // fast enough we could end up setting the gate to 0 before it starts
          // playing. In that case, the node will hang around and not free
          // itself.
          synths[i].set(\gate, -1.1);
          synths[i] = nil;
        };
        if (gate != 0) {
          var freq = (60+i).midicps;
          synths[i] = Synth(synth, [\freq, freq]);
        };
      }, "/1/push%".format(i+1));
      oscfuncs = oscfuncs.add(func);
    }
  }

  *disable { ^this.default.disable }
  disable {
    oscfuncs.do { |f|
      f.free;
    };
    oscfuncs = Array.new;
    synths.do { |s|
      s.set(\gate, 0);
    };
    synths = IdentityDictionary.new;
  }

}
