SuperSynth.setPadSynth(0,0,TouchSynth.hydrate('kick808',SynthDataStore('kick808', [  ]),nil,nil,nil));
SuperSynth.setPadSynth(0,1,TouchSynth.hydrate('ringz',SynthDataStore('kickRingz', [  ]),nil,nil,nil));
SuperSynth.setPadSynth(0,2,TouchSynth.hydrate('oto309',SynthDataStore('kick_oto309', [  ]),nil,nil,nil));
SuperSynth.setPadSynth(0,3,TouchSynth.hydrate('sos',SynthDataStore('sosKick', [  ]),nil,nil,nil));
SuperSynth.setPadSynth(1,0,TouchSynth.hydrate('snare',SynthDataStore('snare', [ 'amp', 0.3 ]),nil,nil,nil));
SuperSynth.setPadSynth(1,1,TouchSynth.hydrate('clap',SynthDataStore('clapOto309', [ 'rel', 0.4, 'amp', 0.2 ]),nil,nil,nil));
SuperSynth.setPadSynth(1,2,TouchSynth.hydrate('hat',SynthDataStore('hihat1', [ 'rel', 0.13 ]),nil,nil,nil));
SuperSynth.setPadSynth(1,3,TouchSynth.hydrate('openhat',SynthDataStore('hihat1', [ 'rel', 0.4 ]),nil,nil,nil));
SuperSynth.setPadSynth(2,0,TouchSynth.hydrate('klank',SynthDataStore('klank', [  ]),nil,nil,nil));
SuperSynth.setPadSynth(2,1,TouchSynth.hydrate('gong',SynthDataStore('prayerBell', [ 'freq', 46.249302838954, 'amp', 0.1 ]),nil,nil,nil));
SuperSynth.setPadSynth(2,2,TouchSynth.hydrate('chime',SynthDataStore('prayerBell', [ 'freq', 2637.020455303, 'amp', 0.05 ]),nil,nil,nil));
SuperSynth.setPadSynth(2,3,TouchSynth.hydrate('kalimba',SynthDataStore('samplerPlayBuf2', [ 'freq', 200, 'rel', 1.4, 'pitch', Buffer.new, 'buf', Buffer.new ]),nil,nil,nil));
SuperSynth.setPadSynth(3,0,TouchSynth.hydrate('blip',SynthDataStore('blip', [  ]),nil,nil,nil));
SuperSynth.setPadSynth(3,1,TouchSynth.hydrate('rav',SynthDataStore('samplerPlayBuf1', [ 'freq', 100, 'rel', 4, 'amp', 0.8, 'pitch', Buffer.new, 'buf', Buffer.new ]),nil,nil,nil));
SuperSynth.setPadSynth(3,2,TouchSynth.hydrate('sawbass',SynthDataStore('sawbass', [  ]),nil,nil,nil));
SuperSynth.setPadSynth(3,3,TouchSynth.hydrate('ikeda',SynthDataStore('ikedaBass', [  ]),nil,nil,nil));
SuperSynth.setLoop(0,LoopRecording(8.0, [ ( 'args': [ 'rel', 0.13 ], 'id': '24:1219', 'beat': 0.0, 'synthName': 'hat',
  'type': 'note_start' ), ( 'args': [  ], 'synthName': 'ringz', 'beat': 0.0, 'type': 'note_start',
  'id': '1126' ), ( 'synthName': 'hat', 'beat': 0.25, 'id': '24:1219', 'type': 'note_end' ), ( 'args': [ 'rel', 0.13 ], 'id': '24:1220', 'beat': 0.5, 'synthName': 'hat',
  'type': 'note_start' ), ( 'synthName': 'ringz', 'beat': 0.5, 'id': '1126', 'type': 'note_end' ), ( 'synthName': 'hat', 'beat': 0.75, 'id': '24:1220', 'type': 'note_end' ), ( 'args': [ 'rel', 0.13 ], 'id': '24:1221', 'beat': 1.0, 'synthName': 'hat',
  'type': 'note_start' ), ( 'args': [  ], 'synthName': 'ringz', 'beat': 1.0, 'type': 'note_start',
  'id': '1129' ), ( 'args': [ 'amp', 0.3 ], 'synthName': 'snare', 'beat': 1.0, 'type': 'note_start',
  'id': '1128' ), ( 'synthName': 'hat', 'beat': 1.25, 'id': '24:1221', 'type': 'note_end' ), ( 'args': [ 'rel', 0.13 ], 'id': '24:1225', 'beat': 1.5, 'synthName': 'hat',
  'type': 'note_start' ), ( 'synthName': 'ringz', 'beat': 1.5, 'id': '1129', 'type': 'note_end' ), ( 'synthName': 'hat', 'beat': 1.75, 'id': '24:1225', 'type': 'note_end' ), ( 'synthName': 'hat', 'beat': 2.0, 'id': '24:1226', 'type': 'note_end' ), ( 'args': [ 'rel', 0.13 ], 'id': '24:1226', 'beat': 2.0, 'synthName': 'hat',
  'type': 'note_start' ), ( 'args': [  ], 'synthName': 'ringz', 'beat': 2.0, 'type': 'note_start',
  'id': '1131' ), ( 'synthName': 'hat', 'beat': 2.5, 'id': '24:1229', 'type': 'note_end' ), ( 'args': [ 'rel', 0.13 ], 'id': '24:1229', 'beat': 2.5, 'synthName': 'hat',
  'type': 'note_start' ), ( 'synthName': 'ringz', 'beat': 2.5, 'id': '1131', 'type': 'note_end' ), ( 'args': [ 'rel', 0.13 ], 'id': '24:1230', 'beat': 3.0, 'synthName': 'hat',
  'type': 'note_start' ), ( 'args': [ 'amp', 0.3 ], 'synthName': 'snare', 'beat': 3.0, 'type': 'note_start',
  'id': '1133' ), ( 'args': [  ], 'synthName': 'ringz', 'beat': 3.0, 'type': 'note_start',
  'id': '1132' ), ( 'synthName': 'hat', 'beat': 3.25, 'id': '24:1230', 'type': 'note_end' ), ( 'synthName': 'openhat', 'beat': 3.5, 'id': '24:1234', 'type': 'note_end' ), ( 'args': [ 'rel', 0.4 ], 'id': '24:1234', 'beat': 3.5, 'synthName': 'openhat',
  'type': 'note_start' ), ( 'synthName': 'ringz', 'beat': 3.5, 'id': '1132', 'type': 'note_end' ), ( 'args': [ 'rel', 0.13 ], 'id': '24:1237', 'beat': 4.0, 'synthName': 'hat',
  'type': 'note_start' ), ( 'args': [  ], 'synthName': 'ringz', 'beat': 4.0, 'type': 'note_start',
  'id': '1135' ), ( 'synthName': 'hat', 'beat': 4.25, 'id': '24:1237', 'type': 'note_end' ), ( 'synthName': 'hat', 'beat': 4.5, 'id': '24:1238', 'type': 'note_end' ), ( 'args': [ 'rel', 0.13 ], 'id': '24:1238', 'beat': 4.5, 'synthName': 'hat',
  'type': 'note_start' ), ( 'synthName': 'ringz', 'beat': 4.5, 'id': '1135', 'type': 'note_end' ), ( 'args': [ 'rel', 0.13 ], 'id': '24:1242', 'beat': 5.0, 'synthName': 'hat',
  'type': 'note_start' ), ( 'args': [ 'amp', 0.3 ], 'synthName': 'snare', 'beat': 5.0, 'type': 'note_start',
  'id': '1139' ), ( 'args': [  ], 'synthName': 'ringz', 'beat': 5.0, 'type': 'note_start',
  'id': '1138' ), ( 'synthName': 'hat', 'beat': 5.25, 'id': '24:1242', 'type': 'note_end' ), ( 'args': [ 'rel', 0.13 ], 'id': '24:1243', 'beat': 5.5, 'synthName': 'hat',
  'type': 'note_start' ), ( 'synthName': 'ringz', 'beat': 5.5, 'id': '1138', 'type': 'note_end' ), ( 'synthName': 'hat', 'beat': 5.75, 'id': '24:1243', 'type': 'note_end' ), ( 'args': [ 'rel', 0.13 ], 'id': '24:1244', 'beat': 6.0, 'synthName': 'hat',
  'type': 'note_start' ), ( 'args': [  ], 'synthName': 'ringz', 'beat': 6.0, 'type': 'note_start',
  'id': '1140' ), ( 'synthName': 'hat', 'beat': 6.25, 'id': '24:1244', 'type': 'note_end' ), ( 'args': [ 'rel', 0.13 ], 'id': '24:1247', 'beat': 6.5, 'synthName': 'hat',
  'type': 'note_start' ), ( 'synthName': 'ringz', 'beat': 6.5, 'id': '1140', 'type': 'note_end' ), ( 'synthName': 'hat', 'beat': 6.75, 'id': '24:1247', 'type': 'note_end' ), ( 'args': [ 'rel', 0.13 ], 'id': '24:1251', 'beat': 7.0, 'synthName': 'hat',
  'type': 'note_start' ), ( 'args': [  ], 'synthName': 'ringz', 'beat': 7.0, 'type': 'note_start',
  'id': '1143' ), ( 'args': [ 'amp', 0.3 ], 'synthName': 'snare', 'beat': 7.0, 'type': 'note_start',
  'id': '1142' ), ( 'synthName': 'hat', 'beat': 7.25, 'id': '24:1251', 'type': 'note_end' ), ( 'synthName': 'openhat', 'beat': 7.5, 'id': '24:1252', 'type': 'note_end' ), ( 'args': [ 'rel', 0.4 ], 'id': '24:1252', 'beat': 7.5, 'synthName': 'openhat',
  'type': 'note_start' ), ( 'synthName': 'ringz', 'beat': 7.5, 'id': '1143', 'type': 'note_end' ), ( 'args': [  ], 'id': '24:1219', 'beat': 8.0, 'synthName': 'hat',
  'type': 'note_end' ), ( 'args': [  ], 'id': '24:1242', 'beat': 8.0, 'synthName': 'hat',
  'type': 'note_end' ), ( 'args': [  ], 'id': '24:1234', 'beat': 8.0, 'synthName': 'openhat',
  'type': 'note_end' ), ( 'args': [  ], 'id': '24:1252', 'beat': 8.0, 'synthName': 'openhat',
  'type': 'note_end' ), ( 'args': [  ], 'id': '24:1229', 'beat': 8.0, 'synthName': 'hat',
  'type': 'note_end' ), ( 'args': [  ], 'id': '24:1238', 'beat': 8.0, 'synthName': 'hat',
  'type': 'note_end' ), ( 'args': [  ], 'id': '24:1225', 'beat': 8.0, 'synthName': 'hat',
  'type': 'note_end' ), ( 'args': [  ], 'id': '24:1251', 'beat': 8.0, 'synthName': 'hat',
  'type': 'note_end' ), ( 'args': [  ], 'id': '24:1247', 'beat': 8.0, 'synthName': 'hat',
  'type': 'note_end' ), ( 'args': [  ], 'id': '24:1230', 'beat': 8.0, 'synthName': 'hat',
  'type': 'note_end' ), ( 'args': [  ], 'id': '24:1237', 'beat': 8.0, 'synthName': 'hat',
  'type': 'note_end' ), ( 'args': [  ], 'id': '24:1220', 'beat': 8.0, 'synthName': 'hat',
  'type': 'note_end' ), ( 'args': [  ], 'id': '24:1226', 'beat': 8.0, 'synthName': 'hat',
  'type': 'note_end' ), ( 'args': [  ], 'id': '24:1221', 'beat': 8.0, 'synthName': 'hat',
  'type': 'note_end' ), ( 'args': [  ], 'id': '24:1244', 'beat': 8.0, 'synthName': 'hat',
  'type': 'note_end' ), ( 'args': [  ], 'id': '24:1243', 'beat': 8.0, 'synthName': 'hat',
  'type': 'note_end' ), ( 'args': [  ], 'synthName': 'snare', 'beat': 8.0, 'type': 'note_end',
  'id': '1139' ), ( 'args': [  ], 'synthName': 'snare', 'beat': 8.0, 'type': 'note_end',
  'id': '1142' ), ( 'args': [  ], 'synthName': 'snare', 'beat': 8.0, 'type': 'note_end',
  'id': '1128' ), ( 'args': [  ], 'synthName': 'snare', 'beat': 8.0, 'type': 'note_end',
  'id': '1133' ) ]));
SuperSynth.setLoop(1,LoopRecording(8.0, [ ( 'args': [ 'freq', 97.998858995437 ], 'synthName': 'sawbass', 'beat': 0.049414661000355, 'type': 'note_start',
  'id': '0:1281' ), ( 'synthName': 'sawbass', 'beat': 1.5305158269998, 'id': '0:1281', 'type': 'note_end' ), ( 'args': [ 'freq', 130.8127826503 ], 'synthName': 'sawbass', 'beat': 1.5800440150001, 'type': 'note_start',
  'id': '0:1291' ), ( 'args': [ 'freq', 130.8127826503 ], 'synthName': 'sawbass', 'beat': 2.4989192790003, 'type': 'note_set',
  'id': '0:1291' ), ( 'args': [ 'freq', 116.54094037952 ], 'synthName': 'sawbass', 'beat': 2.4989192790003, 'type': 'note_start',
  'id': '0:1295' ), ( 'synthName': 'sawbass', 'beat': 2.5102254930002, 'id': '0:1291', 'type': 'note_end' ), ( 'args': [ 'freq', 116.54094037952 ], 'synthName': 'sawbass', 'beat': 2.5102254930002, 'type': 'note_set',
  'id': '0:1295' ), ( 'args': [ 'freq', 116.54094037952 ], 'synthName': 'sawbass', 'beat': 2.9471393810004, 'type': 'note_set',
  'id': '0:1295' ), ( 'args': [ 'freq', 110.0 ], 'synthName': 'sawbass', 'beat': 2.9471393810004, 'type': 'note_start',
  'id': '0:1297' ), ( 'synthName': 'sawbass', 'beat': 2.9803677949999, 'id': '0:1295', 'type': 'note_end' ), ( 'args': [ 'freq', 110.0 ], 'synthName': 'sawbass', 'beat': 2.9803677949999, 'type': 'note_set',
  'id': '0:1297' ), ( 'args': [ 'freq', 110.0 ], 'synthName': 'sawbass', 'beat': 4.1386601830004, 'type': 'note_set',
  'id': '0:1297' ), ( 'args': [ 'freq', 97.998858995437 ], 'synthName': 'sawbass', 'beat': 4.1386601830004, 'type': 'note_start',
  'id': '0:1306' ), ( 'synthName': 'sawbass', 'beat': 4.2005822209999, 'id': '0:1297', 'type': 'note_end' ), ( 'args': [ 'freq', 97.998858995437 ], 'synthName': 'sawbass', 'beat': 4.2005822209999, 'type': 'note_set',
  'id': '0:1306' ), ( 'args': [ 'freq', 130.8127826503 ], 'synthName': 'sawbass', 'beat': 5.6184284289998, 'type': 'note_start',
  'id': '0:1313' ), ( 'args': [ 'freq', 97.998858995437 ], 'synthName': 'sawbass', 'beat': 5.6184284289998, 'type': 'note_set',
  'id': '0:1306' ), ( 'args': [ 'freq', 130.8127826503 ], 'synthName': 'sawbass', 'beat': 5.6263698830003, 'type': 'note_set',
  'id': '0:1313' ), ( 'synthName': 'sawbass', 'beat': 5.6263698830003, 'id': '0:1306', 'type': 'note_end' ), ( 'args': [ 'freq', 130.8127826503 ], 'synthName': 'sawbass', 'beat': 6.5691987470004, 'type': 'note_set',
  'id': '0:1313' ), ( 'args': [ 'freq', 116.54094037952 ], 'synthName': 'sawbass', 'beat': 6.5691987470004, 'type': 'note_start',
  'id': '0:1318' ), ( 'synthName': 'sawbass', 'beat': 6.5809650229999, 'id': '0:1313', 'type': 'note_end' ), ( 'args': [ 'freq', 116.54094037952 ], 'synthName': 'sawbass', 'beat': 6.5809650229999, 'type': 'note_set',
  'id': '0:1318' ), ( 'args': [ 'freq', 116.54094037952 ], 'synthName': 'sawbass', 'beat': 7.0764386169999, 'type': 'note_set',
  'id': '0:1318' ), ( 'args': [ 'freq', 110.0 ], 'synthName': 'sawbass', 'beat': 7.0764386169999, 'type': 'note_start',
  'id': '0:1323' ), ( 'synthName': 'sawbass', 'beat': 7.0823147389997, 'id': '0:1318', 'type': 'note_end' ), ( 'args': [ 'freq', 110.0 ], 'synthName': 'sawbass', 'beat': 7.0823147389997, 'type': 'note_set',
  'id': '0:1323' ), ( 'args': [  ], 'synthName': 'sawbass', 'beat': 8.0, 'type': 'note_end',
  'id': '0:1323' ) ]));
SuperSynth.setLoop(2,LoopRecording(8.0, [ ( 'id': '0:1050', 'synthName': 'blip', 'beat': 0.0, 'args': [ 'freq', 220.0 ],
  'type': 'note_start' ), ( 'id': '0:1050', 'synthName': 'blip', 'beat': 1.25, 'type': 'note_end' ), ( 'id': '0:1052', 'synthName': 'blip', 'beat': 1.5, 'args': [ 'freq', 329.62755691287 ],
  'type': 'note_start' ), ( 'id': '0:1052', 'synthName': 'blip', 'beat': 2.25, 'type': 'note_end' ), ( 'id': '0:1054', 'synthName': 'blip', 'beat': 2.5, 'args': [ 'freq', 293.66476791741 ],
  'type': 'note_start' ), ( 'id': '0:1054', 'synthName': 'blip', 'beat': 2.75, 'type': 'note_end' ), ( 'id': '0:1055', 'synthName': 'blip', 'beat': 3.0, 'args': [ 'freq', 329.62755691287 ],
  'type': 'note_start' ), ( 'id': '0:1055', 'synthName': 'blip', 'beat': 3.75, 'type': 'note_end' ), ( 'id': '0:1057', 'synthName': 'blip', 'beat': 4.0, 'args': [ 'freq', 220.0 ],
  'type': 'note_start' ), ( 'id': '0:1057', 'synthName': 'blip', 'beat': 5.0, 'type': 'note_end' ), ( 'id': '0:1060', 'synthName': 'blip', 'beat': 5.5, 'args': [ 'freq', 329.62755691287 ],
  'type': 'note_start' ), ( 'id': '0:1060', 'synthName': 'blip', 'beat': 6.0, 'type': 'note_end' ), ( 'id': '0:1062', 'synthName': 'blip', 'beat': 6.5, 'args': [ 'freq', 293.66476791741 ],
  'type': 'note_start' ), ( 'id': '0:1062', 'synthName': 'blip', 'beat': 6.75, 'type': 'note_end' ), ( 'id': '0:1063', 'synthName': 'blip', 'beat': 7.0, 'args': [ 'freq', 261.6255653006 ],
  'type': 'note_start' ), ( 'id': '0:1063', 'synthName': 'blip', 'beat': 7.5, 'type': 'note_end' ), ( 'id': '0:1065', 'synthName': 'blip', 'beat': 8.0, 'args': [ 'freq', 220.0 ],
  'type': 'note_start' ), ( 'id': '0:1065', 'synthName': 'blip', 'beat': 8.0, 'args': [  ],
  'type': 'note_end' ) ]));
SuperSynth.setLoop(3,LoopRecording(nil, [  ]));
