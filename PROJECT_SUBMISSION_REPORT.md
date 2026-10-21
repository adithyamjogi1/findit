# STUDENT PROJECT SUBMISSION REPORT
**Lab-Completed Web Application Project**  
*Document prepared in accordance with Student Project Submission Guidelines*

---

## 1. Student and Project Details

| Field | Details |
|---|---|
| **Project Title** | **FindIt — Campus Lost & Found Web Portal** |
| **Team Size** | **Team of 4 Members** |
| **Course / Lab / Batch** | Full Stack Web Development Lab |
| **Submission Date** | October 2026 |

### Team Members & Module Breakdown
| Member # | Student Name | Student ID / Reg No. | Module Assigned | Contribution Summary |
|---|---|---|---|---|
| **Member 1** | Adithya | [Enter ID] | User Authentication & Account Management | User registration, login with BCrypt encryption, User entity and repository, initial login UI layout |
| **Member 2** | Preetham SJ | [Enter ID] | Lost & Found Management & Core Operations | Item entity with LONGTEXT photo storage, Lost & Found reporting forms, feed list with search & category filters, edit/delete modal, post ownership security, password recovery |
| **Member 3** | [Member 3 Name] | [Enter ID] | Item Claims & Verification System | Item claim workflow, proof verification, claim approval/rejection endpoints & UI *(In Progress)* |
| **Member 4** | [Member 4 Name] | [Enter ID] | Admin Moderation & Notifications | Notification center, campus analytics, admin moderation dashboard *(In Progress)* |

---

## 2. Problem Statement and Proposed Solution

### Problem Statement
On modern educational campuses, thousands of students, faculty, and visitors frequently misplace valuable belongings such as student ID cards, laptops, keys, headphones, textbooks, and backpacks. Existing solutions rely on informal bulletin boards, scattered WhatsApp/Telegram group chats, or physical security desks. These methods suffer from:
1. **Lack of centralized tracking**: No single place exists to search for missing items.
2. **Slow reuniting process**: Posts get buried quickly in chat threads.
3. **No verification or security**: Unauthorized individuals can tamper with or claim items.
4. **Poor record-keeping**: No historical status of returned vs. unclaimed items.

### Proposed Solution
**FindIt** is a full-stack, responsive web application designed specifically for the campus ecosystem:
- **Instant Digital Reporting**: Students can file Lost or Found reports with detailed titles, descriptions, categories, locations, and photos.
- **Online Database Image Storage**: Uploaded photos are compressed client-side and saved directly into the database as Base64 Data URLs, eliminating local hard drive dependency.
- **Dynamic Search & Filtering**: Real-time filtering by item type (`LOST` / `FOUND`), category, and keyword search.
- **Strict Post Ownership Security**: Only the student who posted an item can edit or delete it; other students can view item details or mark items as returned.
- **Account Self-Recovery**: Instant password reset capability for forgotten credentials.

---

## 3. Webpage Design and Frontend Implementation

### Technologies Used
| Technology | Role in Project |
|---|---|
| **HTML5** | Defines the semantic page structure (topbar, navigation tabs, cards, report forms, modal dialogs). |
| **Vanilla CSS3** | Controls custom visual styling: responsive 2-column dashboard layout, custom color tokens (`#176b4c` forest green theme), badges, glassmorphism modal overlays, and smooth animations. |
| **TypeScript / JavaScript** | Drives client-side logic, event handling, real-time client-side image downscaling via HTML5 Canvas, and form validation. |
| **Angular 22** | Single Page Application (SPA) architecture using Standalone Components, reactive data binding (`[(ngModel)]`), and Zoneless Change Detection (`ChangeDetectorRef`) for instantaneous UI updates without lag. |
| **Spring Boot 4.1.1** | Implements the backend REST API, validation logic, business rules, and connects to the relational data layer. |

### Key Pages and Components
1. **Authentication Card (Sign In / Register / Reset Password)**:
   - Toggleable view between Sign In, New Account Registration, and Password Reset.
   - Client-side validation for required fields and valid email format.
   - Dynamic error/success feedback messages.
2. **Campus Lost & Found Feed**:
   - Header with active count of reported items and navigation tabs: `All Items`, `⚠️ Report Lost`, and `✅ Report Found`.
   - Real-time search bar and category filter pill buttons (`Electronics`, `Bags`, `Documents`, `Keys`, `Clothing`).
   - Responsive card grid displaying photos, status badges, categories, locations, and reporter names.
3. **Report Lost / Report Found Forms**:
   - Structured inputs for Item Name, Category, Location, Description.
   - Integrated Photo Picker supporting local image upload (auto-compressed) or direct image URL.
4. **Edit Item Modal**:
   - Pop-up modal overlay pre-populated with existing item details for in-place editing of name, location, category, photo, and description.

---

## 4. Entities, Tables, and CRUD Operations

### Entity 1: User (`users` table)
- **Fields**: `id` (BIGINT, PK), `name` (VARCHAR), `email` (VARCHAR, UNIQUE), `password` (VARCHAR, BCrypt Encrypted).
- **CRUD Operations**:
  - **Create**: User registration via `POST /api/users/register`.
  - **Read**: User login authentication via `POST /api/users/login`.
  - **Update**: Password reset via `POST /api/users/reset-password`.
  - **Delete**: Handled via administrative deletion.

### Entity 2: Item (`items` table)
- **Fields**: `item_id` (BIGINT, PK), `item_name` (VARCHAR), `category` (VARCHAR), `location` (VARCHAR), `description` (VARCHAR), `type` (VARCHAR: LOST/FOUND), `status` (VARCHAR: OPEN/RETURNED), `image_url` (LONGTEXT), `user_id` (BIGINT, FK referencing `users.id`), `created_at` (TIMESTAMP).
- **CRUD Operations**:
  - **Create**: Report lost item (`POST /api/items/lost`) or found item (`POST /api/items/found`).
  - **Read**: List & filter items (`GET /api/items?type=LOST&category=Electronics&search=laptop`) or single item (`GET /api/items/{id}`).
  - **Update**: Edit item details (`PUT /api/items/{id}?userId={uid}`) and status update (`PATCH /api/items/{id}/status`).
  - **Delete**: Remove item (`DELETE /api/items/{id}?userId={uid}`).

### Entity-Relationship (ER) Diagram
```
┌─────────────────────────┐             ┌───────────────────────────────┐
│          USERS          │             │             ITEMS             │
├─────────────────────────┤             ├───────────────────────────────┤
│ PK  id        (BIGINT)  │1           *│ PK  item_id     (BIGINT)      │
│     email     (VARCHAR) ├────────────┤ FK  user_id     (BIGINT)      │
│     name      (VARCHAR) │             │     item_name   (VARCHAR)     │
│     password  (VARCHAR) │             │     category    (VARCHAR)     │
└─────────────────────────┘             │     location    (VARCHAR)     │
                                        │     description (VARCHAR)     │
                                        │     type        (VARCHAR)     │
                                        │     status      (VARCHAR)     │
                                        │     image_url   (LONGTEXT)    │
                                        │     created_at  (TIMESTAMP)   │
                                        └───────────────────────────────┘
```

---

## 5. Frontend, Backend, Database, and Connectivity

### Data Flow Architecture
1. **Frontend Layer**: Angular 22 Single Page Application rendered in the browser.
2. **Connectivity / API Layer**: Angular `HttpClient` makes asynchronous HTTP REST calls with JSON payloads. Cross-Origin Resource Sharing (CORS) is enabled with `@CrossOrigin(origins = "*")`.
3. **Backend Layer**: Spring Boot 4.1.1 controllers (`UserController`, `ItemController`) receive requests, validate inputs, enforce post ownership, and delegate to service classes (`UserService`, `ItemService`).
4. **Data Persistence Layer**: Spring Data JPA with Hibernate ORM maps Java entity classes directly to database tables. Configured with H2 in-memory relational database for development and MySQL compatibility for production.

---

## 6. Architecture Note: Real Spring Boot & Relational Database
Unlike prototype applications that use dummy JSON servers (`db.json`), **FindIt is built entirely on production-grade architecture**:
- **Real Backend**: Spring Boot 4.1.1 with dependency injection, service layers, and transaction management (`@Transactional`).
- **Real Relational Database**: Full SQL schema with foreign key constraints, indexes, and JPA repositories (`ItemRepository`, `UserRepository`).
- **Real Security**: BCrypt password hashing and server-side authorization checks on write/delete operations.

---

## 7. Screenshots: Step-by-Step Evidence

> *Capture the following 8 screenshots from the running application for your final report submission:*

- **Screenshot 1 — Project Setup & Folder Structure**:  
  Show the unified monorepo directory layout showing `backend/src/main/java`, `src/app/`, and `pom.xml`.
- **Screenshot 2 — Main Home Page / Login Card**:  
  Show the landing page displaying the welcome section and the FindIt sign-in card.
- **Screenshot 3 — Add / Create Operation (Report Lost/Found)**:  
  Show the "Report a Lost Item" form filled with details and an uploaded photo preview.
- **Screenshot 4 — Read / List Operation (Feed View)**:  
  Show the Lost & Found items feed displaying items with photos, categories, and locations.
- **Screenshot 5 — Update Operation (Edit Modal & Password Reset)**:  
  Show the "Edit Item Details" modal open with modified title/location or the Password Reset screen.
- **Screenshot 6 — Delete Operation**:  
  Show the item deletion action and the card being removed from the feed.
- **Screenshot 7 — API & Backend Logs Evidence**:  
  Show the Spring Boot console output displaying `Tomcat started on port 8081` and Hibernate SQL execution.
- **Screenshot 8 — Database Evidence**:  
  Show the H2 Web Console (`http://localhost:8081/h2-console`) displaying records in the `ITEMS` and `USERS` tables.

---

## 8. Source Code & Repository Structure

- **Repository**: Single Unified Monorepo containing both Frontend and Backend.
- **Prerequisites**: Java 21, Node.js v18+.
- **Run Backend**: `cd backend && .\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=h2"`
- **Run Frontend**: `npm install && npm start`

---

## 9. Testing and Results Table

| Test Case | Steps / Input | Expected Result | Actual Result |
|---|---|---|---|
| **User Registration** | Enter valid name, college email, password | New account created in database, auto login | ✅ PASS |
| **User Login** | Enter registered email & password | Authenticates credentials, returns user ID & name | ✅ PASS |
| **Password Reset** | Enter email & new password on Forgot Password screen | Password re-hashed and updated in database | ✅ PASS |
| **Report Lost Item** | Enter title, category, location, upload photo | Item saved with user foreign key and visible in feed | ✅ PASS |
| **Filter by Category** | Click "Electronics" or "Bags" filter pill | Only items matching selected category are shown | ✅ PASS |
| **Edit Item (Owner)** | Author clicks "Edit", modifies title/location | Item updated in database and reflects in UI | ✅ PASS |
| **Edit Item (Non-Owner)**| Classmate logs in and views author's item | "Edit" and "Delete" buttons are hidden; direct API yields 403 | ✅ PASS |
| **Mark as Returned** | Click "✓ Mark as Returned" on open item | Status updates to `RETURNED` and badge updates | ✅ PASS |
| **Delete Item** | Author clicks "Delete" and confirms prompt | Item removed from database and feed grid | ✅ PASS |

---

## 10. Conclusion & Future Roadmap

FindIt demonstrates a complete, secure, full-stack campus Lost & Found management platform. **Member 1** and **Member 2** have delivered a fully working core application. **Member 3** and **Member 4** will extend this foundation with:
1. **Formal Claims Engine**: Multi-step claim verification for found items.
2. **Notification Subsystem**: Real-time email and in-app alerts when a matching item is posted.
3. **Administrative Dashboard**: Staff oversight for item verification and campus recovery metrics.
