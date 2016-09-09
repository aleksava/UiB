#!/bin/bash

declare -x EVENT
read -p "Event name please: " EVENT

num=$(grep $EVENT $1 | cut -d " " -f2)

totalFor=0

if [ ${#num} -eq 1 ]; then
  numFor=$(echo $num | cut -d " " -f1)
  totalFor=$((numFor+totalFor))
else
  for((i=1; i<=${#num}/2; i++))
  do
    numFor=$(echo $num | cut -d " " -f$i)
    totalFor=$((numFor+totalFor))
  done
fi

echo "This command has been run a total of $totalFor minutes."
