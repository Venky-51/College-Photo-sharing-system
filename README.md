# College Photo Sharing Web Application

## 📌 Project Overview
This is a full-stack web application designed for colleges to manage and share event photos securely.  
The system allows **college management (admin)** to upload and manage photos, while **students** can view event photos and request removal of images they do not wish to be displayed.

The project focuses on **privacy, moderation, and clean role-based access**.

---

## 🎯 Key Features

### 👨‍💼 Admin
- Secure admin login
- Upload event photos
- View photos by event
- View student delete requests
- Approve and delete photos on request

### 👨‍🎓 Student
- View event-wise photo galleries
- Download photos
- Request deletion of specific photos with reason

---

## 🏗️ System Architecture

- **Frontend:** HTML, CSS, JavaScript  
- **Backend:** Spring Boot (REST APIs)  
- **Storage:** Server file system (event-based folders)  
- **Communication:** JSON over HTTP  


---

## 🔁 Application Flow

1. User selects **Student** or **Admin**
2. Admin logs in and manages photos
3. Students view photos without login
4. Students can request deletion of photos
5. Admin reviews and approves delete requests

---

## 🛠️ Technologies Used

- Java
- Spring Boot
- REST APIs
- HTML5
- CSS3
- JavaScript
- Git & GitHub

---

## 📂 Project Structure


---

## 🚀 Running the Project Locally

### Backend
1. Open `photoweb` in IDE
2. Run `PhotowebApplication.java`
3. Server runs on `http://localhost:8080`

### Frontend
1. Open `index.html` using Live Server or browser
2. Interact with Student/Admin flows

---

## 🔐 Note on Deployment & Storage

- Currently runs locally for development and demonstration
- In production:
  - Backend can be deployed to a cloud server
  - Images can be stored using cloud storage (AWS S3, Firebase, etc.)

---

## 🧠 Learning Outcomes

- REST API design
- File upload & download handling
- Role-based access control
- Frontend-backend integration
- Debugging real-world issues
- Privacy-focused system design
