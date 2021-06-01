LoopRecording {
  var touchSynths, <>store, <events, <isRecording=false, <>duration, startTime, idPrefix;
  *new { |duration, events|
    ^super.new.init(nil, duration, events);
  }
  *empty { |store|
    ^super.new.init(store);
  }
  init { |theStore, theDuration, theEvents|
    store = theStore;
    touchSynths = Array.new;
    duration = theDuration;
    events = theEvents ?? {Array.new};
  }
  storeArgs { ^[duration, events] }

  update { |synth, action, nodeID, args|
    var beat = TempoClock.beats - startTime;
    if (store.quantize and: (action != \note_set)) {
      if (store.bpm > 100) {
        beat = beat.round(1/4);
      } {
        beat = beat.round(1/8);
      };
    };
    // TODO filter out no-op set events
    events = events.add((
      type: action,
      // nodeID may not be unique. They can get re-used. This is especially true
      // if we have saved & loaded a recording across sessions
      id: (idPrefix ++ nodeID).asSymbol,
      beat: beat,
      synthName: synth.name,
      args: args,
    ));
  }

  clear {
    events = Array.new;
  }

  record { |touchSynth|
    touchSynth.addDependant(this);
    touchSynths = touchSynths.add(touchSynth);
    if (isRecording.not) {
      startTime = TempoClock.beats;
      isRecording = true;
      idPrefix = "%:".format(events.size);
    };
  }
  stop {
    var nodes = IdentityDictionary.new;
    var badIndexes = Array.new;
    touchSynths.do { |touchSynth|
      touchSynth.removeDependant(this);
    };
    touchSynths = Array.new;
    isRecording = false;
    // Filter out set events that come before a start event or after an end
    // event
    events = events.do { |event, i|
      switch(event.type,
        \note_start, {
          nodes[event.id] = event;
        },
        \note_set, {
          if (nodes.includesKey(event.id).not) {
            badIndexes = badIndexes.add(i);
          };
        },
        \note_end, {
          nodes[event.id] = nil;
        },
      );
    };
    badIndexes.reverseDo { |i|
      events.removeAt(i);
    };

    // Make sure that all notes end at the end of the recording
    nodes.pairsDo { |id, event|
      events = events.add((
      type: \note_end,
      id: id,
      beat: duration,
      synthName: event.synthName,
      args: [],
      ));
    };
  }
}

