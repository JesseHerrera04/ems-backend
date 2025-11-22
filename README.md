# Employee Management System - Backend

This project contains the backend of the Employee Management System sample web application, made with Java and Spring Boot.

The frontend for this web application can be found here:
-[ Employee Management System - Frontend](https://github.com/JesseHerrera04/ems-frontend)

This project was made to further develop my skills in Java and Spring Framework Web Application development.
It works in conjunction with the frontend, a React Application, to simulate a real world web application.

## Project Requirements
- Java 17 or newer
- MySQL Database on port 3307 (default, can be overrided with environment variabless)

## How to Build
- Download from main branch or .jar 
- Pass in environment variables: DB_URL (URL for DB), DB_USER (Username for DB User), & DB_PASS (DB User Password, can be empty)
  - Default Values are used if nothing is entered (see application.properties)
- If using IDE, run Maven Install command

## Run
- IDE: Run with Maven or with IDE Run button
- Terminal/Command Line: Run with java -jar {jar-name}.jar
- Ensure MySQL server is running beforehand, and ems database exists with valid credentials (unless overridden by env, else will use defaults)
