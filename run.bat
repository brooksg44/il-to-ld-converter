@echo off
REM IL to LD Converter Launch Script for Windows

echo Starting IL to LD Converter...

REM Check if Leiningen is installed
where lein >nul 2>nul
if %errorlevel% neq 0 (
    echo Error: Leiningen is not installed.
    echo Please install Leiningen from: https://leiningen.org/
    pause
    exit /b 1
)

REM Check Java version
java -version

REM Run the application
if "%1"=="--console" (
    echo Running in console mode...
    lein run --console
) else (
    echo Running in GUI mode...
    lein run
)

pause
