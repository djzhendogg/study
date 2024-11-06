#!/bin/bash

javac -classpath ./md2html *.java && java -ea -jar Md2HtmlTest.jar $@