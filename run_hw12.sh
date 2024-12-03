#!/bin/bash
javac -classpath ./prog-intro/java-solutions:./prog-intro-2024/java prog-intro/java-solutions/expression/*.java
javac -classpath ./prog-intro/java-solutions:./prog-intro-2024/java prog-intro-2024/java/expression/ExpressionTest.java
java -ea -cp ./prog-intro/java-solutions:./prog-intro-2024/java expression.ExpressionTest easy Base