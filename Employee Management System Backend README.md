# Employee Management System - Backend

[![Java](https://img.shields.io/badge/Java-17%20%2B-orange?style=flat-square&logo=openjdk)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=flat-square&logo=springboot)](https://spring.io/projects/spring-boot)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

The backend service for the **Employee Management System**, built with Java and Spring Boot to provide a robust RESTful API for administrative operations. 

> **Architecture Note:** This backend service is completely **decoupled** from the front-end user interface to reduce tight coupling, optimize resource usage, and allow both systems to scale independently.

---

## 🛠️ Tech Stack & Architecture
* **Core Language:** Java 17+
* **Framework:** Spring Boot (Spring Data JPA, Spring Web)
* **Database:** MySQL (Production/Default) / H2 In-Memory Database (Development/Testing)
* **Build Tool:** Maven

---

## 📡 API Endpoints

Base URL: `http://localhost:8080/api/employees`

| Method | Endpoint | Request Body | Response | Description |
| :--- | :--- | :--- | :--- | :--- |
| **GET** | `/` | None | `List<EmployeeDTO>` (200 OK) | Retrieve a list of all employee records |
| **GET** | `/{id}` | None | `EmployeeDTO` (200 OK) | Retrieve a specific employee by their ID |
| **POST** | `/` | `EmployeeDTO` | `EmployeeDTO` (201 Created) | Create a new employee record |
| **PUT** | `/{id}` | `EmployeeDTO` | `EmployeeDTO` (200 OK) | Update an existing employee record by ID |
| **DELETE** | `/{id}` | None | `String` (200 OK) | Delete an employee record by ID ("Employee Successfully Deleted.") |

---

## 🧪 Testing & Interaction

Because of the decoupled design, you can interact with and test the API using either of the following methods:

1. **Postman / cURL (Recommended for API Testing):**
   * Import the endpoints listed above into Postman to test request payloads and response codes directly without needing to spin up the client UI.
2. **React Front-End Application:**
   * Pair this service with the companion client interface located at the [React Frontend Repository](https://github.com/YOUR_USERNAME/FRONTEND_REPO_NAME). Ensure both applications are running locally to perform full end-to-end integration testing.

---

## ⚙️ Prerequisites & Requirements
* **Java 17** or newer installed locally.
* **Maven** for dependency management and building.
* **MySQL Server** running on port `3307` (Optional if using the H2 profile).

---

## 🚀 Getting Started & Local Setup

### Option A: Quick Test using H2 In-Memory Database (No Server Required)
If you want to run the application instantly without setting up a MySQL server, use the built-in development profile:
1. Clone the repository:
   ```bash
   git clone https://github.com/YOUR_USERNAME/YOUR_REPO_NAME.git
   ```
2. Run the application with the `dev` profile enabled:
   ```bash
   mvn spring-boot:run -Dspring-boot.run.profiles=dev
   ```

### Option B: Running with MySQL Database
1. Ensure your local MySQL server is running (default port: `3307`).
2. Create a database named `ems`:
   ```sql
   CREATE DATABASE ems;
   ```
3. (Optional) Configure environment variables if your credentials or setup differ from the default configuration (default assumes port `3307` with no password):
   * `DB_URL`: JDBC URL of your MySQL database
   * `DB_USER`: Database username (default: `root`)
   * `DB_PASSWORD`: Database password (default: empty)
4. Build and run the project:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

---

## 💻 Running in VS Code

1. Install the **Spring Boot Extension Pack** in VS Code.
2. Create a `.env` file in the project root directory:
   ```env
   DB_URL=jdbc:mysql://localhost:3307/ems
   DB_USER=root
   DB_PASSWORD=
   ```
3. Pass active profiles via `.vscode/launch.json`:
   ```json
   {
     "type": "java",
     "name": "Spring Boot - Dev",
     "request": "launch",
     "mainClass": "com.example.ems.EmsApplication",
     "args": "--spring.profiles.active=dev"
   }
   ```