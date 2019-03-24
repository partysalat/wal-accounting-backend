#!/usr/bin/env bash
BASE=/home/pi/wal-accounting-frontend
ZIPFILE=${BASE}/frontend.zip
FOLDER=${BASE}/build

cd ${BASE}
rm -rf ${FOLDER}
mkdir -p ${FOLDER}
unzip ${ZIPFILE}