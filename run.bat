@echo off
set current_dir=%cd%\fxlib\javafx-sdk-11.0.2\lib
echo The JavaFX directory is this: %current_dir%
cd output
java --module-path %current_dir% --add-modules javafx.controls Main
cd ..