#!/bin/bash
javac -classpath ./prog-intro/java-solutions:./prog-intro-2024/java prog-intro/java-solutions/expression/*.java prog-intro/java-solutions/expression/parser/*.java
javac -classpath ./prog-intro/java-solutions:./prog-intro-2024/java prog-intro/java-solutions/expression/*.java

javac -classpath ./prog-intro/java-solutions:./prog-intro-2024/java prog-intro-2024/java/expression/ToMiniString.java

javac -classpath ./prog-intro/java-solutions:./prog-intro-2024/java prog-intro-2024/java/expression/Expression.java

javac -classpath ./prog-intro/java-solutions:./prog-intro-2024/java prog-intro-2024/java/expression/ExpressionTest.java
javac -classpath ./prog-intro/java-solutions:./prog-intro-2024/java prog-intro-2024/java/expression/ExpressionTester.java
javac -classpath ./prog-intro/java-solutions:./prog-intro-2024/java prog-intro-2024/java/expression/TripleExpression.java
java -ea -cp ./prog-intro/java-solutions:./prog-intro-2024/java expression.TripleExpression hard Triple


java -cp ./prog-intro/java-solutions game.Main

javac -classpath ./prog-intro/java-solutions:./prog-intro-2024/java prog-intro-2024/java/expression/parser/TripleParser.java
javac -classpath ./prog-intro/java-solutions:./prog-intro-2024/java prog-intro-2024/java/expression/parser/ParserTest.java
java -ea -cp ./prog-intro/java-solutions:./prog-intro-2024/java expression.parser.ParserTest easy Base


javac -classpath ./prog-intro/java-solutions:./prog-intro-2024/java prog-intro-2024/java/expression/exceptions/TripleParser.java
javac -classpath ./prog-intro/java-solutions:./prog-intro-2024/java prog-intro-2024/java/expression/exceptions/ExceptionsTest.java
javac -classpath ./prog-intro/java-solutions:./prog-intro-2024/java prog-intro-2024/java/expression/exceptions/ExceptionsTestSet.java
java -ea -cp ./prog-intro/java-solutions:./prog-intro-2024/java expression.exceptions.ExceptionsTest easy Base
