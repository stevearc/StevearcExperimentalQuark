FXDelayDataStore {
  var <enabled=false, <quantize=true, <delayTime=0.125, <decayTime=2, <decayMul=0.8,
    <delayLabel="1/8", <decayLabel="2";

  enabled_ { |newval|
    if (newval != enabled) {
      enabled = newval;
      this.process;
    }
  }

  quantize_ { |newval|
    quantize = newval;
    this.process;
  }

  delayTime_ { |newval|
    delayTime = newval;
    decayTime = max(decayTime, delayTime);
    this.process;
  }

  decayTime_ { |newval|
    decayTime = newval;
    delayTime = min(delayTime, decayTime);
    this.process;
  }

  decayMul_ { |newval|
    decayMul = newval;
    this.process;
  }

  process {
    delayTime = max(delayTime, 1/32);
    decayTime = max(decayTime, 1/32);
    decayTime = max(delayTime, decayTime);
    if (quantize) {
      # decayTime, decayLabel = this.prQuantize(decayTime);
      # delayTime, delayLabel = this.prQuantize(delayTime);
    } {
      decayLabel = decayTime.round(0.01).asString;
      delayLabel = delayTime.round(0.01).asString;
    };
    this.changed(\fxdelay);
  }

  prQuantize { |num|
    var divisor = 32,
      check = (num * divisor).asInteger;
    while { divisor > 1 } {
      check = (num * divisor).asInteger;
      if (check == 1) {
        ^[check / divisor, "1/%".format(divisor.asInteger)];
      };
      divisor = divisor / 2;
    };
    ^[num.asInteger, num.asInteger.asString]
  }
}

TouchFXDelay : TouchOSCResponder {
  var <>delayTime=0.25, <>decayTime=2,
    store, touchSynth, synth;

  *new { |touchSynth|
    ^super.new.init(touchSynth);
  }
  init { |tSynth|
    touchSynth = tSynth;
    store = FXDelayDataStore.new;
    store.addDependant(this);
    this.prAddChild(TouchControlToggle(store, \enabled, '/fx/delay/toggle'));
    this.prAddChild(TouchControlToggle(store, \quantize, '/fx/delay/quantize'));
    this.prAddChild(TouchControlLabel(store, \delayLabel, '/fx/delay/delayTime/label'));
    this.prAddChild(TouchControlLabel(store, \decayLabel, '/fx/delay/decayTime/label'));
    this.prAddChild(TouchControlRange(store, \delayTime, '/fx/delay/delayTime', [0,1,2]));
    this.prAddChild(TouchControlRange(store, \decayTime, '/fx/delay/decayTime', [0,8,4]));
    this.prAddChild(TouchControlRange(store, \decayMul, '/fx/delay/decayMul', [0.1,1]));
  }

  update { |store, what|
    synth = touchSynth.updateFXSynth(synth,
      \touchOSCDelay, store.enabled,
      \delayTime, store.delayTime / TempoClock.tempo,
      \decayTime, store.decayTime / TempoClock.tempo,
      \decayMul, store.decayMul,
    );
    this.sync;
  }

  listen {
    super.listen;
    SynthDef(\touchOSCDelay, {
      var sig = In.ar(\out.kr(0), 2);
      sig = sig + CombL.ar(
        sig,
        1,
        \delayTime.kr,
        \decayTime.kr,
        \decayMul.kr,
      );
      ReplaceOut.ar(\out.kr(0), sig);
    }).add;
  }

  free {
    super.free;
    store.enabled = false;
  }
}
