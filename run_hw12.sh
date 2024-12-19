#!/bin/bash
javac -classpath ./prog-intro/java-solutions:./prog-intro-2024/java prog-intro/java-solutions/expression/*.java prog-intro/java-solutions/expression/parser/*.java

javac -classpath ./prog-intro/java-solutions:./prog-intro-2024/java prog-intro-2024/java/expression/ExpressionTester.java
javac -classpath ./prog-intro/java-solutions:./prog-intro-2024/java prog-intro-2024/java/expression/TripleExpression.java
java -ea -cp ./prog-intro/java-solutions:./prog-intro-2024/java expression.TripleExpression hard Triple

javac -classpath ./prog-intro/java-solutions:./prog-intro-2024/java prog-intro-2024/java/expression/parser/TripleParser.java
javac -classpath ./prog-intro/java-solutions:./prog-intro-2024/java prog-intro-2024/java/expression/parser/ParserTest.java
java -ea -cp ./prog-intro/java-solutions:./prog-intro-2024/java expression.parser.ParserTest easy Base
java -ea -cp ./prog-intro/java-solutions:./prog-intro-2024/java expression.parser.ParserTest easy LastVars
java -ea -cp ./prog-intro/java-solutions:./prog-intro-2024/java expression.parser.ParserTest easy Cbrt


javac -classpath ./prog-intro/java-solutions:./prog-intro-2024/java prog-intro-2024/java/expression/exceptions/TripleParser.java
javac -classpath ./prog-intro/java-solutions:./prog-intro-2024/java prog-intro-2024/java/expression/exceptions/ExceptionsTest.java
javac -classpath ./prog-intro/java-solutions:./prog-intro-2024/java prog-intro-2024/java/expression/exceptions/ExceptionsTestSet.java
java -ea -cp ./prog-intro/java-solutions:./prog-intro-2024/java expression.exceptions.ExceptionsTest easy LastVars
java -ea -cp ./prog-intro/java-solutions:./prog-intro-2024/java expression.exceptions.ExceptionsTest easy Geom
