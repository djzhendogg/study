#!/bin/bash

javac *.java && java -ea -jar WordStatTest.jar $@
javac *.java && java -ea -jar FastReverseTest.jar $@
