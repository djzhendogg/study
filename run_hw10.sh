#!/bin/bash

javac -classpath ./prog-intro/java-solutions/*.java
javac -classpath ./prog-intro/java-solutions:./prog-intro-2024/java prog-intro-2024/java/markup/MarkupTest.java
javac -classpath ./prog-intro/java-solutions prog-intro/java-solutions/md2html/*.java
javac -classpath ./prog-intro/java-solutions:./prog-intro-2024/java prog-intro-2024/java/expression/ExpressionTest.java
javac -classpath ./prog-intro/java-solutions:./prog-intro-2024/java prog-intro/java-solutions/expression/*.java

java -ea -cp ./prog-intro/java-solutions:./prog-intro-2024/java expression.ExpressionTest easy Base
