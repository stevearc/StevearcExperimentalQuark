TouchSynth : TouchOSCResponder {
  var <>name=\default, <>bus=0, <>defaultArgs=#[], <group;
  *new {
    ^super.new.init;
  }
  init {
    this.prAddChild(TouchFXDelay.new(this));
    this.prAddChild(TouchFXFilter.new(this));
  }

  play { |freq|
    ^Synth(name, [\freq, freq, \out, bus *defaultArgs], group, \addToHead);
  }

  updateSynth { |synth, enabled ...args|
    if (enabled) {
      if (synth.isNil) {
        ^Synth(name, [\out, bus] ++ defaultArgs ++ args, group, \addToHead);
      } {
        synth.set(*args);
        ^synth;
      }
    } {
      if (synth.notNil) {
        // Cut off after 100ms to avoid race conditions. If user plays a note
        // fast enough we could end up setting the gate to 0 before it starts
        // playing. In that case, the node will hang around and not free
        // itself.
        synth.set(\gate, -1.1);
      }
      ^nil;
    }
  }

  updateFXSynth { |synth, name, enabled ...args|
    if (enabled) {
      if (synth.isNil) {
        ^Synth(name, [\out, bus] ++ args, group, \addToTail);
      } {
        synth.set(*args);
        ^synth;
      };
    } {
      if (synth.notNil) {
        synth.free;
      }
      ^nil;
    };
  }

  listen {
    super.listen;
    group = Group.new(Server.default, \addToTail);
  }

  free {
    super.free;
    group.free;
    group = nil;
  }
}
