#!/bin/bash

javac md2html/*.java md2html/markup/*.java && java -ea -jar Md2HtmlTest.jar $@