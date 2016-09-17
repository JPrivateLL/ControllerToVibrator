#!/bin/sh

# a simple wrapper script for driving trance vibrators with OpenVibrations
# using the trance-vibrator.c code found at:
#   http://dqd.com/~mayoff/tools/trance-vibrator.c
#
# the compiled trance-vibrator binary will need to be setuid root:
#   chown root trance-vibrator; chmod u+s trance-vibrator

version="0.8.8"
level=$1

if [ "X${level}" = "X" ]; then
	level=0
fi
echo $level | ~/ovics-$version/ovd/trance/trance-vibrator

