#!/usr/bin/env bash

echo "This command will reset the database. Please input YES to confirm:"

read input

if [ $input='YES' ]; then
  java -cp "lib/*" im.hch.datareceiver.InitDB
else
  echo "Abort InitDB."
fi