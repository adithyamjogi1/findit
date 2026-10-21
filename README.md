# FindIt — Campus Lost & Found Portal
> **A Unified Full-Stack Web Application for Campus Lost & Found Management**  
> *Developed for the Full Stack Web Development Lab by a Team of 4 Members following the Student Project Submission Guidelines.*

[![Repository](https://img.shields.io/badge/GitHub-Repository-181717?logo=github)](https://github.com/adithyamjogi1/findit)
[![Spring Boot](https://img.shields.io/badge/Backend-Spring%20Boot%204.1.1-6DB33F?logo=springboot)](https://spring.io/projects/spring-boot)
[![Angular](https://img.shields.io/badge/Frontend-Angular%2022%20SPA-DD0031?logo=angular)](https://angular.dev/)
[![Database](https://img.shields.io/badge/Database-H2%20%2F%20MySQL-003B57?logo=mysql)](http://localhost:8081/h2-console)
[![Build Status](https://img.shields.io/badge/Build-Passing%20(8%20Tests)-brightgreen)](#-automated-testing--results)

---

## 📋 Evaluator / Invigilator Quick Review

> [!IMPORTANT]
> **For Final Lab Assessment & Invigilation:**  
> A formal submission report containing the detailed problem statement, entities, ER diagram, architecture, all 11 step-by-step evidence screenshots, and testing pass results is available in:  
> 👉 **[PROJECT_SUBMISSION_REPORT.md](PROJECT_SUBMISSION_REPORT.md)**

### Quick Verification Links
- **Step-by-Step Evidence**: [8 Screenshot Verification Evidence](PROJECT_SUBMISSION_REPORT.md#7-screenshots-step-by-step-evidence)
- **Live Database Console**: Run backend and open `http://localhost:8081/h2-console` (`jdbc:h2:mem:findit_db`)
- **Automated Test Suite**: 8/8 Tests Passing (`mvn test`)
- **Interactive API Tester**: Direct browser tool at [`findit-tester.html`](findit-tester.html)

---

## 👥 Team Collaboration & Member Modules (Team of 4)

| Member | Student Name | Student ID / Reg No. | Assigned Module | Implementation Status | Key Deliverables |
|---|---|---|---|---|---|
| **Member 1** | **Adithya M Jogi** | `1BF25AI010` | **User Authentication & Profiles** | ✅ Completed | User Registration, BCrypt Login, User JPA Entity, Base Sign-in UI |
| **Member 2** | **Preetham SJ** | `1BM26AI441-T` | **Lost & Found Management & Core Operations** | ✅ Completed | Item JPA Entity, Lost/Found Reporting, Feed with Search & Category Filters, Online Photo Upload (Canvas Base64), Edit/Delete Modal, Post Ownership Security, Password Reset, H2 Console Setup |
| **Member 3** | **S Yogananda Reddy** | `1BM26AI403-T` | **Claim & Verification System** | 🚀 In Progress | Item claim requests, proof verification, claim approval/rejection endpoints & UI |
| **Member 4** | **Kaushal BM** | `1BF25AI061` | **Admin Dashboard & Notifications** | 🚀 In Progress | Moderation portal, email/in-app notifications, campus analytics |

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
- **Online Database Photo Storage**: Direct database storage with client-side image compression—no local files saved on the host computer disk.
- **Search & Category Filters**: Filter by status (`LOST` / `FOUND`), category (`Electronics`, `Bags`, `Keys`, etc.), or keyword search.
- **Post Ownership & Security**: Only the author who uploaded an item can edit or delete it; classmates can browse and mark items as returned.
- **Self-Service Password Recovery**: Secure password reset for forgotten credentials.

---

## 🖼️ Application Preview

| Main Feed & Search | Report Lost Item Form |
|:---:|:---:|
| ![Feed View](docs/screenshots/image4.png) | ![Report Lost](docs/screenshots/image3.png) |

| Edit Item Modal (Owner Only) | Database Console (H2) |
|:---:|:---:|
| ![Edit Modal](docs/screenshots/image6.png) | ![Database](docs/screenshots/image11.png) |

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
│   │   │   ├── FinditApplication.java # Spring Boot entry point & H2 console bean
│   │   │   ├── entity/Item.java       # Item JPA Entity (table: items, user foreign key)
│   │   │   ├── repository/ItemRepository.java # Filter & search queries
│   │   │   ├── service/ItemService.java       # Item CRUD & business rules
│   │   │   └── controller/ItemController.java # Item REST Endpoints (/lost, /found, /status, etc.)
│   │   └── main/resources/
│   │       ├── application.properties         # MySQL production database configuration
│   │       └── application-h2.properties      # Zero-config local H2 in-memory profile
│   ├── pom.xml                        # Maven dependencies & build configuration
│   ├── mvnw / mvnw.cmd                # Maven Wrapper scripts (no Maven install needed)
│   └── src/test/                      # Integration & unit test suites (8 passing tests)
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
├── docs/
│   └── screenshots/                   # All 11 lab evaluation evidence screenshots
├── findit-tester.html                 # Interactive browser-based API testing dashboard
├── angular.json                       # Angular CLI configuration
├── package.json                       # Node dependencies & npm scripts
├── PROJECT_SUBMISSION_REPORT.md       # Complete lab submission report for evaluator
└── README.md                          # Project documentation and developer walkthrough
```

---

## ⚡ Quick Start: Running the Project

### Prerequisites
- **Java 17 or 21** (check with `java -version`)
- **Node.js v18+ or v20+** (check with `node -v`)

---

### Step 1: Start the Backend (Port 8081)

Open a terminal and run:
```powershell
cd backend
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=h2"
```
*(On Linux/macOS: `./mvnw spring-boot:run -Dspring-boot.run.profiles=h2`)*

- The backend will start on **`http://localhost:8081`**.
- H2 in-memory database automatically creates tables and sample data.
- **H2 Web Console**: Access at **`http://localhost:8081/h2-console`**
  - JDBC URL: `jdbc:h2:mem:findit_db`
  - Username: `sa`
  - Password: *(leave empty)*

---

### Step 2: Start the Frontend (Port 4200)

Open a second terminal and run:
```powershell
npm install
npm start
```
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
```powershell
cd backend
.\mvnw.cmd test
```

---

## 🚀 Handoff Guide: Next Steps for Member 3 & Member 4

### Instructions for Member 3 (Claim & Verification Module)
1. **Pull the repository**:
   ```bash
   git clone https://github.com/adithyamjogi1/findit.git
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
   git clone https://github.com/adithyamjogi1/findit.git
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

## 📄 Formal Lab Submission Report

For the complete, formal laboratory assessment document prepared according to the **Student Project Submission Guidelines**, please refer to:  
👉 **[PROJECT_SUBMISSION_REPORT.md](PROJECT_SUBMISSION_REPORT.md)**
