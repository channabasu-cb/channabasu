# 🥛 Bangalore Dairy - Daily Milk & Dairy Ordering Platform

An enterprise-grade, distributed microservices platform for daily milk subscriptions and on-demand dairy delivery across Bengaluru. Built with **Java 21**, **Spring Boot 3.3.x**, **Apache Kafka**, **Redis**, **PostgreSQL**, **MongoDB**, and a responsive modern web application.

---

## 🌟 Key Features

1. **Daily Milk & Dairy Subscriptions**:
   - Schedule recurring deliveries (Everyday, Alternate Days, Weekdays only, Weekends).
   - Strict 9:00 PM cutoff engine for guaranteed 6:00 AM doorstep drop.
   - Flexible Pause & Resume for vacations.
2. **On-Demand Dairy Ordering & Instant Cart**:
   - Order Nandini Milk (Toned, Standardised, Full Cream, A2 Cow Milk), Curd, Benne (Butter), Pure Ghee, Paneer, and Traditional Bengaluru Mysore Pak / Dharwad Peda.
   - Automated delivery fee calculation (Free delivery above ₹199).
3. **Event-Driven Architecture with Apache Kafka**:
   - Asynchronous `dairy.orders.created` event publishing on order placement.
   - Decoupled `notification-service` consumes Kafka stream to build and dispatch responsive HTML emails.
4. **Multi-Database & High-Performance Caching**:
   - **PostgreSQL / MySQL**: ACID compliance for Users, Orders, Subscriptions, Payments, and Cart.
   - **MongoDB**: Flexible document store for daily delivery route sheets and catalog logs.
   - **Redis**: Low-latency caching (`@Cacheable("products")`, `@CacheEvict`) with automated TTL eviction.
5. **Spring Cloud API Gateway & Security**:
   - Centralized ingress router on port `8080` with CORS support and JWT validation.
   - Stateless authentication with BCrypt hashing and Dairy Wallet cashback bonuses.
6. **Live HTML Email Notification Center**:
   - Real-time email notification inspector and simulator directly in the web UI.

---

## 🏗️ Architecture Blueprint

```
+-----------------------------------------------------------------------------------+
|                           Bangalore Dairy Web Frontend                            |
|             (Product Catalog, Daily Subscription Planner, Cart, Email Center)     |
+-----------------------------------------+-----------------------------------------+
                                          | REST / JSON
                                          v
+-----------------------------------------------------------------------------------+
|                              API Gateway (Port 8080)                              |
|                          (Routing, CORS, Security Auth)                           |
+-----------+-----------------------------+-----------------------------+-----------+
            |                             |                             |
            v                             v                             v
+-----------------------+     +-----------------------+     +-----------------------+
|  Auth Service (:8081) |     | Catalog Service(:8082)|     |  Order Service (:8083)|
| - JWT Security        |     | - Products/Categories |     | - Daily Subscriptions |
| - Customer Wallet     |     | - Redis Caching Layer |     | - Cart & Checkout     |
| - PostgreSQL / H2     |     | - MongoDB / SQL Store |     | - PostgreSQL / JPA    |
+-----------------------+     +-----------------------+     +-----------+-----------+
                                                                        |
                                              Kafka Event Stream        |
                                       Topic: dairy.orders.created      v
                                                              +---------------------+
                                                              |Notification Service |
                                                              |      (Port 8084)    |
                                                              | - Kafka Consumer    |
                                                              | - Spring Boot Mail  |
                                                              | - Responsive HTML   |
                                                              +---------------------+
```

---

## 📂 Project Directory Structure

```
BangloreDairy/
├── pom.xml                         # Root Multi-Module Maven Configuration
├── docker-compose.yml              # PostgreSQL, Redis, Kafka, MongoDB, Mailpit & Services
├── README.md                       # Main Project Documentation
├── ARCHITECTURE.md                 # Detailed Architecture Specification
├── API_DOCUMENTATION.md            # REST API Reference
│
├── init-scripts/
│   └── postgres-init.sql           # Database DDL Schema and Bengaluru Dairy Seed Data
│
├── common-library/                 # Shared DTOs, Kafka Event Models, Responses
├── api-gateway/                    # Spring Cloud Gateway (Port 8080)
├── auth-service/                   # Customer Auth & Wallet Service (Port 8081)
├── catalog-service/                # Dairy Catalog & Redis Cache Service (Port 8082)
├── order-service/                  # Subscriptions & Order Service + Kafka Producer (Port 8083)
├── notification-service/           # Kafka Consumer & HTML Email Service (Port 8084)
│
└── frontend/                       # Bangalore Dairy Single Page Web Application
    ├── index.html                  # Semantic UI with Delivery Slot & Pincode Selector
    ├── css/
    │   └── styles.css              # Modern Bangalore Dairy Theme & Glassmorphism
    └── js/
        └── app.js                  # State Management, Cart, Kafka Emulation & Live Email Viewer
```

---

## 🚀 Quick Start Guide

### Option 1: Instant Browser Demo (Zero Dependencies Required)
Open `BangloreDairy/frontend/index.html` in any web browser.
- Browse the authentic Bangalore Dairy catalog.
- Add products to cart, choose delivery slots (Morning 5:30 AM / Evening 5:30 PM).
- Switch between **Buy Once** and **Daily Milk Subscription**.
- Place an order to trigger the Kafka event simulation and inspect the generated HTML email confirmation in the **Email Center**.

### Option 2: Run with Docker Compose (Full Stack Microservices)
Ensure Docker is installed and run:
```bash
cd BangloreDairy
docker compose up -d
```

Service URLs:
- **API Gateway**: `http://localhost:8080`
- **Kafka UI**: `http://localhost:8090`
- **Mailpit Email Inbox**: `http://localhost:8025`
- **PostgreSQL**: `localhost:5432` (`dairy_admin` / `dairy_password_123`)
- **Redis**: `localhost:6379`
- **MongoDB**: `localhost:27017`

---

## 🔑 Demo User Credentials

| Role | Email | Password | Initial Wallet |
|---|---|---|---|
| Customer | `channa@bangaloredairy.in` | `password123` | ₹1,250.00 |
| Operations Admin | `admin@bangaloredairy.in` | `admin123` | ₹5,000.00 |
