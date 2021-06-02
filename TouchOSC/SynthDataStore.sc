SynthDataStore {
  classvar blacklist;
  var <synthName, <args, controls=#[], specs=#[];

  *initClass {
    blacklist = Set[\freq, \pan, \out, \in, \buf, \bus];
  }
  *new { |synthName, args=nil|
    ^super.newCopyArgs(synthName, args ? []);
  }
  storeArgs { ^[synthName, args] }

  args_ { |newval|
    args = newval;
    this.markChanged;
  }

  specLabel { |i|
    var control = controls[i];
    var rounded;
    if (control.isNil) {
      ^"";
    };
    ^"%: %".format(controls[i].name, this.specValue(i).round(0.001));
  }
  specRange { |i|
    var spec = specs[i];
    if (spec.isNil) {
      ^[0,1];
    };
    ^spec;
  }
  specValue { |i|
    var control = controls[i];
    if (control.isNil) {
      ^0;
    };
    args.pairsDo { |key, value|
      if (key == control.name) {
        ^value;
      };
    };
    ^control.defaultValue;
  }
  setValue { |i, newval|
    var control = controls[i];
    if (control.isNil) {
      ^this;
    };
    args.pairsDo { |key, value, i|
      if (key == control.name) {
        args[i+1] = newval;
        this.markChanged;
        ^this;
      };
    };
    args = args.add(control.name);
    args = args.add(newval);
    this.markChanged;
  }
  resetValue { |i|
    var control = controls[i];
    var idx;
    if (control.isNil) {
      ^this;
    };
    idx = args.indexOf(control.name);
    if (idx.notNil) {
      args.removeAt(idx+1);
      args.removeAt(idx);
      this.markChanged;
    };
  }

  markChanged {
    var synthDesc = SynthDescLib.global[synthName];
    var curArgs = IdentityDictionary.new;
    args.pairsDo { |key, val|
      curArgs[key] = val;
    };
    controls = [];
    specs = [];
    if (synthDesc.notNil) {
      synthDesc.controls.do { |control|
        var spec = (synthDesc.metadata.specs ? ())[control.name]
          ?? { Spec.specs[control.name] }
          ?? { Spec.guess(control.name, control.defaultValue) };
        if (blacklist.includes(control.name).not and: spec.notNil) {
          var curVal = curArgs[control.name];
          // This filters out obvious buffers and busses
          if (curVal.isNil or: curVal.isNumber) {
            controls = controls.add(control);
            specs = specs.add(spec);
          };
        };
      };
    };
    this.changed(\synthData);
  }
}
