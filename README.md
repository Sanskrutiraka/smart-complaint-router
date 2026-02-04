# Smart Complaint Router 🛠️

Smart Complaint Router is a web-based application that allows users to submit complaints, track their status, and enables admins to manage and resolve complaints efficiently.

This project is built using **Spring Boot** for the backend and **HTML, Bootstrap, and JavaScript** for the frontend.

---

## 🚀 Features

### User
- User registration & login
- Submit complaints with description and optional image URL
- View all submitted complaints and their status

### Admin
- View all user complaints
- Update complaint status (pending / resolved)
- Simple admin dashboard

---

## 🧰 Tech Stack

**Backend**
- Java
- Spring Boot
- Spring Data JPA
- MySQL
- REST APIs

**Frontend**
- HTML
- Bootstrap 5
- JavaScript

---

## 📂 Project Structure

SmartComplaintRouter
│
├── src/main/java
│ └── com.example.SmartComplaintRouter
│ ├── controller
│ ├── service
│ ├── repository
│ └── model
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


---

## ▶️ How to Run the Project Locally

### Prerequisites
- Java 17+
- Maven
- MySQL
- Git

---

### 1️. Clone the Repository
```bash
git clone https://github.com/your-username/smart-complaint-router.git
cd smart-complaint-router

---

### 2. Configure Database

# Create a MySQL database:

CREATE DATABASE smart_complaint_router;

# Update application.properties :

spring.datasource.url=jdbc:mysql://localhost:3306/smart_complaint_router
spring.datasource.username=your_username
spring.datasource.password=your_password

### 3. Run Backend (Spring Boot)

mvc spring-boot:run

# Backend will start at - 

http://localhost:8086

### 4. Run Frontend 

# Open these files in browser:

login.html

register.html

--- 

## Future Enhancements

JWT authentication

Password encryption

Email notifications

Cloud deployment (AWS / Render / Railway)

---


## 👤 Author

Sanskruti Raka
Backend Developer | Java | Spring Boot


---

## STEP 1D: Commit README to GitHub

Now save the file and run these commands in terminal (project root):

```bash
git status
git add README.md
git commit -m "Add project README"
git push