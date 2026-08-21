# 🚀 InterviewCraft AI Platform

> **Enterprise AI-Powered Interview Preparation & Verified Resource Microservices Platform**  
> Built with **Java 21**, **Spring Boot 3.3.3**, **Spring Cloud Gateway**, **Google Gemini AI**, **PostgreSQL**, **Redis**, and an automated **Real-Time Link & Content Verification Engine**.

---

## 🌟 Key Capabilities

1. **🤖 Interactive Multi-Turn AI Assessment**:
   - Conversational AI consultation powered by **Google Gemini API** (with resilient adaptive fallback).
   - Deeply analyzes target roles, years of experience, core tech stack, and algorithmic/system design proficiencies.
   - Extracts candidate strengths, hurdles, and recommended study milestones into dynamic profiles.

2. **🔍 Zero-Broken-Links Verification Engine**:
   - Real-time HTTP/HTTPS health checker evaluating status codes ($200\text{ OK}$), redirection handling, response latency, SSL integrity, and page titles.
   - Domain credibility scoring guarding against dead links, paywalled mirrors, or AI hallucinations.
   - Pre-seeded and live-verified knowledge repository covering **Books**, **YouTube Channels**, **Online Interactive Tutorials**, and **Official Documentation**.

3. **📅 Tailored Milestone Roadmap & Day-by-Day Checklist**:
   - Structured 4-phase preparation curriculum:
     - **Phase 1**: Algorithmic Patterns & Problem Solving Mastery (Blind 75 / Top 150)
     - **Phase 2**: Scalable Distributed Systems & High-Level Architecture (DDIA & ByteByteGo)
     - **Phase 3**: Java 21, Spring Boot 3 & Low-Level Clean Architecture (SOLID & Baeldung)
     - **Phase 4**: Behavioral Leadership (STAR Method) & Live Mock Simulations
   - Actionable daily tasks with study duration, direct verified resource links, and interactive completion tracking.

4. **✨ Ultra-Modern Responsive Dashboard**:
   - Glassmorphic UI with CSS custom properties, micro-animations, glowing indicators, and interactive progress gauges.

---

## 🏗️ Microservices Topology

| Service | Port | Description |
| :--- | :--- | :--- |
| **`interviewcraft-gateway`** | `8080` | Spring Cloud Gateway, route forwarding, CORS filters |
| **`interviewcraft-auth-service`** | `8081` | User registration, Login, JWT issuance, profile management |
| **`interviewcraft-assessment-service`** | `8082` | Interactive chat engine, Gemini AI client, skill matrix evaluation |
| **`interviewcraft-plan-service`** | `8083` | Tailored roadmap compiler, milestone manager, checklist progress |
| **`interviewcraft-resource-service`** | `8084` | Automated HTTP link verification engine & verified resource directory |
| **`interviewcraft-frontend`** | `3000` | Modern Single Page Application with interactive chat & roadmap |

---

## 🚀 Quick Start Guide

### Option 1: Docker Compose (Recommended)
```bash
# Set Gemini API Key (optional - adaptive mode available)
set GEMINI_API_KEY=your_google_gemini_api_key

# Start all microservices and databases
docker compose up --build -d
```
Access the application at:
- **Web Dashboard**: [http://localhost:3000](http://localhost:3000)
- **API Gateway**: [http://localhost:8080](http://localhost:8080)

### Option 2: Local Web UI Direct Preview
Double click or launch [interviewcraft-frontend/index.html](file:///d:/GitCode/channabasu/InterviewCraft/interviewcraft-frontend/index.html) in any modern browser for immediate interactive testing.

---

## 🧪 Automated API Testing
Run the included verification suite:
```cmd
test-api.bat
```
