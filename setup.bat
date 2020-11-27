@echo off
set current_dir=%cd%\fxlib\javafx-sdk-11.0.2\lib
echo The JavaFX directory is this: %current_dir%
mkdir output
copy src\*.java output\
copy src\*.css output\
cd output
javac --module-path %current_dir% --add-modules javafx.controls Main.java
del *.java
cd ..