# Personal Expense Tracker (Console Application)

A console-based Personal Expense Tracker application built using **Java and JDBC**.  
This project allows multiple users to manage their expenses with category-wise, monthly, and yearly summaries.

---

## 🚀 Features

- User Registration & Login
- Add, Update, Delete Expenses
- View All Expenses
- View Expenses by Category
- Monthly Expense Summary
- Yearly Expense Summary
- Multi-user support (expenses are user-specific)

---

## 🛠 Technologies Used

- Java (Core Java)
- JDBC
- MySQL
- DAO Design Pattern
- Service Layer Architecture
- Console-based UI

---

## 📂 Project Structure
```
personalexpensetracker
│
├── model → POJO classes (User, Expense, Category)
├── dao → DAO interfaces & implementations
├── service → Business logic & validations
├── util → Database connection
└── Main.java → Console-based user interaction
```
---

## 🗄 Database Design

### Tables:
- users
- categories
- expenses

Each expense is linked to:
- A user
- A category

---

## 🔐 Architecture Overview

- **Model** → Represents database entities
- **DAO** → Handles database operations
- **Service** → Contains validations & business logic
- **Main** → Acts as controller for console input/output

---

## 📌 Note

This is a **console-based version** of the project.  
The project will be upgraded to a **Web application** in the next phase.

---

## 👨‍💻 Author

**Dhanush R**  
Aspiring Full Stack Developer
