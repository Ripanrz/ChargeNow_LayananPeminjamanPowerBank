# ⚡ ChargeNow: Power Bank Rental System

![Java](https://img.shields.io/badge/Language-Java-orange?style=for-the-badge&logo=java)
![Platform](https://img.shields.io/badge/Platform-Console%2FTerminal-black?style=for-the-badge&logo=terminal)
![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Active-blue?style=for-the-badge)

> **ChargeNow** is a robust, terminal-based simulation system designed to manage the lifecycle of power bank rentals. Built with **pure Java**, this project demonstrates solid Object-Oriented Programming (OOP) principles and efficient data management without external dependencies.

---

## 📑 Table of Contents
- [Overview](#-overview)
- [Key Features](#-key-features)
- [Technical Architecture](#-technical-architecture)
- [Installation & Usage](#-installation--usage)
- [Application Flow](#-application-flow)
- [Roadmap](#-roadmap)

---

## 📌 Overview
In high-traffic environments like universities or public hubs, managing equipment loans can be chaotic. **ChargeNow** simplifies this by offering a centralized CLI (Command Line Interface) dashboard.

The application serves two distinct user groups:
1.  **Users (Borrowers):** Can quickly rent devices using their institutional ID.
2.  **Admins:** Have full control over inventory, return processing, penalty calculation, and audit logs.

---

## 🚀 Key Features

### 👤 User Module
* **Fast Borrowing Workflow:** Streamlined input for Name, Student ID (NIM), and Study Program.
* **Real-time Availability:** View currently available units (e.g., PB001 - PB005) instantly.
* **Auto-Timestamp:** Rental start time is automatically captured upon confirmation.

### 🛡️ Admin Module
* **Active Rental Dashboard:** Monitor who is currently holding a device and for how long.
* **Smart Return System:**
    * Calculates duration automatically.
    * **Late Detection logic:** Automatically flags returns that exceed the time limit.
    * **Penalty Enforcement:** Triggers a penalty form if the device is returned late.
* **Comprehensive History:** A persistent (session-based) log of all transactions for auditing purposes.

---

## ⚙️ Technical Architecture

This project is crafted to showcase fundamental software engineering concepts:

| Concept | Implementation in ChargeNow |
| :--- | :--- |
| **OOP Principles** | Utilizes Classes and Objects to model `User`, `PowerBank`, and `Transaction`. |
| **Data Structures** | Implements `ArrayList` for dynamic storage of history and active rentals. |
| **Logic & Control** | Complex `if-else` and `switch-case` structures for menu navigation and penalty logic. |
| **Input Handling** | Robust `Scanner` implementation to handle various data types (String, Integer). |
| **Encapsulation** | Private fields with public getters/setters to ensure data integrity. |

---

## 💻 Installation & Usage

### Prerequisites
* **Java Development Kit (JDK)** 8 or higher installed.
* Git (optional, for cloning).

### Steps
1.  **Clone the Repository**
    ```bash
    git clone [https://github.com/your-username/ChargeNow.git](https://github.com/your-username/ChargeNow.git)
    cd ChargeNow
    ```

2.  **Compile the Source Code**
    ```bash
    javac Main.java
    ```

3.  **Run the Application**
    ```bash
    java Main
    ```

---

## 📖 Application Flow

### Main Dashboard
```text
=========================================
      ⚡ WELCOME TO CHARGENOW ⚡
   Power Bank Rental Service System
=========================================
1. Login as User
2. Login as Admin
3. Exit Application
=========================================
Select Option >>
