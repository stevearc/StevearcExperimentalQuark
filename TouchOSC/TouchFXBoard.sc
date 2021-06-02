TouchFXBoard : TouchOSCResponder {
  var reverb, delay, filter, synthUI;
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
    synthUI = SynthParams.new;
    this.prAddChild(synthUI);
  }

  attach { |touchSynth|
    synthUI.store = touchSynth.store;
    reverb.store = touchSynth.reverbStore;
    delay.store = touchSynth.delayStore;
    filter.store = touchSynth.filterStore;
  }
}

SynthParams : TouchStoreUI {
  classvar numFaders=10;
  getChildren {
    ^numFaders.collect({ |i|
      var j = i + 1;
      [
        TouchControlLabel('/synth/params/label/'++j, store, {store.specLabel(i)}),
        TouchControlRange('/synth/params/'++j, store, {store.specValue(i)}, store.specRange(i), {|val| store.setValue(i, val)}),
        TouchControlButton('/synth/params/reset/'++j, store, {store.resetValue(i)}),
      ]
    }).flat;
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
      TouchControlRange.fromStore('/fx/filter/freq', store, \freq, [60,16000,6]),
      TouchControlRange.fromStore('/fx/filter/width', store, \width, [0.1,1]),
    ];
  }
}
