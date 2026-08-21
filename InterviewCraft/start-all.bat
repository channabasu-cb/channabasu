@echo off
title InterviewCraft AI - Platform Launcher
echo ========================================================
echo   InterviewCraft AI Microservices Platform Launcher
echo ========================================================
echo.

cd /d %~dp0

echo [1/3] Checking Docker Compose availability...
docker --version >nul 2>&1
if %ERRORLEVEL% EQU 0 (
    echo Docker detected. Launching full containerized ecosystem...
    docker compose up --build -d
    echo.
    echo ========================================================
    echo   All Microservices are up and running!
    echo   - Web Dashboard:     http://localhost:3000
    echo   - API Gateway:       http://localhost:8080
    echo   - Auth Service:      http://localhost:8081/swagger-ui.html
    echo   - Assessment AI:     http://localhost:8082/swagger-ui.html
    echo   - Plan Service:      http://localhost:8083/swagger-ui.html
    echo   - Resource Verifier: http://localhost:8084/swagger-ui.html
    echo ========================================================
    pause
    exit /b 0
)

echo [2/3] Docker not detected. Launching Frontend and instructions...
start "" "%~dp0interviewcraft-frontend\index.html"
echo.
echo Opened InterviewCraft Web Dashboard in your browser!
echo To run backend microservices without Docker, execute:
echo   mvn clean package -DskipTests
echo and start each service using 'java -jar target/*.jar'
pause
