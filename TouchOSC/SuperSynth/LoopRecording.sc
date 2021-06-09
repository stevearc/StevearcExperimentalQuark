LoopRecording {
  var touchSynths, <>store, <events, <isRecording=false, <>duration, <>startTime, idPrefix;
  *new { |duration, events|
    ^super.new.init(nil, duration, events);
  }
  *empty { |store|
    ^super.new.init(store, 1);
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
    // Because of the fudge factor in LoopController, beat can be negative.
    // In that case, round it up to 0
    beat = max(0, beat);
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
    events.sort { |a, b| a.beat < b.beat };
    // Filter out set/end events that come before a start event or after an end
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
          if (nodes.includesKey(event.id).not) {
            badIndexes = badIndexes.add(i);
          };
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

  asNoteEvents {
    var active = IdentityDictionary.new;
    var ret = [];
    var filtered = events.select{ |e| e.type != \note_set };
    filtered.do { |event, i|
      var j = i+1;
      var nextEvent = filtered[j];
      var dur, noteEvent;
      while {nextEvent.notNil and: {nextEvent.type == \note_end}} {
        j = j + 1;
        nextEvent = filtered[j];
      };
      if (nextEvent.isNil) {
        dur = duration - event.beat;
      } {
        dur = nextEvent.beat - event.beat;
      };
      if (i == 0 and: (event.beat > 0)) {
        ret = ret.add(Event.silent(event.beat));
      };
      switch(event.type,
        \note_start, {
          noteEvent = (
            type: \note,
            dur: dur,
            beat: event.beat,
            synthName: event.synthName,
            sustain: dur,
          );
          event.args.pairsDo { |key, val|
            noteEvent[key] = val;
          };
          active[event.id] = noteEvent;
          ret = ret.add(noteEvent);
        },
        \note_end, {
          var prev = active[event.id];
          if (prev.notNil) {
            prev.sustain = event.beat - prev.beat;
            active[event.id] = nil;
          }
        },
      );
    };
    ^ret;
  }

  asSetEvents {
    var ret = Array.new;
    var filtered = events.select{ |e| e.type == \note_set };
    filtered.do { |event, i|
      var nextEvent = filtered[i+1];
      var dur, setEvent;
      if (nextEvent.isNil) {
        dur = duration - event.beat;
      } {
        dur = nextEvent.beat - event.beat;
      };
      if (i == 0 and: (event.beat > 0)) {
        ret = ret.add(Event.silent(event.beat));
      };
      setEvent = (
        type: \set,
        dur: dur,
        args: Array.new,
      );
      event.args.pairsDo { |key, val|
        setEvent[key] = val;
        setEvent.args = setEvent.args.add(key);
      };
      ret = ret.add(setEvent);
    };
    ^ret;
  }
}
