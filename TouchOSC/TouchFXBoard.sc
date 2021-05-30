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
    this.prAddChild(TouchControlToggle(store, \enabled, '/fx/reverb/toggle'));
    this.prAddChild(TouchControlRange(store, \wet, '/fx/reverb/wet'));
    this.prAddChild(TouchControlRange(store, \room, '/fx/reverb/room', [0.1,1]));
  }
}

TouchFXDelay : TouchStoreUI {
  addChildrenImpl {
    this.prAddChild(TouchControlToggle(store, \enabled, '/fx/delay/toggle'));
    this.prAddChild(TouchControlToggle(store, \quantize, '/fx/delay/quantize'));
    this.prAddChild(TouchControlLabel(store, \delayLabel, '/fx/delay/delayTime/label'));
    this.prAddChild(TouchControlLabel(store, \decayLabel, '/fx/delay/decayTime/label'));
    this.prAddChild(TouchControlRange(store, \delayTime, '/fx/delay/delayTime', [0,1,2]));
    this.prAddChild(TouchControlRange(store, \decayTime, '/fx/delay/decayTime', [0,8,4]));
    this.prAddChild(TouchControlRange(store, \decayMul, '/fx/delay/decayMul', [0.1,1]));
  }
}

TouchFXFilter : TouchStoreUI {
  addChildrenImpl {
    this.prAddChild(TouchControlToggle(store, \enabled, '/fx/filter/toggle'));
    this.prAddChild(TouchControlRange(store, \wet, '/fx/filter/wet'));
    this.prAddChild(TouchControlRange(store, \freq, '/fx/filter/freq', [60,16000,6]));
    this.prAddChild(TouchControlRange(store, \width, '/fx/filter/width', [0.1,1]));
  }
}
