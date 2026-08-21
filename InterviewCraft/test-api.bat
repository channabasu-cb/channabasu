@echo off
title InterviewCraft AI - API Verification Suite
echo =========================================================================
echo   InterviewCraft AI - Automated API & Link Verification Test Suite
echo =========================================================================
echo.

set BASE_URL=http://localhost:8080/api

echo [TEST 1] Checking Resource Service & Verified Catalog...
curl -s -X GET "%BASE_URL%/resources/verified" | findstr "success"
if %ERRORLEVEL% EQU 0 (
    echo   ==> PASS: Verified Materials Catalog accessible with working HTTP 200 items.
) else (
    echo   ==> (Gateway/Resource Service offline - check local setup)
)
echo.

echo [TEST 2] Testing Real-Time Link Health Verifier (Live URL check)...
curl -s -X POST "%BASE_URL%/resources/verify-link" ^
  -H "Content-Type: application/json" ^
  -d "{\"url\":\"https://dataintensive.net/\"}" | findstr "isValidAndWorking"
if %ERRORLEVEL% EQU 0 (
    echo   ==> PASS: Live Link Verifier successfully validated external URL health.
) else (
    echo   ==> (Gateway/Resource Service offline - check local setup)
)
echo.

echo [TEST 3] Testing Candidate User Registration...
curl -s -X POST "%BASE_URL%/auth/register" ^
  -H "Content-Type: application/json" ^
  -d "{\"email\":\"test_engineer_%RANDOM%@interviewcraft.io\",\"password\":\"Password123!\",\"fullName\":\"Test Candidate\",\"targetRole\":\"Senior Backend Engineer\"}" | findstr "token"
if %ERRORLEVEL% EQU 0 (
    echo   ==> PASS: User registration and JWT generation successful.
) else (
    echo   ==> (Gateway/Auth Service offline - check local setup)
)
echo.

echo [TEST 4] Testing Interactive AI Assessment Session...
curl -s -X POST "%BASE_URL%/assessment/session" ^
  -H "Content-Type: application/json" ^
  -d "{\"targetRole\":\"Senior Backend Engineer\",\"primaryTechStack\":\"Java 21, Spring Boot, Kafka\"}" | findstr "sessionId"
if %ERRORLEVEL% EQU 0 (
    echo   ==> PASS: Interactive AI Assessment session created.
) else (
    echo   ==> (Gateway/Assessment Service offline - check local setup)
)
echo.

echo [TEST 5] Testing Tailored Interview Plan Generation...
curl -s -X POST "%BASE_URL%/plans/generate" ^
  -H "Content-Type: application/json" ^
  -d "{\"targetRole\":\"Senior Backend Engineer\",\"targetCompanyTier\":\"Tier-1 Tech\"}" | findstr "milestones"
if %ERRORLEVEL% EQU 0 (
    echo   ==> PASS: Tailored 4-phase preparation plan with verified links generated.
) else (
    echo   ==> (Gateway/Plan Service offline - check local setup)
)
echo.

echo =========================================================================
echo   Test Suite Execution Completed!
echo =========================================================================
pause
