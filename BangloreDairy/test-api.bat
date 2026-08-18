@echo off
REM =========================================================================
REM Bangalore Dairy Platform - Automated REST API Test Suite (cURL)
REM Tests Auth, Products (Redis Cached), Orders (Kafka Event), and Emails
REM =========================================================================

set BASE_URL=http://localhost:8080/api/v1
echo ========================================================
echo   🥛 Bangalore Dairy Platform - API Verification Suite
echo ========================================================
echo Target Gateway: %BASE_URL%
echo.

REM 1. Health Check / Products
echo [1/5] Testing GET /api/v1/products (Catalog & Redis Cache)...
curl -s -X GET "%BASE_URL%/products" -H "Content-Type: application/json"
echo.
echo.

REM 2. Customer Login
echo [2/5] Testing POST /api/v1/auth/login (JWT Authentication)...
curl -s -X POST "%BASE_URL%/auth/login" ^
  -H "Content-Type: application/json" ^
  -d "{\"email\":\"channa@bangaloredairy.in\",\"password\":\"password123\"}"
echo.
echo.

REM 3. Place On-Demand Order (Triggers Kafka Event)
echo [3/5] Testing POST /api/v1/orders (Place Order & Publish Kafka Event)...
curl -s -X POST "%BASE_URL%/orders" ^
  -H "Content-Type: application/json" ^
  -d "{\"userId\":1,\"customerName\":\"Channabasappa Ullagaddi\",\"customerEmail\":\"channa@bangaloredairy.in\",\"customerPhone\":\"+91 98450 12345\",\"orderType\":\"ON_DEMAND\",\"deliverySlot\":\"MORNING_5_30_AM\",\"deliveryDate\":\"2026-08-19\",\"deliveryAddress\":\"#128, 4th Cross, Indiranagar, Bangalore\",\"pincode\":\"560038\",\"paymentMode\":\"WALLET\",\"deliveryFee\":0.0,\"items\":[{\"productId\":1,\"productName\":\"Nandini Toned Milk (Blue Pouch)\",\"unitSize\":\"500 ml\",\"unitPrice\":22.0,\"quantity\":2,\"totalPrice\":44.0}]}"
echo.
echo.

REM 4. Activate Daily Subscription
echo [4/5] Testing POST /api/v1/subscriptions (Daily Milk Subscription)...
curl -s -X POST "%BASE_URL%/subscriptions" ^
  -H "Content-Type: application/json" ^
  -d "{\"userId\":1,\"productId\":1,\"quantity\":2,\"frequency\":\"DAILY\",\"deliverySlot\":\"MORNING_5_30_AM\",\"startDate\":\"2026-08-19\",\"deliveryAddress\":\"#128, 4th Cross, Indiranagar\",\"pincode\":\"560038\"}"
echo.
echo.

REM 5. Query Dispatched Email Notifications (Kafka Consumer)
echo [5/5] Testing GET /api/v1/notifications/recent (Dispatched Email Logs)...
curl -s -X GET "%BASE_URL%/notifications/recent" -H "Content-Type: application/json"
echo.
echo.

echo ========================================================
echo   ✓ Verification Tests Completed!
echo ========================================================
