#!/bin/bash

declare -x EVENT
read -p "Event name please: " EVENT

num=$(grep $EVENT $1 | cut -d " " -f2)

#noEntries="0 "

#if [ "$num" == "$noEntries" ]; then
#  echo "Sorry, we couldn't find that command"
#fi

num1=$(echo $num | cut -d " " -f1)
num2=$(echo $num | cut -d " " -f2)
num3=$(echo $num | cut -d " " -f3)

#echo ${#num}

total=0
total=$((num1+num2+num3))

echo $num
echo "This command has been run $total times."
