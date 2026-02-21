# Smart Complaint Router 🛠️

Smart Complaint Router is a backend-focused web application designed to streamline complaint management for organizations, institutions, and government systems.

The system allows users to submit complaints, automatically routes them based on complaint category, and enables administrators to monitor and resolve issues through a centralized dashboard.

Built with a scalable architecture, the project is designed to be **deployable today** and **expandable into an enterprise-level complaint management platform**.

---

## 🚀 Key Features

### 👤 User Module
- User registration and login
- Secure session-based authentication
- Submit complaints with:
  - Description
  - Optional image URL
  - Complaint subject/category selection
- Automatic complaint routing to departments
- Track complaint status

### 🛠️ Admin Module
- Admin-only dashboard access
- View all submitted complaints
- Filter complaints by status
- Update complaint lifecycle:
  - Pending
  - Approved
  - Rejected
  - Resolved
- Manage complaint categories (Enable/Disable subjects)

### 🤖 Smart Routing (Enterprise Foundation)
- Complaint automatically assigned to department based on category
- Routing logs stored for future AI/ML enhancement
- Designed for organization-specific customization

---

## 🧰 Tech Stack

### Backend
- Java 17
- Spring Boot
- Spring Data JPA (Hibernate)
- REST API Architecture
- MySQL Database

### Frontend
- HTML5
- Bootstrap 5
- Vanilla JavaScript (Fetch API)

### Architecture
- Layered Architecture (Controller → Service → Repository)
- Session-based authentication
- RESTful communication

---

## 📂 Project Structure

```bash
SmartComplaintRouter
│
├── src/main/java/com/example/SmartComplaintRouter
│ ├── controller
│ ├── service
│ ├── repository
│ ├── model
│ ├── dto
│ └── exception
│
├── src/main/resources
│ ├── static
│ │ ├── login.html
│ │ ├── register.html
│ │ ├── complaint.html
│ │ ├── my-complaints.html
│ │ └── admin.html
│ └── application.properties
│
├── pom.xml
└── README.md
```

---

## ⚙️ How to Run Locally

### Prerequisites
- Java 17+
- Maven
- MySQL
- Git

---

### 1️. Clone Repository

```bash
git clone https://github.com/your-username/smart-complaint-router.git
cd smart-complaint-router
```

---

### 2. Configure Database

# Create a MySQL database:

```bash
CREATE DATABASE smart_complaint_router;
```

# Update application.properties :

```bash
spring.datasource.url=jdbc:mysql://localhost:3306/smart_complaint_router
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
server.port=8086
```

### 3. Run Backend (Spring Boot)

```bash
mvc spring-boot:run
```

# Backend will start at - 

```bash
http://localhost:8086
```

### 4. Run Frontend 

# Open these files in browser:

login.html

register.html

(admin dashboard opens automatically for admin login.)

---

### 🔒 Current Security

Session-token based authentication

Role-based access (User/Admin)

Unauthorized access protection

Admin restriction from complaint submission

--- 
### 📸 Screenshots

Login Page

Complaint Submission

Admin Dashboard

Complaint Status Management

---

### 🛠️ Future Enhancements

JWT Authentication

Password Encryption (BCrypt)

Logout & Session Expiry

API Security (Spring Security)

Email Notifications

File Upload Support

AI-based complaint classification

Cloud Deployment (AWS / Render / Railway)

---

### 🌍 Real-World Use Cases

Government grievance portals

University complaint systems

Corporate IT helpdesk

Facility management systems

Customer support workflows

---

### 👤 Author

Sanskruti Raka

Java Backend Developer | Spring Boot | REST APIs

---

### ⭐ Project Status

✅ Stable
✅ Deployable
✅ Designed for future enterprise expansion


