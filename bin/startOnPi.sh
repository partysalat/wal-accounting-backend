#!/usr/bin/env bash
BASE=/home/pi/wal-accounting-backend
ZIPFILE=${BASE}/wal-accounting-backend-0.0.1-SNAPSHOT.zip
FOLDER=${BASE}/wal-accounting-backend-0.0.1-SNAPSHOT

cd ${BASE}
echo "Kill old processes"
pkill java
rm -rf ${FOLDER}
unzip ${ZIPFILE}
cd ${FOLDER}
echo "Starting server...."
nohup ./bin/wal-accounting-backend -J-Xms128M -J-Xmx512M -Dconfig.resource=application-pi.conf -Dhttp.port=9000 -Dplay.crypto.secret="QCY?tAnfk?aZnfui823hu8bfuiqnwk" &
