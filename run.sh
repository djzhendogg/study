#!/bin/bash

javac -classpath ./prog-intro/java-solutions/*.java
javac -classpath ./prog-intro/java-solutions:./prog-intro-2024/java prog-intro-2024/java/markup/MarkupTest.java
java -ea -classpath ./prog-intro/java-solutions:./prog-intro-2024/java markup.MarkupTest Base
java -ea -classpath ./prog-intro-2024/artifacts/SumTest.jar:./prog-intro/java-solutions/ -jar ./prog-intro-2024/artifacts/SumTest.jar Float