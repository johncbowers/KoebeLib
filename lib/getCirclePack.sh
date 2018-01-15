#!/usr/bin/env bash

curl -o pack.jar http://www.math.utk.edu/%7Ekens/CirclePack/downloads/oldversions/CirclePack-J2.8.jar
jar xf pack.jar
rm -r META-INF runCirclePack pack.jar 
