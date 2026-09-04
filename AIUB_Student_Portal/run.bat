@echo off
title AIUB Student Portal - 1-Click Runner
echo =========================================================================
echo   AMERICAN INTERNATIONAL UNIVERSITY-BANGLADESH (AIUB)
echo   Student & Faculty Portal System - Pure Java Swing Application
echo   Compiling Packages: model/, service/, gui/, Start.java
echo =========================================================================
if not exist bin mkdir bin

javac -encoding UTF-8 -d bin model\*.java service\*.java gui\*.java Start.java
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Compilation failed! Please ensure JDK is installed and in PATH.
    pause
    exit /b %ERRORLEVEL%
)

echo [OK] Compilation successful!
echo Launching AIUB Portal GUI...
java -cp bin Start
pause
