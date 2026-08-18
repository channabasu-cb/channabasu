# 📖 Bangalore Dairy Platform - REST API Reference

All requests can be routed through the **API Gateway** on port `8080` (Base URL: `http://localhost:8080/api/v1`).

---

## 1. Authentication Service (`/api/v1/auth`)

### 1.1 Customer Login
- **Endpoint**: `POST /api/v1/auth/login`
- **Request Body**:
```json
{
  "email": "channa@bangaloredairy.in",
  "password": "password123"
}
```
- **Response**:
```json
{
  "success": true,
  "message": "Login successful. Welcome back!",
  "data": {
    "id": 1,
    "name": "Channabasappa Ullagaddi",
    "email": "channa@bangaloredairy.in",
    "phone": "+91 98450 12345",
    "address": "#128, 4th Cross, CMH Road, Indiranagar",
    "area": "Indiranagar",
    "pincode": "560038",
    "walletBalance": 1250.00,
    "role": "ROLE_CUSTOMER",
    "token": "eyJhbGciOiJIUzI1NiJ9..."
  },
  "timestamp": "2026-08-18T05:45:00"
}
```

### 1.2 Customer Registration
- **Endpoint**: `POST /api/v1/auth/register`
- **Request Body**:
```json
{
  "name": "Ramesh Kumar",
  "email": "ramesh@example.com",
  "password": "securepassword",
  "phone": "+91 98765 43210",
  "address": "#42, 100ft Road",
  "area": "Indiranagar",
  "pincode": "560038"
}
```

---

## 2. Product Catalog Service (`/api/v1/products`)

### 2.1 Get All Products (Cached in Redis)
- **Endpoint**: `GET /api/v1/products`
- **Query Params**:
  - `categoryId` (Optional): Filter by category ID.
  - `subscriptionOnly` (Optional `true`/`false`): Filter only daily subscription eligible items.

### 2.2 Get Product Details
- **Endpoint**: `GET /api/v1/products/{id}`

---

## 3. Order Service (`/api/v1/orders`)

### 3.1 Place Order (Triggers Kafka Event)
- **Endpoint**: `POST /api/v1/orders`
- **Request Body**:
```json
{
  "userId": 1,
  "customerName": "Channabasappa Ullagaddi",
  "customerEmail": "channa@bangaloredairy.in",
  "customerPhone": "+91 98450 12345",
  "orderType": "ON_DEMAND",
  "deliverySlot": "MORNING_5_30_AM",
  "deliveryDate": "2026-08-19",
  "deliveryAddress": "#128, 4th Cross, CMH Road, Indiranagar, Bangalore",
  "pincode": "560038",
  "paymentMode": "WALLET",
  "deliveryFee": 0.00,
  "items": [
    {
      "productId": 1,
      "productName": "Nandini Toned Milk (Blue Pouch)",
      "unitSize": "500 ml",
      "unitPrice": 22.00,
      "quantity": 2
    }
  ]
}
```

### 3.2 Get Customer Order History
- **Endpoint**: `GET /api/v1/orders/user/{userId}`

---

## 4. Subscriptions Service (`/api/v1/subscriptions`)

### 4.1 Activate Daily Subscription
- **Endpoint**: `POST /api/v1/subscriptions`
- **Request Body**:
```json
{
  "userId": 1,
  "productId": 1,
  "quantity": 2,
  "frequency": "DAILY",
  "deliverySlot": "MORNING_5_30_AM",
  "startDate": "2026-08-19",
  "deliveryAddress": "#128, 4th Cross, CMH Road, Indiranagar",
  "pincode": "560038"
}
```

---

## 5. Notification Service (`/api/v1/notifications`)

### 5.1 Query Sent Email Logs
- **Endpoint**: `GET /api/v1/notifications/recent`
