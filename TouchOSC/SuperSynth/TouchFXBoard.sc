TouchFXBoard : TouchOSCResponder {
  var reverb, delay, filter, chorus, distortion, synthUI;
  *new {
    ^super.new.init;
  }
  init {
    reverb = TouchFXReverb.new;
    this.prAddChild(reverb);
    delay = TouchFXDelay.new;
    this.prAddChild(delay);
    filter = TouchFXFilter.new;
    this.prAddChild(filter);
    chorus = TouchFXChorus.new;
    this.prAddChild(chorus);
    distortion = TouchFXDistortion.new;
    this.prAddChild(distortion);
    synthUI = SynthParams.new;
    this.prAddChild(synthUI);
  }

  attach { |touchSynth|
    synthUI.store = touchSynth.store;
    reverb.store = touchSynth.reverbStore;
    delay.store = touchSynth.delayStore;
    filter.store = touchSynth.filterStore;
    chorus.store = touchSynth.chorusStore;
    distortion.store = touchSynth.distStore;
  }
}

SynthParams : TouchStoreUI {
  classvar fadersPerColumn=#[8,8];
  getChildren {
    var ret = Array.new;
    var idx = 0;
    fadersPerColumn.do { |numFaders, i|
      var col = i + 1;
      var n = idx;
      ret = ret.add(
        TouchControlLabel("/synth/params%/visible".format(col), store, {store.numControls > n}),
      );
      numFaders.do { |j|
        var row = j + 1;
        var n = idx;
        ret = ret.add(
          TouchControlLabel("/synth/params%/label/%".format(col, row), store, {store.specLabel(n)}),
        );
        ret = ret.add(
          TouchControlRange("/synth/params%/%".format(col, row), store, {store.specValue(n)}, store.specRange(n), {|val| store.setValue(n, val)}),
        );
        ret = ret.add(
          TouchControlButton("/synth/params%/reset/%".format(col, row), store, {store.resetValue(n)}),
        );
        idx = idx + 1;
      };
    };
    ^ret;
  }
}

TouchFXReverb : TouchStoreUI {
  getChildren {
    ^[
      TouchControlToggle.fromStore('/fx/reverb/toggle', store, \enabled),
      TouchControlRange.fromStore('/fx/reverb/wet', store, \wet),
      TouchControlRange.fromStore('/fx/reverb/room', store, \room, [0.1,1]),
    ];
  }
}

TouchFXDelay : TouchStoreUI {
  getChildren {
    ^[
      TouchControlToggle.fromStore('/fx/delay/toggle', store, \enabled),
      TouchControlToggle.fromStore('/fx/delay/quantize', store, \quantize),
      TouchControlLabel.fromStore('/fx/delay/delayTime/label', store, \delayLabel),
      TouchControlLabel.fromStore('/fx/delay/decayTime/label', store, \decayLabel),
      TouchControlRange.fromStore('/fx/delay/delayTime', store, \delayTime, [0,1,2]),
      TouchControlRange.fromStore('/fx/delay/decayTime', store, \decayTime, [0,8,4]),
      TouchControlRange.fromStore('/fx/delay/decayMul', store, \decayMul, [0.1,1]),
    ];
  }
}

TouchFXFilter : TouchStoreUI {
  getChildren {
    ^[
      TouchControlToggle.fromStore('/fx/filter/toggle', store, \enabled),
      TouchControlRange.fromStore('/fx/filter/wet', store, \wet),
      TouchControlRange.fromStore('/fx/filter/freq', store, \freq, [-1,1]),
      TouchControlRange.fromStore('/fx/filter/width', store, \width, [0.1,1]),
    ];
  }
}

TouchFXChorus : TouchStoreUI {
  getChildren {
    ^[
      TouchControlToggle.fromStore('/fx/chorus/toggle', store, \enabled),
      TouchControlRange.fromStore('/fx/chorus/speed', store, \speed, ControlSpec(0.001, 10, \exp)),
      TouchControlRange.fromStore('/fx/chorus/depth', store, \depth, ControlSpec(0.0001, 0.25, \exp)),
      TouchControlRange.fromStore('/fx/chorus/predelay', store, \predelay, ControlSpec(0.0001, 0.2, \exp, 0, 0.001)),
    ];
  }
}

TouchFXDistortion : TouchStoreUI {
  getChildren {
    ^[
      TouchControlToggle.fromStore('/fx/distortion/toggle', store, \enabled),
      TouchControlRange.fromStore('/fx/distortion/wet', store, \wet),
      TouchControlRange.fromStore('/fx/distortion/distortion', store, \distortion),
      TouchControlRange.fromStore('/fx/distortion/gain', store, \gain),
    ];
  }
}
