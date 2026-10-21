<<<<<<< HEAD
# FindIt — Campus Lost & Found Portal
> **A Unified Web Application for Campus Lost & Found Management**  
> Built as a collaborative project by a **Team of 4 Members** following the *Student Project Submission Guidelines*.

---

## 👥 Team Collaboration & Member Modules (Team of 4)

| Member | Name / Role | Assigned Module | Implementation Status | Key Deliverables |
|---|---|---|---|---|
| **Member 1** | Adithya | **User Authentication & Profiles** | ✅ Completed | User Registration, BCrypt Login, User JPA Entity, Base Sign-in UI |
| **Member 2** | Preetham SJ | **Lost & Found Management & Core Operations** | ✅ Completed | Item JPA Entity, Lost/Found Reporting, Feed with Search & Category Filters, Online Photo Upload (Canvas Base64), Edit/Delete Modal, Post Ownership Security, Password Reset |
| **Member 3** | Next Contributor | **Claim & Verification System** | 🚀 Ready to Implement | Item claim requests, proof verification, claim approval/rejection |
| **Member 4** | Next Contributor | **Admin Dashboard & Notifications** | 🚀 Ready to Implement | Moderation portal, email/in-app notifications, campus analytics |

---

## 📌 Project Overview & Problem Statement

### The Problem
On university and college campuses, hundreds of personal items (student IDs, laptops, keys, headphones, bags) are misplaced every week. Traditional physical lost-and-found desks and scattered WhatsApp/Telegram groups lead to:
- High loss rates and delayed reuniting of belongings.
- No central searchable repository.
- Privacy risks and lack of item verification.

### The Solution: FindIt
**FindIt** provides a centralized, secure, and interactive campus web portal:
- **Instant Reporting**: Report lost or found items in seconds with title, category, location, and photos.
- **Online Database Photo Storage**: Direct database storage with client-side image compression—no local files saved on your computer disk.
- **Search & Category Filters**: Filter by status (Lost / Found), category (Electronics, Bags, Keys, etc.), or keyword search.
- **Post Ownership & Security**: Only the author who uploaded an item can edit or delete it; classmates can browse and mark items as returned.
- **Self-Service Password Recovery**: Secure password reset for forgotten credentials.

---

## 🏗️ Repository Architecture (Unified Monorepo)

```
findit-frontend/
├── backend/                           # Spring Boot 4 / Java 21 REST API
│   ├── src/
│   │   ├── main/java/com/findit/
│   │   │   ├── User.java              # User JPA Entity (table: users)
│   │   │   ├── UserRepository.java    # Spring Data JPA Repository for users
│   │   │   ├── UserService.java       # User business logic & BCrypt hashing
│   │   │   ├── UserController.java    # Auth REST Endpoints (/register, /login, /reset-password)
│   │   │   ├── entity/Item.java       # Item JPA Entity (table: items, user foreign key)
│   │   │   ├── repository/ItemRepository.java # Filter & search queries
│   │   │   ├── service/ItemService.java       # Item CRUD & business rules
│   │   │   └── controller/ItemController.java # Item REST Endpoints (/lost, /found, /status, etc.)
│   │   └── main/resources/
│   │       ├── application.properties         # MySQL production database configuration
│   │       └── application-h2.properties      # Zero-config local H2 in-memory profile
│   ├── pom.xml                        # Maven dependencies & build configuration
│   ├── mvnw / mvnw.cmd                # Maven Wrapper scripts (no Maven install needed)
│   └── src/test/                      # Integration & unit test suites (ItemModuleTest, MemberCollaborationTest)
│
├── src/                               # Angular 22 Frontend Application
│   ├── app/
│   │   ├── services/
│   │   │   ├── user.ts                # UserService (Auth API calls)
│   │   │   └── item.ts                # ItemService (Item CRUD API calls)
│   │   ├── app.ts                     # Main Component Controller (State, validation, handlers)
│   │   ├── app.html                   # Component Template (Login, Feed, Report forms, Edit modal)
│   │   ├── app.css                    # Responsive styling system & modal animations
│   │   └── app.config.ts              # Angular app configuration (Zoneless change detection)
│   ├── index.html                     # HTML root
│   └── main.ts                        # Application bootstrap
│
├── findit-tester.html                 # Interactive browser-based API testing dashboard
├── angular.json                       # Angular CLI configuration
├── package.json                       # Node dependencies & npm scripts
├── PROJECT_SUBMISSION_REPORT.md       # Complete lab submission report for evaluator
└── README.md                          # Project documentation and developer walkthrough
=======
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
>>>>>>> 8c2f93f8b6ef4135cacfdebdd8e41a51f43f21b9
```

---

<<<<<<< HEAD
## ⚡ Quick Start: Running the Project

### Prerequisites
- **Java 17 or 21** (check with `java -version`)
- **Node.js v18+ or v20+** (check with `node -v`)

---

### Step 1: Start the Backend (Port 8081)

Open a terminal and run:
=======
## 🚀 Quick Start Guide

### Prerequisites
- **Java 17 or 21** (`java -version`)
- **Node.js v18+ or v20+** (`node -v`)

### 1. Launch Backend (Port 8081)
>>>>>>> 8c2f93f8b6ef4135cacfdebdd8e41a51f43f21b9
```powershell
cd backend
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=h2"
```
*(On Linux/macOS: `./mvnw spring-boot:run -Dspring-boot.run.profiles=h2`)*

<<<<<<< HEAD
- The backend will start on **`http://localhost:8081`**.
- H2 in-memory database automatically creates tables and sample data.
- H2 Web Console is available at **`http://localhost:8081/h2-console`** (JDBC URL: `jdbc:h2:mem:findit_db`).

---

### Step 2: Start the Frontend (Port 4200)

Open a second terminal and run:
=======
- **API URL**: `http://localhost:8081/api/items`
- **H2 Database Console**: `http://localhost:8081/h2-console`
  - JDBC URL: `jdbc:h2:mem:findit_db`
  - Username: `sa` | Password: *(blank)*

### 2. Launch Frontend (Port 4200)
>>>>>>> 8c2f93f8b6ef4135cacfdebdd8e41a51f43f21b9
```powershell
npm install
npm start
```
<<<<<<< HEAD
*(Or `npx ng serve --port 4200 --host 0.0.0.0`)*

- The application will be live at: **`http://localhost:4200`**
- **Mobile / Friends on Wi-Fi**: Accessible across your local network at `http://<your-local-ip>:4200` (e.g. `http://192.168.31.34:4200`).

---

## 📊 Database Entities & Data Model

### Entity 1: `User` (`users` table)
| Column | Type | Constraints | Description |
|---|---|---|---|
| `id` | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Unique user identifier |
| `name` | VARCHAR(255) | NOT NULL | Full name of student/faculty |
| `email` | VARCHAR(255) | NOT NULL, UNIQUE | College email address |
| `password` | VARCHAR(255) | NOT NULL | BCrypt one-way encrypted password hash |

### Entity 2: `Item` (`items` table)
| Column | Type | Constraints | Description |
|---|---|---|---|
| `item_id` | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Unique item identifier |
| `item_name` | VARCHAR(255) | NOT NULL | Title of the item (e.g., "Dell XPS 13") |
| `category` | VARCHAR(255) | NOT NULL | Electronics, Bags, Keys, Documents, Clothing, Books, Other |
| `description` | VARCHAR(1000) | NULLABLE | Detailed marks, color, brand, condition |
| `location` | VARCHAR(255) | NOT NULL | Location where lost or found |
| `type` | VARCHAR(255) | NOT NULL | `LOST` or `FOUND` |
| `status` | VARCHAR(255) | NOT NULL | `OPEN`, `CLAIMED`, `RETURNED`, `CLOSED` |
| `image_url` | LONGTEXT | NULLABLE | Base64-encoded image data or web URL |
| `user_id` | BIGINT | FOREIGN KEY (`users.id`) | Student who posted the report |
| `created_at` | TIMESTAMP | AUTO-GENERATED | Date & time report was filed |

**Relationship**: One User has Many Items (`User` 1 ──< `Item`).

---

## 🔌 API Endpoints Reference

### 🔐 Authentication (Member 1 & Member 2)
| Method | Endpoint | Description | Status |
|---|---|---|---|
| `POST` | `/api/users/register` | Register new user (`name`, `email`, `password`) | 201 Created |
| `POST` | `/api/users/login` | Authenticate user & return user metadata | 200 OK / 401 Unauthorized |
| `POST` | `/api/users/reset-password` | Reset password (`email`, `newPassword`) | 200 OK / 404 Not Found |

### 📦 Lost & Found Management (Member 2)
| Method | Endpoint | Description | Status |
|---|---|---|---|
| `GET` | `/api/items` | List items with filters (`type`, `category`, `status`, `search`) | 200 OK |
| `GET` | `/api/items/{id}` | Get detailed information for an item | 200 OK / 404 |
| `POST` | `/api/items/lost` | Report a lost item with user association | 201 Created |
| `POST` | `/api/items/found` | Report a found item with user association | 201 Created |
| `PUT` | `/api/items/{id}?userId={uid}` | Update item (Owner only) | 200 OK / 403 Forbidden |
| `DELETE` | `/api/items/{id}?userId={uid}` | Delete item (Owner only) | 204 No Content / 403 |
| `PATCH` | `/api/items/{id}/status` | Update item status (e.g., `RETURNED`) | 200 OK |

---

## 🔒 Security & Post Ownership Architecture

1. **Owner-Only Controls**:
   - When a student views the feed, only items created by their account show the **"✏️ Edit"** and **"🗑️ Delete"** buttons along with a **`⭐ My Post`** badge.
   - For all items posted by classmates, the Edit/Delete buttons are hidden.
2. **Backend Enforcement**:
   - `PUT /api/items/{id}` and `DELETE /api/items/{id}` verify that `item.user.id == userId`. If an unauthorized user attempts an update, the backend rejects it with `403 Forbidden: Permission denied. You can only edit/delete items you reported.`
3. **Password Security**:
   - Passwords are encrypted using BCrypt (`BCryptPasswordEncoder`) with salting before being stored in the database.

---

## 🧪 Automated Testing & Results

| Test Suite | Scope | Tests Run | Result |
|---|---|---|---|
| `ItemModuleTest.java` | Item CRUD, status updates, search filters, and delete operations | 6 | ✅ PASS (0 Failures) |
| `MemberCollaborationTest.java` | Multi-user collaboration, user registration, lost/found linking, ownership | 1 | ✅ PASS (0 Failures) |
| `FinditApplicationTests.java` | Spring context loading & configuration | 1 | ✅ PASS (0 Failures) |
| `Angular Build` | Typescript type checking, bundle size budgets, zoneless templates | - | ✅ PASS (0 Errors) |

Run all backend tests:
=======
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
>>>>>>> 8c2f93f8b6ef4135cacfdebdd8e41a51f43f21b9
```powershell
cd backend
.\mvnw.cmd test
```

---

<<<<<<< HEAD
## 🚀 Handoff Guide: Next Steps for Member 3 & Member 4

### Instructions for Member 3 (Claim & Verification Module)
1. **Pull the repository**:
   ```bash
   git clone <repo-url>
   git checkout main
   git checkout -b feature/claim-verification
   ```
2. **Backend Extensions**:
   - Create `entity/Claim.java` with fields: `id`, `itemId`, `claimantUserId`, `proofDescription`, `status` (`PENDING`, `APPROVED`, `REJECTED`), `createdAt`.
   - Add `ClaimRepository.java`, `ClaimService.java`, and `ClaimController.java` (`POST /api/claims`, `GET /api/claims/item/{itemId}`, `PATCH /api/claims/{id}/status`).
3. **Frontend Extensions**:
   - Add a **"🙋 This is Mine (Claim)"** button on item cards where `!isMyItem(item)` and `item.type === 'FOUND'`.
   - Add a modal for the claimant to provide proof/identifying marks.
   - Add a "Claims Received" tab on the owner's dashboard to Approve or Reject claims.

### Instructions for Member 4 (Admin Dashboard & Notifications)
1. **Pull the repository**:
   ```bash
   git checkout main
   git checkout -b feature/admin-notifications
   ```
2. **Backend Extensions**:
   - Add role field to `User` entity (`ROLE_STUDENT`, `ROLE_ADMIN`).
   - Create `entity/Notification.java` to notify users when someone claims or returns their item.
   - Add admin endpoints (`GET /api/admin/stats`, `DELETE /api/admin/items/{id}`).
3. **Frontend Extensions**:
   - Add a notification bell icon with unread badge count in the topbar.
   - Build an Admin panel route or toggle to moderate flagged items and view campus lost/found analytics.

---

## 📄 Project Submission Documentation

For the formal college project submission matching the **Student Project Submission Guidelines**, see:
👉 [PROJECT_SUBMISSION_REPORT.md](file:///d:/zproh/findit-frontend/PROJECT_SUBMISSION_REPORT.md)
Contains the problem statement, frontend/backend architecture, CRUD operations, ER diagram, screenshot evidence guide, and testing results.
=======
## 🔗 Quick Links
- 📘 **Detailed Academic Report**: [PROJECT_SUBMISSION_REPORT.md](PROJECT_SUBMISSION_REPORT.md)
- 🖥️ **Interactive API Tester Tool**: [findit-tester.html](findit-tester.html)
- 📦 **GitHub Repository**: [https://github.com/adithyamjogi1/findit](https://github.com/adithyamjogi1/findit)
>>>>>>> 8c2f93f8b6ef4135cacfdebdd8e41a51f43f21b9
