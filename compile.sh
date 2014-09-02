#!/bin/sh
CP=classes/:lib/*
SP=src/

/bin/mkdir -p classes/

/usr/lib/jvm/java-8-oracle/bin/javac -sourcepath $SP -classpath $CP -d classes/ src/*/*.java || exit 1

/bin/rm -f jay.jar 
/usr/lib/jvm/java-8-oracle/bin/jar cf jay.jar -C classes . || exit 1
/bin/rm -rf classes

echo "nxt.jar generated successfully"
