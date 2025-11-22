# Employee Management System - Backend

This project contains the backend of the Employee Management System sample web application, made with Java and Spring Boot.

The frontend for this web application can be found here:
-[ Employee Management System - Frontend](https://github.com/JesseHerrera04/ems-frontend)

This project was made to further develop my skills in Java and Spring Framework Web Application development.
It works in conjunction with the frontend, a React Application, to simulate a real world web application.

## Project Requirements
- Java 17 or newer
- MySQL Database named `ems` on port `3307` (default, can be overridden with environment variables)
- Maven installed
## How to Build

- Download or clone this repository
- Open the project in an IDE of your choice
- Ensure MySQL server is running at port `3307` with no password (default application configuration)
- Optional: Set environment variables if you want to override defaults
    - `DB_URL`: URL of MySQL database (database name and port can be different from default)
    - `DB_USER`: Username of MySQL database
    - `DB_PASSWORD`: Password of MySQL database (leave empty if no password)
    - Optional: set `SPRING_PROFILES_ACTIVE` to `dev` to use H2 database instead of MySQL (No server needed)
- Build the project using Maven `install` or `package` command

## Run
- IDE: Run with Maven or with IDE Run button
- Ensure MySQL server is running beforehand, and `ems` database exists with valid credentials (unless overridden by env
  var or profiles, else will use defaults)
