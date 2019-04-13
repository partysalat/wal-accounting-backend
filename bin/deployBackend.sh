#!/usr/bin/env bash
sbt clean compile universal:packageBin
scp target/universal/wal-accounting-backend-0.0.1-SNAPSHOT.zip pi@bra:/home/pi/wal-accounting-backend
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
ssh pi@bra 'bash -s' < $DIR/startOnPi.sh