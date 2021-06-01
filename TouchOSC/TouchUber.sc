TouchUber : TouchOSCResponder {
  classvar <>default, numKeyboards=3;
  var keys, fxboard, pads, lastSave, saveRoutine;
  *initClass {
    default = this.new;
  }
  *new {
    ^super.new.init;
  }
  init {
    keys = Array.fill(numKeyboards, {|i| TouchKeyboard.new(i+1)});
    keys.do { |keyboard|
      this.prAddChild(keyboard);
    };
    fxboard = TouchFXBoard.new;
    this.prAddChild(fxboard);
    pads = TouchPads.new;
    this.prAddChild(pads);
  }

  *setPadSynth { |row, col, touchSynth| ^this.default.setPadSynth(row,col,touchSynth) }
  setPadSynth { |row, col, touchSynth|
    pads.store.setPadSynth(row, col, touchSynth);
  }

  *start { ^this.default.start }
  start {
    if (isListening) {
      ^this;
    };
    super.start;
    fxboard.attach(pads.selectedSynth);
    keys.do { |keyboard|
      keyboard.touchSynth = pads.selectedSynth;
    };
    CmdPeriod.doOnce { this.stop };
    this.prAddFunc('/keys', {this.onPageChange('/keys')});
    this.prAddFunc('/fx', {this.onPageChange('/fx')});
    this.prAddFunc('/pads', {this.onPageChange('/pads')});
  }

  onPageChange { |page|
    keys.do { |keyboard|
      keyboard.touchSynth = pads.selectedSynth;
      keyboard.sync(true, true);
    };
    switch(page,
      '/keys', {
      },
      '/fx', {
        fxboard.attach(pads.selectedSynth);
        fxboard.sync(true, true);
      },
      '/pads', {
        pads.sync(true, true);
      },
    );
  }

  *serializeSynths { ^this.default.serializeSynths }
  serializeSynths {
    ^String.streamContents({ |stream|
      pads.store.padTouchSynths.rowsDo { |row, i|
        row.do { |touchSynth, j|
          stream << this.class.name << ".setPadSynth(" << i << "," << j << ",";
          touchSynth.storeOn(stream);
          stream << ");\n";
        };
      };
    });
  }

  *save { |filename| ^this.default.save(filename) }
  save { |filename|
    var path;
    path = PathName(filename ? lastSave);
    if (path.extension == "") {
      path = PathName(filename ++ ".scd");
    };
    lastSave = path.fullPath;
    File.mkdir(path.pathOnly);
    File.use(path.fullPath, "w", { |f| f.write(this.serializeSynths); });
  }

  *load { |filename| ^this.default.load(filename) }
  load { |filename|
    var path = PathName(filename);
    if (path.extension == "") {
      path = PathName(filename ++ ".scd");
    };
    lastSave = path.fullPath;
    path.fullPath.load;
  }

  *pSynth { |name| ^this.default.pSynth(name) }
  pSynth { |name|
    var synth = pads.store.getSynth(name);
    ^Pn(synth.asEvent);
  }

  *getSynth { |name| ^this.default.getSynth(name) }
  getSynth { |name|
    ^pads.store.getSynth(name);
  }

  *autosave { |filename, interval=10| ^this.default.autosave(filename, interval) }
  autosave { |filename, interval=10|
    this.stopAutosave;
    saveRoutine = Task({
      loop {
        interval.wait;
        this.save(filename);
      }
    }).start;
    CmdPeriod.doOnce {this.stopAutosave};
  }
  *stopAutosave { ^this.default.stopAutosave }
  stopAutosave {
    if (saveRoutine.notNil) {
      saveRoutine.stop;
      saveRoutine = nil;
    };
  }

  *stop { ^this.default.stop }
}
