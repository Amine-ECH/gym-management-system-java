# Gym Management System – Java Swing & JDBC

## 📌 Project Overview
This project is a **desktop gym management application** developed using **Java Swing**
for the graphical user interface and **JDBC** for database access.

The application allows the gym administrator to manage clients efficiently by performing
CRUD operations (Create, Read, Update, Delete) on a relational database.

This project was developed as part of an **academic mini-project** for the BTS DAI program.

---

## 🛠 Technologies Used
- Java (JDK 8 or higher)
- Swing (Desktop GUI)
- JDBC
- MySQL
- Git & GitHub

---

## 📂 Project Structure
src/
├── ui/ # Swing interfaces (JFrame, JPanel, forms)
├── dao/ # Data Access Objects (JDBC)
├── model/ # Business entities
└── util/ # Database connection & utilities

database/
└── database.sql


---

## ⚙️ Installation & Setup

### 1️⃣ Prerequisites
Before running the project, make sure you have:
- Java JDK installed
- MySQL installed and running
- An IDE (Eclipse / IntelliJ IDEA / NetBeans)

---

### 2️⃣ Clone the Repository
```bash
git clone https://github.com/Amine-ECH/gym-management-system-java.git

3️⃣ Database Configuration
Create a new MySQL database (example: gym_db)
Import the SQL script located in:
database/database.sql
Open the database connection class:
src/util/DBConnection.java
Update the following fields:
Database URL
Username
Password

4️⃣ Run the Application
Open the project in your IDE
Add the MySQL JDBC driver to the project
Run the main class from the ui package

## 👤 Author
- **Amine ECH CHIGUER**