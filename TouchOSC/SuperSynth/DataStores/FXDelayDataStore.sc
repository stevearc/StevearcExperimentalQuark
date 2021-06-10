FXDelayDataStore : FXDataStore {
  var <quantize, <delayTime, <decayTime, <decayMul,
    <delayLabel, <decayLabel;

  *new { |enabled=false, quantize=true, delayTime=0.125, decayTime=2, decayMul=0.8|
    ^super.newCopyArgs(\fxdelay, enabled, quantize, delayTime, decayTime, decayMul).init;
  }
  init {
    this.forceUpdate;
    this.prAddSetters([\quantize, \decayMul])
  }
  storeArgs { ^[enabled, quantize, delayTime, decayTime, decayMul] }

  delayTime_ { |newval|
    this.setState({
      delayTime = newval;
      decayTime = max(decayTime, delayTime);
    });
  }
  decayTime_ { |newval|
    this.setState({
      decayTime = newval;
      delayTime = min(delayTime, decayTime);
    });
  }

  onPostStateChange {
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
