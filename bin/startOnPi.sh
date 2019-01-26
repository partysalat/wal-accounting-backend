#!/usr/bin/env bash
BASE=/home/pi/2null16-bra-scala
ZIPFILE=${BASE}/2null16-bra-1.0-SNAPSHOT.zip
FOLDER=${BASE}/2null16-bra-1.0-SNAPSHOT

cd ${BASE}
echo "Kill old processes"
pkill java
rm -rf ${FOLDER}
unzip ${ZIPFILE}
cd ${FOLDER}
echo "Starting server...."
nohup ./bin/wal-accounting-backend -J-Xms128M -J-Xmx512M -Dconfig.resource=application-pi.conf -Dhttp.port=9000 -Dplay.crypto.secret="QCY?tAnfk?aZnfui823hu8bfuiqnwk" &
