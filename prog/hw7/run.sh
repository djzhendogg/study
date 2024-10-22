#!/bin/bash

javac -classpath ./hw7:./prog-intro-2024/java prog-intro-2024/java/markup/MarkupTest.java

java -ea -classpath ./hw7:./prog-intro-2024/java markup.MarkupTest $@
