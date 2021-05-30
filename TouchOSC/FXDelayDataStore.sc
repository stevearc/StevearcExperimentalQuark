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
