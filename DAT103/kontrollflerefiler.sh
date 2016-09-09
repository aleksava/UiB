#!/bin/bash

for var in "$@"
do
  source ./filkontroll.sh 60 $var &
done
