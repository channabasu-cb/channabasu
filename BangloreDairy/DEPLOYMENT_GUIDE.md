# 🚀 Bangalore Dairy Platform - Build, Deploy & Test Guide

This guide provides step-by-step instructions for building, deploying, and testing the **Bangalore Dairy Platform** using **100% Free & Open-Source Tools**, covering both **Plan 1: Local Access** and **Plan 2: Global Public Access**.

---

## 🛠️ Free & Open-Source Tool Ecosystem

| Purpose | Free & Open-Source Tools | Role in Bangalore Dairy Platform |
|---|---|---|
| **Build & Compile** | **Eclipse Temurin OpenJDK 21**, **Apache Maven** | Compiles Java microservices, runs unit tests, generates executable `.jar` artifacts. |
| **Containers & Orchestration** | **Docker Community Edition**, **Docker Compose**, **Podman** | Packages microservices, PostgreSQL, Kafka, Redis, and Mailpit in isolated containers. |
| **Local SMTP / Email Testing** | **Mailpit** (Open-Source) / **Inbucket** | Captures all outgoing emails, provides a local web UI (`:8025`) to view HTML emails without real email delivery limits. |
| **Kafka Inspection** | **Kafka UI** (Provectus Open-Source) | Visual Web UI (`:8090`) to monitor Kafka topics (`dairy.orders.created`), messages, and consumer groups. |
| **API Testing** | **cURL**, **Bruno** (Open Source Postman alternative), **Postman (Free)** | Executes automated test scripts and validates REST endpoints. |
| **Load & Stress Testing** | **k6** (Grafana Open-Source), **Apache JMeter** | Simulates 10,000+ daily milk orders and morning checkout traffic. |
| **Global Tunneling (Zero Cost)** | **Cloudflare Tunnels (`cloudflared`)**, **ngrok (Free)**, **localtunnel** | Securely exposes local microservices to the global internet without opening firewall ports or buying static IPs. |
| **Global Cloud Deployment** | **Oracle Cloud Always Free Tier**, **Render / Fly.io (Free Tier)**, **Cloudflare Pages / GitHub Pages** | Hosts microservices, databases, and static frontends globally at $0 cost. |

---

# 💻 PLAN 1: Local Access (Development & Testing)

### Step 1: Install Prerequisites (One-time Setup)
You can install all necessary open-source tools on Windows using `winget`:
```powershell
# Open PowerShell as Administrator and run:
winget install EclipseAdoptium.Temurin.21.JDK
winget install Apache.Maven
winget install Docker.DockerDesktop  # Or Podman Desktop
```

### Step 2: Build All Microservices
Open a terminal in the `BangloreDairy` root directory and build the multi-module project:
```bash
cd d:\GitCode\channabasu\BangloreDairy

# Build all modules and package JARs
mvn clean package -DskipTests
```

### Step 3: Run the Full Stack with Docker Compose
Start all databases, message brokers, caching, and microservices in one command:
```bash
docker compose up --build -d
```

### Step 4: Verify Running Services
Check container status:
```bash
docker compose ps
```

| Service | Access URL | Credentials / Notes |
|---|---|---|
| **API Gateway** | `http://localhost:8080` | Entry point for all REST requests |
| **Auth Service** | `http://localhost:8081` | Direct Auth Microservice |
| **Catalog Service** | `http://localhost:8082` | Direct Catalog Microservice |
| **Order Service** | `http://localhost:8083` | Direct Order Microservice |
| **Notification Service** | `http://localhost:8084` | Direct Notification Microservice |
| **Kafka UI Dashboard** | `http://localhost:8090` | Inspect `dairy.orders.created` topic messages |
| **Mailpit (Email Web UI)**| `http://localhost:8025` | Inspect all sent HTML order emails |
| **PostgreSQL Database** | `localhost:5432` | DB: `bangalore_dairy_db`, User: `dairy_admin`, Pass: `dairy_password_123` |
| **Redis Cache** | `localhost:6379` | Password: `redis_password_123` |
| **MongoDB Store** | `localhost:27017` | User: `mongo_admin`, Pass: `mongo_password_123` |
| **Web Frontend** | `file:///d:/GitCode/channabasu/BangloreDairy/frontend/index.html` | Or host via `npx serve frontend` |

### Step 5: Test the Application
1. **Automated Script**:
   Run the included test script in `BangloreDairy`:
   ```cmd
   test-api.bat
   ```
2. **Interactive UI Testing**:
   - Open `BangloreDairy/frontend/index.html` in your browser.
   - Click **Quick Demo: Channabasu (Customer)** to log in.
   - Browse milk pouches, curd, and Mysore Pak.
   - Set up a **Daily Milk Subscription** (5:30 AM Morning delivery).
   - Add items to Cart -> Select **Dairy Wallet** -> Click **Place Order**.
   - Open the **Email Center** or Mailpit (`http://localhost:8025`) to see the rendered HTML invoice and delivery confirmation!

---

# 🌐 PLAN 2: Global Project Access (Production / Public Showcase)

To share the Bangalore Dairy platform globally with clients, reviewers, or team members, you have two flexible, zero-cost approaches:

---

### Option A: Cloudflare Tunnels (Zero Cloud Hosting Cost, Runs from Local Machine)
Expose your local Docker microservices stack directly to a public HTTPS URL using Cloudflare's 100% free tunnel tool (`cloudflared`).

1. **Install Cloudflared**:
   ```powershell
   winget install Cloudflare.cloudflared
   ```
2. **Launch Public Tunnel for API Gateway & Frontend**:
   ```bash
   cloudflared tunnel --url http://localhost:8080
   ```
   *Cloudflare will generate a free public URL like: `https://dairy-bengaluru-xyz.trycloudflare.com`.*
3. **Launch Public Tunnel for Mailpit (Email Inspector)**:
   ```bash
   cloudflared tunnel --url http://localhost:8025
   ```
   *Anyone worldwide can now view live order events, place dairy subscriptions, and inspect real-time HTML email dispatches!*

---

### Option B: Cloud Hosting on Free Tier (Oracle Cloud / Render / GitHub Pages)

#### 1. Frontend Hosting (Free Forever):
- **GitHub Pages / Cloudflare Pages / Vercel**:
  - Deploy `BangloreDairy/frontend/` with zero build configuration.
  - Generates instant global CDN URL (e.g., `https://bangaloredairy.pages.dev`).

#### 2. Backend & Database Hosting:
- **Oracle Cloud Always Free Tier**:
  - Provides **4 OCPU ARM Compute cores + 24 GB RAM + 200 GB Storage** free forever!
  - Clone this repository onto the Oracle Linux VM:
    ```bash
    git clone <your-repo-url>
    cd BangloreDairy
    docker compose up -d
    ```
- **Render / Fly.io**:
  - Deploy each Dockerized service (`api-gateway`, `auth-service`, `catalog-service`, `order-service`, `notification-service`) as a Web Service.
  - Connect a free managed PostgreSQL instance and Redis instance (e.g., Upstash Redis Free Tier).

#### 3. Continuous Integration & Deployment (CI/CD with GitHub Actions):
Create `.github/workflows/deploy.yml` to automatically build and test every Git commit:
```yaml
name: Bangalore Dairy CI/CD

on:
  push:
    branches: [ main ]

jobs:
  build-and-test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Set up JDK 21
        uses: actions/setup-java@v4
        with:
          java-version: '21'
          distribution: 'temurin'
      - name: Build with Maven
        run: |
          cd BangloreDairy
          mvn clean package -DskipTests
      - name: Run Docker Compose Integration Tests
        run: |
          cd BangloreDairy
          docker compose up -d
          sleep 30
          curl -f http://localhost:8080/api/v1/products || exit 1
```

---

## 🧪 Comprehensive Testing Strategy

| Test Layer | Open-Source Tool | Command / Action |
|---|---|---|
| **Unit & JPA Integration Tests** | JUnit 5 + Mockito + Testcontainers | `mvn test` |
| **REST API Smoke Test** | `test-api.bat` / cURL | `./test-api.bat` |
| **Kafka Event Stream Verification** | Kafka UI | Navigate to `http://localhost:8090/ui/clusters/bangalore-dairy-cluster/topics/dairy.orders.created` |
| **Email Template Rendering** | Mailpit Web UI | Open `http://localhost:8025` to inspect HTML responsive layout |
| **End-to-End User Journeys** | Chrome DevTools / Browser UI | Open `frontend/index.html` (Register -> Subscribe -> Order -> Track) |
| **High Concurrency Load Test** | k6 | `k6 run load-test.js` (Simulates 500 concurrent morning orders) |
