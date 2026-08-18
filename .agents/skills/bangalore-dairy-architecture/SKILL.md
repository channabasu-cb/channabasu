---
name: bangalore-dairy-architecture
description: >-
  Architectural guide, microservices blueprint, and operational runbook for the Bangalore Dairy
  Platform. Use when extending Spring Boot services, configuring Apache Kafka event streams,
  managing Redis caching layers, or modifying PostgreSQL/MySQL/MongoDB schemas.
---

# Bangalore Dairy Platform Architecture Skill

## 1. Overview & System Boundaries

The **Bangalore Dairy Platform** is an enterprise-grade, distributed microservices application designed for daily milk subscriptions and on-demand dairy delivery in Bengaluru.

```
[ Frontend (HTML5/CSS3/JS/SPA) ]
              │ (REST / JSON)
              ▼
    [ API Gateway :8080 ]
   ├── /api/v1/auth/**          ──▶ [ Auth Service :8081 ] (PostgreSQL / JWT)
   ├── /api/v1/products/**      ──▶ [ Catalog Service :8082 ] (Redis Cache / MongoDB / JPA)
   ├── /api/v1/orders/**        ──▶ [ Order Service :8083 ] (Kafka Producer / JPA)
   └── /api/v1/notifications/** ──▶ [ Notification Service :8084 ] (Kafka Consumer / JavaMail)
```

---

## 2. Microservice Directory Structure

| Service Name | Port | Primary Tech Stack | Responsibilities |
|---|---|---|---|
| `api-gateway` | `8080` | Spring Cloud Gateway, Reactive Web | Request routing, CORS filtering, rate limiting, and centralized ingress. |
| `auth-service` | `8081` | Spring Boot 3, Spring Security, JJWT, JPA | Customer registration, login, JWT token issuance, address book, wallet balance. |
| `catalog-service` | `8082` | Spring Data Redis, Spring Data JPA / MongoDB | Product catalog, dairy categories, stock inventory, Redis `@Cacheable` caching. |
| `order-service` | `8083` | Spring Boot 3, Spring Data JPA, Spring Kafka | Shopping cart, daily recurring subscriptions, checkout, Kafka `OrderCreatedEvent` publisher. |
| `notification-service` | `8084` | Spring Kafka, Spring Boot Starter Mail | Kafka `@KafkaListener` consumer for `dairy.orders.created`, HTML email generation & dispatch. |

---

## 3. Kafka Event Catalog

### Topic: `dairy.orders.created`
- **Producer**: `order-service` (`OrderEventProducer.java`)
- **Consumer**: `notification-service` (`OrderEventConsumer.java`)
- **Payload Structure**:
```json
{
  "orderId": 10042,
  "orderNumber": "BLR-DRY-20260818-A89F",
  "userId": 1,
  "customerName": "Channabasappa Ullagaddi",
  "customerEmail": "channa@bangaloredairy.in",
  "orderType": "ON_DEMAND",
  "orderStatus": "CONFIRMED",
  "deliverySlot": "MORNING_5_30_AM",
  "deliveryDate": "2026-08-19",
  "deliveryAddress": "#128, 4th Cross, CMH Road, Indiranagar, Bangalore",
  "pincode": "560038",
  "subtotal": 128.00,
  "deliveryFee": 0.00,
  "totalAmount": 128.00,
  "paymentMode": "WALLET",
  "paymentStatus": "PAID",
  "items": [
    {
      "productId": 1,
      "productName": "Nandini Toned Milk (Blue Pouch)",
      "unitSize": "500 ml",
      "unitPrice": 22.00,
      "quantity": 2,
      "totalPrice": 44.00
    }
  ]
}
```

---

## 4. Redis Caching Strategy

In `catalog-service`, products and categories are cached with a 10-minute TTL:
- `@Cacheable(value = "products", key = "'all'")`: Caches full product list.
- `@Cacheable(value = "products", key = "'category_' + #categoryId")`: Caches category-filtered products.
- `@CacheEvict(value = {"products", "categories"}, allEntries = true)`: Evicts cache whenever admin modifies products or inventory.

---

## 5. Running & Testing

### Docker Compose
Run the entire platform including databases, Kafka, Redis, and microservices:
```bash
cd BangloreDairy
docker compose up -d
```

### Local Standalone Frontend
Open `BangloreDairy/frontend/index.html` in any modern browser. It automatically detects backend availability and functions in standalone simulation mode with instant response when offline.
