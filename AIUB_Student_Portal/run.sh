#!/usr/bin/env bash
echo "========================================================================="
echo "  AMERICAN INTERNATIONAL UNIVERSITY-BANGLADESH (AIUB)"
echo "  Student & Faculty Portal System - Pure Java Swing Application"
echo "  Compiling Packages: model/, service/, gui/, Start.java"
echo "========================================================================="
mkdir -p bin

javac -encoding UTF-8 -d bin model/*.java service/*.java gui/*.java Start.java
if [ $? -ne 0 ]; then
    echo "[ERROR] Compilation failed! Please ensure JDK is installed."
    exit 1
fi

echo "[OK] Compilation successful! Launching AIUB Portal GUI..."
java -cp bin Start
