FXDelayDataStore {
  var <enabled, <quantize, <delayTime, <decayTime, <decayMul,
    <delayLabel, <decayLabel;

  *new { |enabled=false, quantize=true, delayTime=0.125, decayTime=2, decayMul=0.8|
    var instance = super.newCopyArgs(enabled, quantize, delayTime, decayTime, decayMul);
    instance.markChanged;
    ^instance;
  }
  storeArgs { ^[enabled, quantize, delayTime, decayTime, decayMul] }
  storeOn { |stream|
    if (enabled) {
      super.storeOn(stream);
    } {
      stream <<< nil;
    }
  }

  enabled_ { |newval|
    if (newval != enabled) {
      enabled = newval;
      this.markChanged;
    }
  }
  quantize_ { |newval|
    quantize = newval;
    this.markChanged;
  }
  delayTime_ { |newval|
    delayTime = newval;
    decayTime = max(decayTime, delayTime);
    this.markChanged;
  }
  decayTime_ { |newval|
    decayTime = newval;
    delayTime = min(delayTime, decayTime);
    this.markChanged;
  }
  decayMul_ { |newval|
    decayMul = newval;
    this.markChanged;
  }

  markChanged {
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
