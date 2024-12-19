#!/bin/bash
javac -classpath ./prog-intro/java-solutions:./prog-intro-2024/java prog-intro/java-solutions/expression/*.java prog-intro/java-solutions/expression/parser/*.java prog-intro/java-solutions/expression/exceptions/*.java prog-intro/java-solutions/expression/generic/**/*.java
javac -classpath ./prog-intro/java-solutions:./prog-intro-2024/java prog-intro-2024/java/expression/generic/GenericTester.java
javac -classpath ./prog-intro/java-solutions:./prog-intro-2024/java prog-intro-2024/java/expression/generic/GenericTest.java
java -ea -cp ./prog-intro/java-solutions:./prog-intro-2024/java expression.generic.GenericTest easy Base
java -ea -cp ./prog-intro/java-solutions:./prog-intro-2024/java expression.generic.GenericTest easy Parens
