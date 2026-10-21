# FindIt — Campus Lost & Found Web Portal

> A modern, full-stack campus Lost & Found management platform built with **Spring Boot 4** and **Angular 22**. Designed to centralize, secure, and accelerate the recovery of misplaced items across university campuses.

[![Spring Boot](https://img.shields.io/badge/Backend-Spring%20Boot%204.1.1-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Angular](https://img.shields.io/badge/Frontend-Angular%2022-DD0031?style=for-the-badge&logo=angular&logoColor=white)](https://angular.dev/)
[![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Database](https://img.shields.io/badge/Database-H2%20%2F%20MySQL-003B57?style=for-the-badge&logo=mysql&logoColor=white)](http://localhost:8081/h2-console)
[![Tests](https://img.shields.io/badge/Test%20Suite-8%20Passing-success?style=for-the-badge)](backend/src/test/)

---

## 📑 Formal Academic Lab Report
> [!IMPORTANT]
> This repository is submitted for the **Full Stack Web Development Lab** evaluation.  
> For the comprehensive academic report containing the full problem statement, ER diagram, all 11 step-by-step evidence screenshots, database schemas, and the 11-point test verification matrix, please refer to:  
> 📄 **[PROJECT_SUBMISSION_REPORT.md](PROJECT_SUBMISSION_REPORT.md)**

---

## 👥 Project Team & Contributors

Developed collaboratively by a team of 4 members:

| Contributor | Student ID | GitHub | Role | Assigned Module & Key Deliverables |
|---|---|---|---|---|
| **Adithya M Jogi** | `1BF25AI010` | [@adithyamjogi1](https://github.com/adithyamjogi1) | **Lead Author** | **User Authentication & Account Management**<br>• User registration and BCrypt password encryption<br>• User JPA entity, repository, and controller<br>• Base sign-in UI and session handling |
| **Preetham SJ** | `1BM26AI441-T` | [@PreethamSJ](https://github.com/PreethamSJ) | **Core Developer** | **Lost & Found Core & Monorepo Architecture**<br>• Full-stack unified monorepo setup<br>• Item JPA entity with `LONGTEXT` Base64 photo storage<br>• Real-time campus feed, search & category filtering<br>• Client-side HTML5 Canvas photo compressor<br>• Post ownership security & self-service password reset |
| **S Yogananda Reddy** | `1BM26AI403-T` | [@yogananda2007](https://github.com/yogananda2007) | **Contributor** | **Item Claims & Verification System**<br>• Claim submission workflow and proof verification logic<br>• Claim status transition handlers (`PENDING`, `APPROVED`, `REJECTED`)<br>• Claimant UI integration and status tracking |
| **Kaushal BM** | `1BF25AI061` | [@kaushalbm](https://github.com/kaushalbm) | **Contributor** | **Admin Moderation & Notifications**<br>• System notification center and moderation criteria<br>• Campus lost-and-found analytics & recovery audit metrics<br>• Item flag and administrative review endpoints |

---

## ✨ Key Features & Capabilities

- 🔍 **Instant Digital Reporting**: Report lost or found belongings with titles, categories, locations, descriptions, and pictures.
- 🖼️ **In-Database Photo Storage**: Images are compressed client-side using HTML5 Canvas and stored directly in the database as Base64 Data URLs—no local disk dependence.
- 🏷️ **Smart Categorization & Search**: Real-time reactive search by keyword, category pill filters (`Electronics`, `Bags`, `Documents`, `Keys`, `Clothing`), and status tabs (`All Items`, `Report Lost`, `Report Found`).
- 🛡️ **Post Ownership & Security**: Only the author who posted an item can edit or delete it; unauthorized classmates receive a `403 Forbidden` response.
- 🔑 **Account Self-Recovery**: In-place password reset with BCrypt hashing for forgotten credentials.
- ⚡ **Zoneless Angular 22 Performance**: Built using Angular's next-generation zoneless change detection for instant UI re-renders without change-detection lag.

---

## 📸 Application Preview

| Campus Feed & Category Filters | Report Lost Item Form |
|:---:|:---:|
| ![Feed](docs/screenshots/image4.png) | ![Report Form](docs/screenshots/image3.png) |

| Edit Item Modal (Owner Only) | Relational Database Console |
|:---:|:---:|
| ![Edit Modal](docs/screenshots/image6.png) | ![Database Console](docs/screenshots/image11.png) |

*(See all 11 detailed screenshots in the [Formal Submission Report](PROJECT_SUBMISSION_REPORT.md#7-screenshots-step-by-step-evidence))*

---

## 🏛️ System Architecture

```
findit/
├── backend/                       # Spring Boot 4.1.1 REST API (Java 21)
│   ├── src/main/java/com/findit/
│   │   ├── User.java              # User Entity (BCrypt password, email unique)
│   │   ├── UserController.java    # /api/users (register, login, reset-password)
│   │   ├── entity/Item.java       # Item Entity (LONGTEXT photo, user foreign key)
│   │   ├── controller/ItemController.java # /api/items (CRUD, search, filters, ownership)
│   │   └── FinditApplication.java # Entry point & H2 Web Console bean
│   └── src/test/                  # Automated integration tests (8/8 Passing)
│
├── src/                           # Angular 22 Single Page Application
│   ├── app/
│   │   ├── app.ts                 # Main component logic, Canvas compression, filters
│   │   ├── app.html               # Responsive template (Feed, Modals, Forms)
│   │   └── app.css                # Custom forest green design system
│   └── main.ts                    # Zoneless application bootstrap
│
├── docs/screenshots/              # High-resolution lab evaluation evidence images
├── PROJECT_SUBMISSION_REPORT.md   # Complete academic submission report
└── README.md                      # Project summary & developer overview
```

---

## 🚀 Quick Start Guide

### Prerequisites
- **Java 17 or 21** (`java -version`)
- **Node.js v18+ or v20+** (`node -v`)

### 1. Launch Backend (Port 8081)
```powershell
cd backend
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=h2"
```
*(On Linux/macOS: `./mvnw spring-boot:run -Dspring-boot.run.profiles=h2`)*

- **API URL**: `http://localhost:8081/api/items`
- **H2 Database Console**: `http://localhost:8081/h2-console`
  - JDBC URL: `jdbc:h2:mem:findit_db`
  - Username: `sa` | Password: *(blank)*

### 2. Launch Frontend (Port 4200)
```powershell
npm install
npm start
```
- Open **`http://localhost:4200`** in any web browser.

---

## 🧪 Testing & Verification

| Test Suite | Scope | Tests Run | Result |
|---|---|---|:---:|
| `ItemModuleTest.java` | Item CRUD, status updates, search filters, and delete operations | 6 | ✅ PASS |
| `MemberCollaborationTest.java` | Multi-user collaboration, user registration, lost/found linking, ownership | 1 | ✅ PASS |
| `FinditApplicationTests.java` | Spring context loading & configuration | 1 | ✅ PASS |
| `Angular Production Build` | Typescript type checking, bundle size budgets, zoneless templates | - | ✅ PASS |

To execute all automated backend tests:
```powershell
cd backend
.\mvnw.cmd test
```

---

## 🔗 Quick Links
- 📘 **Detailed Academic Report**: [PROJECT_SUBMISSION_REPORT.md](PROJECT_SUBMISSION_REPORT.md)
- 🖥️ **Interactive API Tester Tool**: [findit-tester.html](findit-tester.html)
- 📦 **GitHub Repository**: [https://github.com/adithyamjogi1/findit](https://github.com/adithyamjogi1/findit)
