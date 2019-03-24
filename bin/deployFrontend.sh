#!/usr/bin/env bash
cd frontend/
npm run build
zip -r frontend.zip build
scp frontend.zip pi@bra:/home/pi/wal-accounting-frontend/frontend.zip

DIR="$( cd ../"$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
ssh pi@bra 'bash -s' < $DIR/expandFrontend.sh
cd ..