TouchFXBoard : TouchOSCResponder {
  var reverb, delay, filter;
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
  }

  attach { |touchSynth|
    reverb.store = touchSynth.reverbStore;
    delay.store = touchSynth.delayStore;
    filter.store = touchSynth.filterStore;
  }
}

TouchFXReverb : TouchStoreUI {
  addChildrenImpl {
    this.prAddChild(TouchControlToggle.fromStore('/fx/reverb/toggle', store, \enabled));
    this.prAddChild(TouchControlRange.fromStore('/fx/reverb/wet', store, \wet));
    this.prAddChild(TouchControlRange.fromStore('/fx/reverb/room', store, \room, [0.1,1]));
  }
}

TouchFXDelay : TouchStoreUI {
  addChildrenImpl {
    this.prAddChild(TouchControlToggle.fromStore('/fx/delay/toggle', store, \enabled));
    this.prAddChild(TouchControlToggle.fromStore('/fx/delay/quantize', store, \quantize));
    this.prAddChild(TouchControlLabel.fromStore('/fx/delay/delayTime/label', store, \delayLabel));
    this.prAddChild(TouchControlLabel.fromStore('/fx/delay/decayTime/label', store, \decayLabel));
    this.prAddChild(TouchControlRange.fromStore('/fx/delay/delayTime', store, \delayTime, [0,1,2]));
    this.prAddChild(TouchControlRange.fromStore('/fx/delay/decayTime', store, \decayTime, [0,8,4]));
    this.prAddChild(TouchControlRange.fromStore('/fx/delay/decayMul', store, \decayMul, [0.1,1]));
  }
}

TouchFXFilter : TouchStoreUI {
  addChildrenImpl {
    this.prAddChild(TouchControlToggle.fromStore('/fx/filter/toggle', store, \enabled));
    this.prAddChild(TouchControlRange.fromStore('/fx/filter/wet', store, \wet));
    this.prAddChild(TouchControlRange.fromStore('/fx/filter/freq', store, \freq, [60,16000,6]));
    this.prAddChild(TouchControlRange.fromStore('/fx/filter/width', store, \width, [0.1,1]));
  }
}
