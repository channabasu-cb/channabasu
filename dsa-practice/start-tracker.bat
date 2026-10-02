@echo off
title DSA 60-Day Interview Prep Tracker
echo ========================================================
echo   Starting DSA 60-Day Interview Prep Tracker...
echo ========================================================

set "TRACKER_DIR=%~dp0tracker"
set "NODE_DIR=%TRACKER_DIR%\.node\node-v20.18.0-win-x64"

if exist "%NODE_DIR%\node.exe" (
    set "PATH=%NODE_DIR%;%PATH%"
)

cd /d "%TRACKER_DIR%"

if not exist "%TRACKER_DIR%\node_modules" (
    echo Installing dependencies...
    call npm install
)

echo.
echo Opening browser at http://localhost:3000 ...
start http://localhost:3000

echo Starting server...
node server.js

pause
