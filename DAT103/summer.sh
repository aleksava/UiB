#!/bin/bash

TOTAL=0

echo "Please type in numbers followed by the 'return' button"
echo "When finished, hit CTRL+D"

while read NUM
do
  let TOTAL=NUM+TOTAL
done

echo "Sum of input: "$TOTAL
