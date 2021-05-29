// This is overriding the logic in ProxyInterfaces.sc. It allows us to specify
// the rate and numChannels in the metadata of a SynthDef, so that it will be
// correctly initialized when a NodeProxy is created in ProxySpace.
+ SynthControl {
  build { | proxy, orderIndex |
    var rate, numChannels, desc;
    desc = this.synthDesc;
    if(desc.notNil) {
      canFreeSynth = desc.canFreeSynth;
      canReleaseSynth = desc.hasGate && canFreeSynth;
      hasFadeTimeControl = desc.controls.any { |x| x.name === \fadeTime };
    };
    // XXX This is the changed code
    if(proxy.isNeutral) {
      rate = desc.metadata.proxyRate ? \audio;
      numChannels = desc.metadata.proxyChannels;
    };
    ^proxy.initBus(rate, numChannels)
  }
}
