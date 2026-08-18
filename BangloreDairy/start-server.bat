@echo off
REM =========================================================================
REM Bangalore Dairy - Zero Dependency Local Server
REM =========================================================================
echo Starting Bangalore Dairy application server on port 3000...
powershell -ExecutionPolicy Bypass -File "%~dp0serve.ps1" -Port 3000
pause
