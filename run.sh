#!/bin/bash

javac -classpath ./prog-intro/java-solutions/*.java
javac -classpath ./prog-intro/java-solutions:./prog-intro-2024/java prog-intro-2024/java/markup/MarkupTest.java

java -ea -cp ./prog-intro-2024/artifacts/SumTest.jar:./prog-intro/java-solutions sum.SumTest Float
java -ea -cp ./prog-intro-2024/artifacts/ReverseTest.jar:./prog-intro/java-solutions reverse.ReverseTest Odd
java -ea -cp ./prog-intro-2024/artifacts/WordStatTest.jar:./prog-intro/java-solutions wordStat.WordStatTest Words
java -ea -cp ./prog-intro-2024/artifacts/FastReverseTest.jar:./prog-intro/java-solutions reverse.FastReverseTest.jar OddOct
java -ea -cp ./prog-intro-2024/artifacts/WsppTest.jar:./prog-intro/java-solutions wspp.WsppTest Position
java -ea -cp ./prog-intro/java-solutions:./prog-intro-2024/java markup.MarkupTest Typst