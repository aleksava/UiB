#!/bin/bash

FILE=$2
declare -i TIME=$1
declare -i FILETIMESTAMP


FILEEXISTS=false

if [ -f $FILE ]; then
  echo "This file allready exists"
else
  touch $2
  echo "The file $FILE was created"
  FILETIMESTAMP=$(stat -c%Y $FILE)
  FILEEXISTS=true
fi

LOOP=true

while $LOOP
do
  sleep $1
  if [ -e $FILE ]; then
    if [ $FILETIMESTAMP -ne $(stat -c%Y $FILE) ]
      then
      echo "The file $FILE has been changed"
      FILETIMESTAMP=$(stat -c%Y $FILE)
    fi

  else
    echo "The file $FILE has been deleted"
    LOOP=false
  fi
  echo "Cycled"
done
