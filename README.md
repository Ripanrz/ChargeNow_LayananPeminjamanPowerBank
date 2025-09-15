# ChargeNow: Layanan Peminjaman Power Bank

## 📌 Overview
ChargeNow is a **simple terminal-based power bank rental system** built using **Java**.  
This project was created to simulate the process of renting and returning power banks, both from the **user side** and the **admin side**.  

The application runs entirely in the terminal/console and allows users to borrow power banks, while admins can manage rentals, returns, and view rental history.  

---

## 🚀 Features

### 🔹 User Menu
- Borrow a power bank by entering:
  - Name
  - Student ID (NIM)
  - Study Program
  - Institution  
- View a list of available power banks (PB001 - PB005).
- Rental time is automatically recorded when borrowing.

### 🔹 Admin Menu
1. **View Active Rentals**  
   Displays the list of current borrowers, including their details and rental time.  

2. **Return Power Bank**  
   - Admin inputs the power bank code.  
   - The system shows rental time and return time.  
   - The system checks whether the return is **on time** or **late**.  
   - If late, a **penalty form** appears.  

3. **Rental History**  
   Shows the full history of rentals and returns, including:  
   - Borrower details  
   - Rental and return time  
   - Penalty status  

---

## 🛠️ Technology Used
- **Java** (Console/Terminal-based application)
- No external libraries required

---

## 🎯 Purpose
This project is designed as a **learning project** for Java beginners.  
It demonstrates:
- Implementation of object-oriented programming (OOP) in a practical case.  
- Basic CRUD-like operations (create rental, update return, display records).  
- How to manage **user input**, **loops**, and **data storage** in arrays/lists.  

---

## 📂 How to Run
1. Clone this repository:
   ```bash
   git clone https://github.com/your-username/ChargeNow-PowerBankRental.git
