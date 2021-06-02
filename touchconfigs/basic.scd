TouchUber.setPadSynth(0,0,TouchSynth('kick808','kick808',[],nil,nil,nil));
TouchUber.setPadSynth(0,1,TouchSynth('ringz','kickRingz',[],nil,nil,nil));
TouchUber.setPadSynth(0,2,TouchSynth('oto309','kick_oto309',[],nil,nil,nil));
TouchUber.setPadSynth(0,3,TouchSynth('sos','sosKick',[],nil,nil,nil));
TouchUber.setPadSynth(1,0,TouchSynth('snare','snare',['amp',0.3,],nil,nil,nil));
TouchUber.setPadSynth(1,1,TouchSynth('clap','clapOto309',['rel',0.4,'amp',0.2,],nil,nil,nil));
TouchUber.setPadSynth(1,2,TouchSynth('hat','hihat1',['rel',0.13,],nil,nil,nil));
TouchUber.setPadSynth(1,3,TouchSynth('openhat','hihat1',['rel',0.4,],nil,nil,nil));
TouchUber.setPadSynth(2,0,TouchSynth('klank','klank',[],nil,nil,nil));
TouchUber.setPadSynth(2,1,TouchSynth('gong','prayerBell',['freq',46.249302838954,'amp',0.1,],nil,nil,nil));
TouchUber.setPadSynth(2,2,TouchSynth('chime','prayerBell',['freq',2637.020455303,'amp',0.05,],nil,nil,nil));
TouchUber.setPadSynth(2,3,TouchSynth('kalimba','samplerPlayBuf2',['freq',200,'rel',1.4,],nil,nil,nil));
TouchUber.setPadSynth(3,0,TouchSynth('blip','blip',[],nil,nil,nil));
TouchUber.setPadSynth(3,1,TouchSynth('rav','samplerPlayBuf1',['freq',100,'rel',4,'amp',0.8,],nil,nil,nil));
TouchUber.setPadSynth(3,2,TouchSynth('sawbass','sawbass',[],nil,nil,nil));
TouchUber.setPadSynth(3,3,TouchSynth('ikeda','ikedaBass',[],nil,nil,nil));
TouchUber.setLoop(0,LoopRecording(8.0, [ ( 'args': [ 'rel', 0.13 ], 'synthName': 'hat', 'beat': 0.0, 'type': 'note_start', 
  'id': '24:1219' ), ( 'args': [  ], 'type': 'note_start', 'beat': 0.0, 'id': '1126', 
  'synthName': 'ringz' ), ( 'synthName': 'hat', 'beat': 0.25, 'id': '24:1219', 'type': 'note_end' ), ( 'args': [ 'rel', 0.13 ], 'synthName': 'hat', 'beat': 0.5, 'type': 'note_start', 
  'id': '24:1220' ), ( 'synthName': 'ringz', 'beat': 0.5, 'id': '1126', 'type': 'note_end' ), ( 'synthName': 'hat', 'beat': 0.75, 'id': '24:1220', 'type': 'note_end' ), ( 'args': [ 'rel', 0.13 ], 'synthName': 'hat', 'beat': 1.0, 'type': 'note_start', 
  'id': '24:1221' ), ( 'args': [  ], 'type': 'note_start', 'beat': 1.0, 'id': '1129', 
  'synthName': 'ringz' ), ( 'args': [ 'amp', 0.3 ], 'type': 'note_start', 'beat': 1.0, 'id': '1128', 
  'synthName': 'snare' ), ( 'synthName': 'hat', 'beat': 1.25, 'id': '24:1221', 'type': 'note_end' ), ( 'args': [ 'rel', 0.13 ], 'synthName': 'hat', 'beat': 1.5, 'type': 'note_start', 
  'id': '24:1225' ), ( 'synthName': 'ringz', 'beat': 1.5, 'id': '1129', 'type': 'note_end' ), ( 'synthName': 'hat', 'beat': 1.75, 'id': '24:1225', 'type': 'note_end' ), ( 'synthName': 'hat', 'beat': 2.0, 'id': '24:1226', 'type': 'note_end' ), ( 'args': [ 'rel', 0.13 ], 'synthName': 'hat', 'beat': 2.0, 'type': 'note_start', 
  'id': '24:1226' ), ( 'args': [  ], 'type': 'note_start', 'beat': 2.0, 'id': '1131', 
  'synthName': 'ringz' ), ( 'synthName': 'hat', 'beat': 2.5, 'id': '24:1229', 'type': 'note_end' ), ( 'args': [ 'rel', 0.13 ], 'synthName': 'hat', 'beat': 2.5, 'type': 'note_start', 
  'id': '24:1229' ), ( 'synthName': 'ringz', 'beat': 2.5, 'id': '1131', 'type': 'note_end' ), ( 'args': [ 'rel', 0.13 ], 'synthName': 'hat', 'beat': 3.0, 'type': 'note_start', 
  'id': '24:1230' ), ( 'args': [ 'amp', 0.3 ], 'type': 'note_start', 'beat': 3.0, 'id': '1133', 
  'synthName': 'snare' ), ( 'args': [  ], 'type': 'note_start', 'beat': 3.0, 'id': '1132', 
  'synthName': 'ringz' ), ( 'synthName': 'hat', 'beat': 3.25, 'id': '24:1230', 'type': 'note_end' ), ( 'synthName': 'openhat', 'beat': 3.5, 'id': '24:1234', 'type': 'note_end' ), ( 'args': [ 'rel', 0.4 ], 'synthName': 'openhat', 'beat': 3.5, 'type': 'note_start', 
  'id': '24:1234' ), ( 'synthName': 'ringz', 'beat': 3.5, 'id': '1132', 'type': 'note_end' ), ( 'args': [ 'rel', 0.13 ], 'synthName': 'hat', 'beat': 4.0, 'type': 'note_start', 
  'id': '24:1237' ), ( 'args': [  ], 'type': 'note_start', 'beat': 4.0, 'id': '1135', 
  'synthName': 'ringz' ), ( 'synthName': 'hat', 'beat': 4.25, 'id': '24:1237', 'type': 'note_end' ), ( 'synthName': 'hat', 'beat': 4.5, 'id': '24:1238', 'type': 'note_end' ), ( 'args': [ 'rel', 0.13 ], 'synthName': 'hat', 'beat': 4.5, 'type': 'note_start', 
  'id': '24:1238' ), ( 'synthName': 'ringz', 'beat': 4.5, 'id': '1135', 'type': 'note_end' ), ( 'args': [ 'rel', 0.13 ], 'synthName': 'hat', 'beat': 5.0, 'type': 'note_start', 
  'id': '24:1242' ), ( 'args': [ 'amp', 0.3 ], 'type': 'note_start', 'beat': 5.0, 'id': '1139', 
  'synthName': 'snare' ), ( 'args': [  ], 'type': 'note_start', 'beat': 5.0, 'id': '1138', 
  'synthName': 'ringz' ), ( 'synthName': 'hat', 'beat': 5.25, 'id': '24:1242', 'type': 'note_end' ), ( 'args': [ 'rel', 0.13 ], 'synthName': 'hat', 'beat': 5.5, 'type': 'note_start', 
  'id': '24:1243' ), ( 'synthName': 'ringz', 'beat': 5.5, 'id': '1138', 'type': 'note_end' ), ( 'synthName': 'hat', 'beat': 5.75, 'id': '24:1243', 'type': 'note_end' ), ( 'args': [ 'rel', 0.13 ], 'synthName': 'hat', 'beat': 6.0, 'type': 'note_start', 
  'id': '24:1244' ), ( 'args': [  ], 'type': 'note_start', 'beat': 6.0, 'id': '1140', 
  'synthName': 'ringz' ), ( 'synthName': 'hat', 'beat': 6.25, 'id': '24:1244', 'type': 'note_end' ), ( 'args': [ 'rel', 0.13 ], 'synthName': 'hat', 'beat': 6.5, 'type': 'note_start', 
  'id': '24:1247' ), ( 'synthName': 'ringz', 'beat': 6.5, 'id': '1140', 'type': 'note_end' ), ( 'synthName': 'hat', 'beat': 6.75, 'id': '24:1247', 'type': 'note_end' ), ( 'args': [ 'rel', 0.13 ], 'synthName': 'hat', 'beat': 7.0, 'type': 'note_start', 
  'id': '24:1251' ), ( 'args': [  ], 'type': 'note_start', 'beat': 7.0, 'id': '1143', 
  'synthName': 'ringz' ), ( 'args': [ 'amp', 0.3 ], 'type': 'note_start', 'beat': 7.0, 'id': '1142', 
  'synthName': 'snare' ), ( 'synthName': 'hat', 'beat': 7.25, 'id': '24:1251', 'type': 'note_end' ), ( 'synthName': 'openhat', 'beat': 7.5, 'id': '24:1252', 'type': 'note_end' ), ( 'args': [ 'rel', 0.4 ], 'synthName': 'openhat', 'beat': 7.5, 'type': 'note_start', 
  'id': '24:1252' ), ( 'synthName': 'ringz', 'beat': 7.5, 'id': '1143', 'type': 'note_end' ), ( 'args': [  ], 'synthName': 'hat', 'beat': 8.0, 'type': 'note_end', 
  'id': '24:1219' ), ( 'args': [  ], 'synthName': 'hat', 'beat': 8.0, 'type': 'note_end', 
  'id': '24:1242' ), ( 'args': [  ], 'synthName': 'openhat', 'beat': 8.0, 'type': 'note_end', 
  'id': '24:1234' ), ( 'args': [  ], 'synthName': 'openhat', 'beat': 8.0, 'type': 'note_end', 
  'id': '24:1252' ), ( 'args': [  ], 'synthName': 'hat', 'beat': 8.0, 'type': 'note_end', 
  'id': '24:1229' ), ( 'args': [  ], 'synthName': 'hat', 'beat': 8.0, 'type': 'note_end', 
  'id': '24:1238' ), ( 'args': [  ], 'synthName': 'hat', 'beat': 8.0, 'type': 'note_end', 
  'id': '24:1225' ), ( 'args': [  ], 'synthName': 'hat', 'beat': 8.0, 'type': 'note_end', 
  'id': '24:1251' ), ( 'args': [  ], 'synthName': 'hat', 'beat': 8.0, 'type': 'note_end', 
  'id': '24:1247' ), ( 'args': [  ], 'synthName': 'hat', 'beat': 8.0, 'type': 'note_end', 
  'id': '24:1230' ), ( 'args': [  ], 'synthName': 'hat', 'beat': 8.0, 'type': 'note_end', 
  'id': '24:1237' ), ( 'args': [  ], 'synthName': 'hat', 'beat': 8.0, 'type': 'note_end', 
  'id': '24:1220' ), ( 'args': [  ], 'synthName': 'hat', 'beat': 8.0, 'type': 'note_end', 
  'id': '24:1226' ), ( 'args': [  ], 'synthName': 'hat', 'beat': 8.0, 'type': 'note_end', 
  'id': '24:1221' ), ( 'args': [  ], 'synthName': 'hat', 'beat': 8.0, 'type': 'note_end', 
  'id': '24:1244' ), ( 'args': [  ], 'synthName': 'hat', 'beat': 8.0, 'type': 'note_end', 
  'id': '24:1243' ), ( 'args': [  ], 'type': 'note_end', 'beat': 8.0, 'id': '1139', 
  'synthName': 'snare' ), ( 'args': [  ], 'type': 'note_end', 'beat': 8.0, 'id': '1142', 
  'synthName': 'snare' ), ( 'args': [  ], 'type': 'note_end', 'beat': 8.0, 'id': '1128', 
  'synthName': 'snare' ), ( 'args': [  ], 'type': 'note_end', 'beat': 8.0, 'id': '1133', 
  'synthName': 'snare' ) ]));
TouchUber.setLoop(1,LoopRecording(8.0, [ ( 'args': [ 'freq', 97.998858995437 ], 'type': 'note_start', 'beat': 0.049414661000355, 'id': '0:1281', 
  'synthName': 'sawbass' ), ( 'synthName': 'sawbass', 'beat': 1.5305158269998, 'id': '0:1281', 'type': 'note_end' ), ( 'args': [ 'freq', 130.8127826503 ], 'type': 'note_start', 'beat': 1.5800440150001, 'id': '0:1291', 
  'synthName': 'sawbass' ), ( 'args': [ 'freq', 130.8127826503 ], 'type': 'note_set', 'beat': 2.4989192790003, 'id': '0:1291', 
  'synthName': 'sawbass' ), ( 'args': [ 'freq', 116.54094037952 ], 'type': 'note_start', 'beat': 2.4989192790003, 'id': '0:1295', 
  'synthName': 'sawbass' ), ( 'synthName': 'sawbass', 'beat': 2.5102254930002, 'id': '0:1291', 'type': 'note_end' ), ( 'args': [ 'freq', 116.54094037952 ], 'type': 'note_set', 'beat': 2.5102254930002, 'id': '0:1295', 
  'synthName': 'sawbass' ), ( 'args': [ 'freq', 116.54094037952 ], 'type': 'note_set', 'beat': 2.9471393810004, 'id': '0:1295', 
  'synthName': 'sawbass' ), ( 'args': [ 'freq', 110.0 ], 'type': 'note_start', 'beat': 2.9471393810004, 'id': '0:1297', 
  'synthName': 'sawbass' ), ( 'synthName': 'sawbass', 'beat': 2.9803677949999, 'id': '0:1295', 'type': 'note_end' ), ( 'args': [ 'freq', 110.0 ], 'type': 'note_set', 'beat': 2.9803677949999, 'id': '0:1297', 
  'synthName': 'sawbass' ), ( 'args': [ 'freq', 110.0 ], 'type': 'note_set', 'beat': 4.1386601830004, 'id': '0:1297', 
  'synthName': 'sawbass' ), ( 'args': [ 'freq', 97.998858995437 ], 'type': 'note_start', 'beat': 4.1386601830004, 'id': '0:1306', 
  'synthName': 'sawbass' ), ( 'synthName': 'sawbass', 'beat': 4.2005822209999, 'id': '0:1297', 'type': 'note_end' ), ( 'args': [ 'freq', 97.998858995437 ], 'type': 'note_set', 'beat': 4.2005822209999, 'id': '0:1306', 
  'synthName': 'sawbass' ), ( 'args': [ 'freq', 130.8127826503 ], 'type': 'note_start', 'beat': 5.6184284289998, 'id': '0:1313', 
  'synthName': 'sawbass' ), ( 'args': [ 'freq', 97.998858995437 ], 'type': 'note_set', 'beat': 5.6184284289998, 'id': '0:1306', 
  'synthName': 'sawbass' ), ( 'args': [ 'freq', 130.8127826503 ], 'type': 'note_set', 'beat': 5.6263698830003, 'id': '0:1313', 
  'synthName': 'sawbass' ), ( 'synthName': 'sawbass', 'beat': 5.6263698830003, 'id': '0:1306', 'type': 'note_end' ), ( 'args': [ 'freq', 130.8127826503 ], 'type': 'note_set', 'beat': 6.5691987470004, 'id': '0:1313', 
  'synthName': 'sawbass' ), ( 'args': [ 'freq', 116.54094037952 ], 'type': 'note_start', 'beat': 6.5691987470004, 'id': '0:1318', 
  'synthName': 'sawbass' ), ( 'synthName': 'sawbass', 'beat': 6.5809650229999, 'id': '0:1313', 'type': 'note_end' ), ( 'args': [ 'freq', 116.54094037952 ], 'type': 'note_set', 'beat': 6.5809650229999, 'id': '0:1318', 
  'synthName': 'sawbass' ), ( 'args': [ 'freq', 116.54094037952 ], 'type': 'note_set', 'beat': 7.0764386169999, 'id': '0:1318', 
  'synthName': 'sawbass' ), ( 'args': [ 'freq', 110.0 ], 'type': 'note_start', 'beat': 7.0764386169999, 'id': '0:1323', 
  'synthName': 'sawbass' ), ( 'synthName': 'sawbass', 'beat': 7.0823147389997, 'id': '0:1318', 'type': 'note_end' ), ( 'args': [ 'freq', 110.0 ], 'type': 'note_set', 'beat': 7.0823147389997, 'id': '0:1323', 
  'synthName': 'sawbass' ), ( 'args': [  ], 'type': 'note_end', 'beat': 8.0, 'id': '0:1323', 
  'synthName': 'sawbass' ) ]));
TouchUber.setLoop(2,LoopRecording(8.0, [  ]));
TouchUber.setLoop(3,LoopRecording(nil, [  ]));
