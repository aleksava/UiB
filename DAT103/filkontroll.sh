#!/bin/bash

FILE=$1
declare -i TIME=$2

while true
do
  if [[ test -e $FILE ]]; then
    echo "Huzza"
  #else
  fi
done
