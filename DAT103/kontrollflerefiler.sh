#!/bin/bash

for var in "$@"
do
  source ./filkontroll.sh 5 $var &
done
