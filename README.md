# Employee Management System - Backend

This project contains the backend of the Employee Management System sample web application, made with Java and Spring Boot.

The frontend for this web application can be found here:
-[ Employee Management System - Frontend](https://github.com/JesseHerrera04/ems-frontend)

This project was made to further develop my skills in Java and Spring Framework Web Application development.
It works in conjunction with the frontend, a React Application, to simulate a real world web application.

## Project Requirements
- Java 25 or newer
- PostgreSQL Database named `ems` on port `5432` (default, can be overridden with environment variables)
- Maven installed
## How to Build

- Download or clone this repository
- Open the project in an IDE of your choice
- Ensure PostgreSQL is running locally with a database named `ems`
- Optional: Set environment variables if you want to override defaults
    - `DB_URL`: JDBC URL of the PostgreSQL database (default: `jdbc:postgresql://localhost:5432/ems`)
    - `DB_USER`: PostgreSQL username (default: `postgres`)
    - `DB_PASS`: PostgreSQL password (default: empty for local development)
    - Optional: set `SPRING_PROFILES_ACTIVE` to `dev` to use H2 instead of PostgreSQL (no server needed)
- Build the project using Maven `install` or `package` command

## Run
- IDE: Run with Maven or with IDE Run button
- Ensure PostgreSQL is running beforehand, and the `ems` database exists with valid credentials (unless overridden by
  environment variables or the `dev` profile)
