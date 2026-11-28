@echo off
javac -cp ".;*" connection\*.java model\*.java view\*.java controller\*.java
java -cp ".;*" controller.MainController
pause