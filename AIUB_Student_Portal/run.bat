@echo off
echo =========================================================================
echo   AMERICAN INTERNATIONAL UNIVERSITY-BANGLADESH (AIUB)
echo   Student Portal System
echo   Compiling Packages: model/, service/, gui/, Start.java
echo =========================================================================

if not exist bin mkdir bin

REM Compiling java files using forward slashes to avoid wildcard errors
javac -d bin model/*.java service/*.java gui/*.java Start.java

if %errorlevel% neq 0 (
    echo [ERROR] Compilation failed! Please ensure JDK is installed and in PATH.
    pause
    exit /b %errorlevel%
)

echo.
echo Running Application...
java -cp bin Start
pause