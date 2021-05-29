FXFilterDataStore {
  var <enabled=false, <wet=1, <freq=440, <width=1;

  enabled_ { |newval|
    if (newval != enabled) {
      enabled = newval;
      this.process;
    }
  }
  wet_ { |newval|
    wet = newval;
    this.process;
  }
  freq_ { |newval|
    freq = newval;
    this.process;
  }
  width_ { |newval|
    width = newval;
    this.process;
  }

  process {
    this.changed(\fxfilter);
  }
}

TouchFXFilter : TouchOSCResponder {
  var store, touchSynth, synth;
  *new { |touchSynth|
    ^super.new.init(touchSynth);
  }
  init { |tSynth|
    touchSynth = tSynth;
    store = FXFilterDataStore.new;
    store.addDependant(this);
    this.prAddChild(TouchControlToggle(store, \enabled, '/fx/filter/toggle'));
    this.prAddChild(TouchControlRange(store, \wet, '/fx/filter/wet', [-1,1]));
    this.prAddChild(TouchControlRange(store, \freq, '/fx/filter/freq', [60,16000,6]));
    this.prAddChild(TouchControlRange(store, \width, '/fx/filter/width', [0.1,1]));
  }

  update { |store, what|
    synth = touchSynth.updateFXSynth(synth,
      \touchOSCFilter, store.enabled,
      \wet, store.wet,
      \freq, store.freq,
      \width, store.width,
    );
    this.sync;
  }

  listen {
    super.listen;
    SynthDef(\touchOSCFilter, {
      var sig = In.ar(\out.kr(0), 2);
      // Boost the volume on the lows when we're doing a LPF
      var amp = \freq.kr.lincurve(60,440,1.5,1,-1);
      var filter = BPF.ar(sig, \freq.kr, \width.kr, amp);
      sig = XFade2.ar(sig, filter, \wet.kr);
      ReplaceOut.ar(\out.kr(0), sig);
    }).add;
  }

  free {
    super.free;
    store.enabled = false;
  }
}
