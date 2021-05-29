// This makes NdefGui correctly use specs defined in synthdefs or directly on
// the NodeProxy itself

+ NdefParamGui {
  getSpec { |key, value|
    var spec, synthdesc;
    spec = object.specs[key];
    if (spec.isNil and: object.source.class == Symbol) {
      synthdesc = SynthDescLib.global.synthDescs.at(this.object.source);
      if (synthdesc.notNil) {
        spec = (synthdesc.metadata.specs ? ())[key];
      };
    };
    spec = spec ? specs[key] ?? { key.asSpec };
    if (spec.isNil) {
      spec = Spec.guess(key, value);
      specs.put(key, spec);
    };
    ^spec
  }
}
