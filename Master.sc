Master {
  classvar activateFn, <synth;

  *enable {
    if (Master.isEnabled) {
      ^nil;
    };
    activateFn = {Routine({
      SynthDef(\master, {
        var sig, hpfwet, lpfwet, bpfwet;
				// Assumes 2 channels
        sig = In.ar(0, 2);
				
        sig = XFade2.ar(sig, RHPF.ar(sig, \hpf.kr(1000)), \hpfwet.kr(-1));
        sig = XFade2.ar(sig, RLPF.ar(sig, \lpf.kr(800)), \lpfwet.kr(-1));
        sig = XFade2.ar(sig, BPF.ar(sig, \bpfhz.kr(440), \bpfrq.kr(1)), \bpfwet.kr(-1));

				// Don't blow out my eardrums
				sig = Select.ar(CheckBadValues.ar(sig, 0, 0), [sig, DC.ar(0), DC.ar(0), sig]);
				sig = Limiter.ar(sig);
				sig = sig * \amp.kr(1);

        ReplaceOut.ar(0, sig);
      }, metadata: (
        specs: (
          amp: ControlSpec(0, 1),
          hpfwet: ControlSpec(-1, 1),
          hpf: ControlSpec(20, 20000, \exp, 1),
          lpfwet: ControlSpec(-1, 1),
          lpf: ControlSpec(20, 20000, \exp, 1),
          bpfwet: ControlSpec(-1, 1),
          bpfhz: ControlSpec(20, 10000, \exp, 1),
          bpfrq: ControlSpec(0.1, 20),
        )
      )).add;
      Server.default.sync;
      synth = Synth(\master,
        target: RootNode(Server.default), 
        addAction: \addToTail
      );
    }).play};
    ServerTree.add(activateFn);
    activateFn.value;
  }

  *isEnabled {
    ^synth.notNil;
  }

  *disable {
    if (Master.isEnabled) {
      synth.free;
      ServerTree.remove(activateFn);
      activateFn = nil;
      synth = nil;
    };
  }

  *gui {
		MixerGUI.makeGui("Master", \master, synth);
  }
}
