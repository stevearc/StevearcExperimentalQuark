MixerGUI {
  // Mostly copied from SynthDesc
  *makeGui { |title, descSymbol, synth|
		var w, startButton, sliders;
		var usefulControls, numControls;
		var getSliderValues, gui;
    var desc = SynthDescLib.global[descSymbol];

		gui = GUI.current;

		usefulControls = desc.controls.select {|controlName, i|
			var ctlname;
			ctlname = controlName.name.asString;
			(ctlname != "?") && (desc.msgFuncKeepGate or: { ctlname != "gate" })
		};

		numControls = usefulControls.collect(_.defaultValue).flatten.size;
		sliders = IdentityDictionary(numControls);

		// make the window
		w = gui.window.new(title, Rect(20, 400, 440, numControls * 28 + 32));
		w.view.decorator = FlowLayout(w.view.bounds);

		w.view.background = Color.rand(0.5, 1.0);

		getSliderValues = {
			var envir;

			envir = ();
			usefulControls.do {|controlName, i|
				var ctlname = controlName.name.asString;
				var sliderEntry = sliders[controlName.name];
				if(ctlname[1] == $_ and: { "ti".includes(ctlname[0]) }) {
					ctlname = ctlname[2..];
				};

				if (sliderEntry.isArray) {
					envir.put(controlName.name, sliderEntry.collect(_.value));
				} {
					envir.put(controlName.name, sliderEntry.value);
				}
			};
			envir.use {
				desc.msgFunc.valueEnvir
			};
		};


		// create controls for all parameters
		usefulControls.do {|controlName|
			var ctlname, ctlname2, capname, spec, controlIndex, slider;
			ctlname = controlName.name;
			capname = ctlname.asString;
			capname[0] = capname[0].toUpper;
			w.view.decorator.nextLine;
			ctlname = ctlname.asSymbol;
			if((spec = desc.metadata.tryPerform(\at, \specs).tryPerform(\at, ctlname)).notNil) {
				spec = spec.asSpec
			} {
				spec = ctlname.asSpec;
			};

			if (spec.isKindOf(ControlSpec)) {
				slider = EZSlider(w, 400 @ 24, capname, spec,
					{ |ez|
						if(synth.notNil) { synth.set(ctlname, ez.value) }
					}, controlName.defaultValue);
			} {
				spec = ControlSpec(-1e10, 1e10);
				if (controlName.defaultValue.isNumber) {
					slider = EZNumber(w, 400 @ 24, capname, spec,
						{ |ez|
							if(synth.notNil) { synth.set(ctlname, ez.value) }
						}, controlName.defaultValue)
				} {
					slider = Array(controlName.defaultValue.size);
					controlName.defaultValue.do {|value, i|
						slider.add(EZNumber(w, 96 @ 24, "%[%]".format(capname, i), spec,
							{ |ez|
								if(synth.notNil) { synth.set(controlName.index + i, ez.value) }
							}, value))
					}
				}
			};
			sliders.put(ctlname, slider)
		};

		^w.front; // make window visible and front window.
  }
}
