TouchUber.setPadSynth(0,0,TouchSynth.hydrate('kick808',SynthDataStore('kick808', [  ]),nil,nil,nil));
TouchUber.setPadSynth(0,1,TouchSynth.hydrate('ringz',SynthDataStore('kickRingz', [  ]),nil,nil,nil));
TouchUber.setPadSynth(0,2,TouchSynth.hydrate('oto309',SynthDataStore('kick_oto309', [  ]),nil,nil,nil));
TouchUber.setPadSynth(0,3,TouchSynth.hydrate('sos',SynthDataStore('sosKick', [  ]),nil,nil,nil));
TouchUber.setPadSynth(1,0,TouchSynth.hydrate('snare',SynthDataStore('snare', [ 'amp', 0.3 ]),nil,nil,nil));
TouchUber.setPadSynth(1,1,TouchSynth.hydrate('clap',SynthDataStore('clapOto309', [ 'rel', 0.4, 'amp', 0.2 ]),nil,nil,nil));
TouchUber.setPadSynth(1,2,TouchSynth.hydrate('hat',SynthDataStore('hihat1', [ 'rel', 0.13 ]),nil,nil,nil));
TouchUber.setPadSynth(1,3,TouchSynth.hydrate('openhat',SynthDataStore('hihat1', [ 'rel', 0.4 ]),nil,nil,nil));
TouchUber.setPadSynth(2,0,TouchSynth.hydrate('klank',SynthDataStore('klank', [  ]),nil,nil,nil));
TouchUber.setPadSynth(2,1,TouchSynth.hydrate('gong',SynthDataStore('prayerBell', [ 'freq', 46.249302838954, 'amp', 0.1 ]),nil,nil,nil));
TouchUber.setPadSynth(2,2,TouchSynth.hydrate('chime',SynthDataStore('prayerBell', [ 'freq', 2637.020455303, 'amp', 0.05 ]),nil,nil,nil));
TouchUber.setPadSynth(2,3,TouchSynth.hydrate('kalimba',SynthDataStore('samplerPlayBuf2', [ 'freq', 200, 'rel', 1.4, 'pitch', Buffer.new, 'buf', Buffer.new ]),nil,nil,nil));
TouchUber.setPadSynth(3,0,TouchSynth.hydrate('blip',SynthDataStore('blip', [  ]),nil,nil,nil));
TouchUber.setPadSynth(3,1,TouchSynth.hydrate('rav',SynthDataStore('samplerPlayBuf1', [ 'freq', 100, 'rel', 4, 'amp', 0.8, 'pitch', Buffer.new, 'buf', Buffer.new ]),nil,nil,nil));
TouchUber.setPadSynth(3,2,TouchSynth.hydrate('sawbass',SynthDataStore('sawbass', [  ]),nil,nil,nil));
TouchUber.setPadSynth(3,3,TouchSynth.hydrate('ikeda',SynthDataStore('ikedaBass', [  ]),nil,nil,nil));
TouchUber.setLoop(0,LoopRecording(8.0, [ ( 'args': [ 'rel', 0.13 ], 'type': 'note_start', 'beat': 0.0, 'id': '24:1219', 
  'synthName': 'hat' ), ( 'args': [  ], 'id': '1126', 'beat': 0.0, 'synthName': 'ringz', 
  'type': 'note_start' ), ( 'synthName': 'hat', 'beat': 0.25, 'id': '24:1219', 'type': 'note_end' ), ( 'args': [ 'rel', 0.13 ], 'type': 'note_start', 'beat': 0.5, 'id': '24:1220', 
  'synthName': 'hat' ), ( 'synthName': 'ringz', 'beat': 0.5, 'id': '1126', 'type': 'note_end' ), ( 'synthName': 'hat', 'beat': 0.75, 'id': '24:1220', 'type': 'note_end' ), ( 'args': [ 'rel', 0.13 ], 'type': 'note_start', 'beat': 1.0, 'id': '24:1221', 
  'synthName': 'hat' ), ( 'args': [  ], 'id': '1129', 'beat': 1.0, 'synthName': 'ringz', 
  'type': 'note_start' ), ( 'args': [ 'amp', 0.3 ], 'id': '1128', 'beat': 1.0, 'synthName': 'snare', 
  'type': 'note_start' ), ( 'synthName': 'hat', 'beat': 1.25, 'id': '24:1221', 'type': 'note_end' ), ( 'args': [ 'rel', 0.13 ], 'type': 'note_start', 'beat': 1.5, 'id': '24:1225', 
  'synthName': 'hat' ), ( 'synthName': 'ringz', 'beat': 1.5, 'id': '1129', 'type': 'note_end' ), ( 'synthName': 'hat', 'beat': 1.75, 'id': '24:1225', 'type': 'note_end' ), ( 'synthName': 'hat', 'beat': 2.0, 'id': '24:1226', 'type': 'note_end' ), ( 'args': [ 'rel', 0.13 ], 'type': 'note_start', 'beat': 2.0, 'id': '24:1226', 
  'synthName': 'hat' ), ( 'args': [  ], 'id': '1131', 'beat': 2.0, 'synthName': 'ringz', 
  'type': 'note_start' ), ( 'synthName': 'hat', 'beat': 2.5, 'id': '24:1229', 'type': 'note_end' ), ( 'args': [ 'rel', 0.13 ], 'type': 'note_start', 'beat': 2.5, 'id': '24:1229', 
  'synthName': 'hat' ), ( 'synthName': 'ringz', 'beat': 2.5, 'id': '1131', 'type': 'note_end' ), ( 'args': [ 'rel', 0.13 ], 'type': 'note_start', 'beat': 3.0, 'id': '24:1230', 
  'synthName': 'hat' ), ( 'args': [ 'amp', 0.3 ], 'id': '1133', 'beat': 3.0, 'synthName': 'snare', 
  'type': 'note_start' ), ( 'args': [  ], 'id': '1132', 'beat': 3.0, 'synthName': 'ringz', 
  'type': 'note_start' ), ( 'synthName': 'hat', 'beat': 3.25, 'id': '24:1230', 'type': 'note_end' ), ( 'synthName': 'openhat', 'beat': 3.5, 'id': '24:1234', 'type': 'note_end' ), ( 'args': [ 'rel', 0.4 ], 'type': 'note_start', 'beat': 3.5, 'id': '24:1234', 
  'synthName': 'openhat' ), ( 'synthName': 'ringz', 'beat': 3.5, 'id': '1132', 'type': 'note_end' ), ( 'args': [ 'rel', 0.13 ], 'type': 'note_start', 'beat': 4.0, 'id': '24:1237', 
  'synthName': 'hat' ), ( 'args': [  ], 'id': '1135', 'beat': 4.0, 'synthName': 'ringz', 
  'type': 'note_start' ), ( 'synthName': 'hat', 'beat': 4.25, 'id': '24:1237', 'type': 'note_end' ), ( 'synthName': 'hat', 'beat': 4.5, 'id': '24:1238', 'type': 'note_end' ), ( 'args': [ 'rel', 0.13 ], 'type': 'note_start', 'beat': 4.5, 'id': '24:1238', 
  'synthName': 'hat' ), ( 'synthName': 'ringz', 'beat': 4.5, 'id': '1135', 'type': 'note_end' ), ( 'args': [ 'rel', 0.13 ], 'type': 'note_start', 'beat': 5.0, 'id': '24:1242', 
  'synthName': 'hat' ), ( 'args': [ 'amp', 0.3 ], 'id': '1139', 'beat': 5.0, 'synthName': 'snare', 
  'type': 'note_start' ), ( 'args': [  ], 'id': '1138', 'beat': 5.0, 'synthName': 'ringz', 
  'type': 'note_start' ), ( 'synthName': 'hat', 'beat': 5.25, 'id': '24:1242', 'type': 'note_end' ), ( 'args': [ 'rel', 0.13 ], 'type': 'note_start', 'beat': 5.5, 'id': '24:1243', 
  'synthName': 'hat' ), ( 'synthName': 'ringz', 'beat': 5.5, 'id': '1138', 'type': 'note_end' ), ( 'synthName': 'hat', 'beat': 5.75, 'id': '24:1243', 'type': 'note_end' ), ( 'args': [ 'rel', 0.13 ], 'type': 'note_start', 'beat': 6.0, 'id': '24:1244', 
  'synthName': 'hat' ), ( 'args': [  ], 'id': '1140', 'beat': 6.0, 'synthName': 'ringz', 
  'type': 'note_start' ), ( 'synthName': 'hat', 'beat': 6.25, 'id': '24:1244', 'type': 'note_end' ), ( 'args': [ 'rel', 0.13 ], 'type': 'note_start', 'beat': 6.5, 'id': '24:1247', 
  'synthName': 'hat' ), ( 'synthName': 'ringz', 'beat': 6.5, 'id': '1140', 'type': 'note_end' ), ( 'synthName': 'hat', 'beat': 6.75, 'id': '24:1247', 'type': 'note_end' ), ( 'args': [ 'rel', 0.13 ], 'type': 'note_start', 'beat': 7.0, 'id': '24:1251', 
  'synthName': 'hat' ), ( 'args': [  ], 'id': '1143', 'beat': 7.0, 'synthName': 'ringz', 
  'type': 'note_start' ), ( 'args': [ 'amp', 0.3 ], 'id': '1142', 'beat': 7.0, 'synthName': 'snare', 
  'type': 'note_start' ), ( 'synthName': 'hat', 'beat': 7.25, 'id': '24:1251', 'type': 'note_end' ), ( 'synthName': 'openhat', 'beat': 7.5, 'id': '24:1252', 'type': 'note_end' ), ( 'args': [ 'rel', 0.4 ], 'type': 'note_start', 'beat': 7.5, 'id': '24:1252', 
  'synthName': 'openhat' ), ( 'synthName': 'ringz', 'beat': 7.5, 'id': '1143', 'type': 'note_end' ), ( 'args': [  ], 'type': 'note_end', 'beat': 8.0, 'id': '24:1219', 
  'synthName': 'hat' ), ( 'args': [  ], 'type': 'note_end', 'beat': 8.0, 'id': '24:1242', 
  'synthName': 'hat' ), ( 'args': [  ], 'type': 'note_end', 'beat': 8.0, 'id': '24:1234', 
  'synthName': 'openhat' ), ( 'args': [  ], 'type': 'note_end', 'beat': 8.0, 'id': '24:1252', 
  'synthName': 'openhat' ), ( 'args': [  ], 'type': 'note_end', 'beat': 8.0, 'id': '24:1229', 
  'synthName': 'hat' ), ( 'args': [  ], 'type': 'note_end', 'beat': 8.0, 'id': '24:1238', 
  'synthName': 'hat' ), ( 'args': [  ], 'type': 'note_end', 'beat': 8.0, 'id': '24:1225', 
  'synthName': 'hat' ), ( 'args': [  ], 'type': 'note_end', 'beat': 8.0, 'id': '24:1251', 
  'synthName': 'hat' ), ( 'args': [  ], 'type': 'note_end', 'beat': 8.0, 'id': '24:1247', 
  'synthName': 'hat' ), ( 'args': [  ], 'type': 'note_end', 'beat': 8.0, 'id': '24:1230', 
  'synthName': 'hat' ), ( 'args': [  ], 'type': 'note_end', 'beat': 8.0, 'id': '24:1237', 
  'synthName': 'hat' ), ( 'args': [  ], 'type': 'note_end', 'beat': 8.0, 'id': '24:1220', 
  'synthName': 'hat' ), ( 'args': [  ], 'type': 'note_end', 'beat': 8.0, 'id': '24:1226', 
  'synthName': 'hat' ), ( 'args': [  ], 'type': 'note_end', 'beat': 8.0, 'id': '24:1221', 
  'synthName': 'hat' ), ( 'args': [  ], 'type': 'note_end', 'beat': 8.0, 'id': '24:1244', 
  'synthName': 'hat' ), ( 'args': [  ], 'type': 'note_end', 'beat': 8.0, 'id': '24:1243', 
  'synthName': 'hat' ), ( 'args': [  ], 'id': '1139', 'beat': 8.0, 'synthName': 'snare', 
  'type': 'note_end' ), ( 'args': [  ], 'id': '1142', 'beat': 8.0, 'synthName': 'snare', 
  'type': 'note_end' ), ( 'args': [  ], 'id': '1128', 'beat': 8.0, 'synthName': 'snare', 
  'type': 'note_end' ), ( 'args': [  ], 'id': '1133', 'beat': 8.0, 'synthName': 'snare', 
  'type': 'note_end' ) ]));
TouchUber.setLoop(1,LoopRecording(8.0, [ ( 'args': [ 'freq', 97.998858995437 ], 'id': '0:1281', 'beat': 0.049414661000355, 'synthName': 'sawbass', 
  'type': 'note_start' ), ( 'synthName': 'sawbass', 'beat': 1.5305158269998, 'id': '0:1281', 'type': 'note_end' ), ( 'args': [ 'freq', 130.8127826503 ], 'id': '0:1291', 'beat': 1.5800440150001, 'synthName': 'sawbass', 
  'type': 'note_start' ), ( 'args': [ 'freq', 130.8127826503 ], 'id': '0:1291', 'beat': 2.4989192790003, 'synthName': 'sawbass', 
  'type': 'note_set' ), ( 'args': [ 'freq', 116.54094037952 ], 'id': '0:1295', 'beat': 2.4989192790003, 'synthName': 'sawbass', 
  'type': 'note_start' ), ( 'synthName': 'sawbass', 'beat': 2.5102254930002, 'id': '0:1291', 'type': 'note_end' ), ( 'args': [ 'freq', 116.54094037952 ], 'id': '0:1295', 'beat': 2.5102254930002, 'synthName': 'sawbass', 
  'type': 'note_set' ), ( 'args': [ 'freq', 116.54094037952 ], 'id': '0:1295', 'beat': 2.9471393810004, 'synthName': 'sawbass', 
  'type': 'note_set' ), ( 'args': [ 'freq', 110.0 ], 'id': '0:1297', 'beat': 2.9471393810004, 'synthName': 'sawbass', 
  'type': 'note_start' ), ( 'synthName': 'sawbass', 'beat': 2.9803677949999, 'id': '0:1295', 'type': 'note_end' ), ( 'args': [ 'freq', 110.0 ], 'id': '0:1297', 'beat': 2.9803677949999, 'synthName': 'sawbass', 
  'type': 'note_set' ), ( 'args': [ 'freq', 110.0 ], 'id': '0:1297', 'beat': 4.1386601830004, 'synthName': 'sawbass', 
  'type': 'note_set' ), ( 'args': [ 'freq', 97.998858995437 ], 'id': '0:1306', 'beat': 4.1386601830004, 'synthName': 'sawbass', 
  'type': 'note_start' ), ( 'synthName': 'sawbass', 'beat': 4.2005822209999, 'id': '0:1297', 'type': 'note_end' ), ( 'args': [ 'freq', 97.998858995437 ], 'id': '0:1306', 'beat': 4.2005822209999, 'synthName': 'sawbass', 
  'type': 'note_set' ), ( 'args': [ 'freq', 130.8127826503 ], 'id': '0:1313', 'beat': 5.6184284289998, 'synthName': 'sawbass', 
  'type': 'note_start' ), ( 'args': [ 'freq', 97.998858995437 ], 'id': '0:1306', 'beat': 5.6184284289998, 'synthName': 'sawbass', 
  'type': 'note_set' ), ( 'args': [ 'freq', 130.8127826503 ], 'id': '0:1313', 'beat': 5.6263698830003, 'synthName': 'sawbass', 
  'type': 'note_set' ), ( 'synthName': 'sawbass', 'beat': 5.6263698830003, 'id': '0:1306', 'type': 'note_end' ), ( 'args': [ 'freq', 130.8127826503 ], 'id': '0:1313', 'beat': 6.5691987470004, 'synthName': 'sawbass', 
  'type': 'note_set' ), ( 'args': [ 'freq', 116.54094037952 ], 'id': '0:1318', 'beat': 6.5691987470004, 'synthName': 'sawbass', 
  'type': 'note_start' ), ( 'synthName': 'sawbass', 'beat': 6.5809650229999, 'id': '0:1313', 'type': 'note_end' ), ( 'args': [ 'freq', 116.54094037952 ], 'id': '0:1318', 'beat': 6.5809650229999, 'synthName': 'sawbass', 
  'type': 'note_set' ), ( 'args': [ 'freq', 116.54094037952 ], 'id': '0:1318', 'beat': 7.0764386169999, 'synthName': 'sawbass', 
  'type': 'note_set' ), ( 'args': [ 'freq', 110.0 ], 'id': '0:1323', 'beat': 7.0764386169999, 'synthName': 'sawbass', 
  'type': 'note_start' ), ( 'synthName': 'sawbass', 'beat': 7.0823147389997, 'id': '0:1318', 'type': 'note_end' ), ( 'args': [ 'freq', 110.0 ], 'id': '0:1323', 'beat': 7.0823147389997, 'synthName': 'sawbass', 
  'type': 'note_set' ), ( 'args': [  ], 'id': '0:1323', 'beat': 8.0, 'synthName': 'sawbass', 
  'type': 'note_end' ) ]));
TouchUber.setLoop(2,LoopRecording(8.0, [  ]));
TouchUber.setLoop(3,LoopRecording(nil, [  ]));
