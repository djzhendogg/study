#!/bin/bash

javac *.java && java -ea -jar ReverseTest.jar Base
javac *.java && java -ea -jar WordStatTest.jar $@
