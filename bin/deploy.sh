#!/usr/bin/env bash
sbt clean compile universal:packageBin
scp target/universal/2null16-bra-1.0-SNAPSHOT.zip pi@bra:/home/pi/2null16-bra-scala
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
ssh pi@bra 'bash -s' < $DIR/startOnPi.sh