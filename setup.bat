@echo off
mkdir output
copy src\*.java output\
copy src\*.css output\
cd output
javac --module-path "C:\Program Files\Java\javafx-sdk-11.0.2\lib" --add-modules javafx.controls Main.java
del *.java
cd ..