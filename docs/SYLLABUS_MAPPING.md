# AgriPulse Matrix Dashboard - Syllabus & Course Outcome Mapping

This document maps the AgriPulse Matrix Dashboard ("Golden Harvest") architecture, tech stack, and deliverables directly to the Mumbai University (MU) Semester 3 Mini Project 1-A and Web Technology Lab syllabus criteria.

---

## 1. Course Outcome (CO) Mapping Matrix

| MU Course Outcome / Requirement | AgriPulse System Implementation | Tech Stack & Verification |
| :--- | :--- | :--- |
| **CO1: Client-Server Decoupled Architecture** | Frontend decoupled into static HTML/CSS with modular JavaScript assets serving dynamic requests to backend APIs. | HTML5, Tailwind CSS, JavaScript (`fetch()` / `Axios`), Spring Boot `@RestController` |
| **CO2: Client-Side Input Validation & Asynchronous Operations** | Dynamic DOM updates, real-time input verification (email/phone/pH bounds), non-reloading data fetches. | `auth-validation.js`, `api-client.js`, Chart.js dynamic data binding |
| **CO3: RESTful Web Service Design & Error Interception** | Spring Controllers expose structured JSON endpoints with explicit HTTP status codes and global error handling. | Spring Boot REST, `ResponseEntity<T>`, `@ControllerAdvice` global exception handling |
| **CO4: Database Persistence & Relational Design** | Relational database mapping with entities (Users, Plots, Soil Records, Loans) backed by persistent storage. | Spring Data JPA, Hibernate, File-Backed H2 RDBMS (`jdbc:h2:file:./data/...`) |
| **CO5: Microservices & Container Orchestration** | Hybrid architecture pairing Spring Boot Java backend with a FastAPI Python NLP voice microservice in containers. | Docker, Docker Compose, Python FastAPI, Web Speech API |
| **CO6: Software Engineering Artifacts & Testing** | Complete design documentation including UML diagrams, test case matrices, and boundary validation specs. | Use-Case, ER, Class, Sequence Diagrams, and Test Case Matrix (`docs/`) |

---

## 2. Technology Stack Overview

- **Frontend:** HTML5, Tailwind CSS (CDN/Custom glassmorphism styling), JavaScript (ES6+ modular scripts), Chart.js[cite: 1, 3]
- **Backend:** Java 17, Spring Boot, Spring Data JPA, Spring Security (RBAC)[cite: 1, 2]
- **Database:** H2 Database Engine (File-backed local persistence)[cite: 1, 2]
- **AI/Voice Microservice:** Python 3, FastAPI, Web Speech API integration[cite: 1]
- **DevOps & Containerization:** Docker, Docker Compose[cite: 1]