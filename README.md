# Employee Management System - Backend

[![API Status](https://img.shields.io/badge/API_Health-Online-brightgreen?style=for-the-badge&logo=springboot&logoColor=white)](https://9mw56dfg4l.execute-api.us-east-2.amazonaws.com/actuator/health)
[![Live Frontend](https://img.shields.io/badge/Live_App-AWS_Amplify-FF9900?style=for-the-badge&logo=awsamplify&logoColor=white)](https://main.d1u8p67w1orxg1.amplifyapp.com)

This project contains the backend of the Employee Management System sample web application, made with Java and Spring Boot.

The frontend for this web application can be found here:
-[ Employee Management System - Frontend](https://github.com/JesseHerrera04/ems-frontend)

This project was made to further develop my skills in Java and Spring Framework Web Application development.
It works in conjunction with the frontend, a React Application, to simulate a real world web application.

## Project Requirements
- Java 25 or newer
- PostgreSQL Database named `ems` on port `5432` (default, can be overridden with environment variables)
- Maven (or the included Maven Wrapper)

## How to Build
- Download or clone this repository
- Open the project in an IDE of your choice
- Ensure PostgreSQL is running locally with a database named `ems`
- Optionally set environment variables to override local defaults:
    - `DB_URL`: JDBC URL of the PostgreSQL database (default: `jdbc:postgresql://localhost:5432/ems`)
    - `DB_USER`: PostgreSQL username (default: `postgres`)
    - `DB_PASS`: PostgreSQL password (default: empty for local development)
- Set `SPRING_PROFILES_ACTIVE=dev` to use the H2 in-memory database instead of PostgreSQL (no server needed).
- Build and test with `./mvnw clean verify` (Windows: `mvnw.cmd clean verify`).

## Run
- IDE: Run with Maven or with IDE Run button
- Ensure PostgreSQL is running beforehand, and the `ems` database exists with valid credentials (unless overridden by
  environment variables or the `dev` profile)

## AWS Deployment (EC2, RDS PostgreSQL, GitHub Actions)

The `prod` profile is intended for EC2 and requires these runtime variables:

- `SPRING_PROFILES_ACTIVE=prod`
- `DB_URL`: `jdbc:postgresql://<RDS-endpoint>:5432/ems`
- `DB_USER` and `DB_PASS`: PostgreSQL credentials used by both the application and Flyway
- `CORS_ALLOWED_ORIGIN`: exact HTTPS origin of the deployed Amplify frontend

Store the database credentials in AWS Secrets Manager or another protected runtime configuration source. Do not commit credentials or add database secrets to GitHub Actions. The EC2 instance profile should have read access only to the required secret. Configure the `ems` database on RDS before deployment. For a new empty database, Flyway uses `DB_USER` and `DB_PASS` to apply `src/main/resources/db/migration/V1__create_employees_table.sql`; Hibernate validates the resulting schema and does not update it. For this demo setup, that database user needs permission to create the table.

The CodeDeploy bundle consists of `appspec.yml`, the service unit under `deploy/`, and lifecycle scripts under `scripts/`. The service expects the EC2 secret/configuration to be materialized as `/etc/ems-backend/ems-backend.env` with restrictive permissions before deployment. EC2 must have Java 25, curl, systemd, and the CodeDeploy agent installed.

The GitHub Actions workflow at `.github/workflows/backend-ci-cd.yml` runs tests and JaCoCo on pull requests and pushes to `main`. A push to `main` also publishes the bundle to S3 and deploys through CodeDeploy using standard AWS access keys. Configure the GitHub `production` environment with these secrets and variables:

- Secrets: `AWS_ACCESS_KEY_ID` and `AWS_SECRET_ACCESS_KEY`
- Variables: `AWS_REGION`, `DEPLOYMENT_BUCKET`, `CODEDEPLOY_APPLICATION`, and `CODEDEPLOY_DEPLOYMENT_GROUP`

Create a dedicated IAM deployer identity for the workflow; do not use root or a personal access key. Grant it only the S3 upload and CodeDeploy create/read permissions needed for this deployment. Create an access key for that identity and store both key values as GitHub **environment secrets**, never in workflow files or repository variables. Rotate or revoke the key if it is exposed, and prefer short-lived OIDC credentials when you are ready to use them. Protect the GitHub `production` environment with required reviewers if deployment approval is desired. AWS still needs an S3 bucket for deployment bundles, a CodeDeploy application/deployment group targeting the EC2 instance, the CodeDeploy service role, and an EC2 instance profile for SSM, CodeDeploy artifact access, and reading the runtime secret.

For network security, keep RDS private and allow PostgreSQL port 5432 only from the backend EC2 security group. If using an Application Load Balancer, allow the backend port only from its security group and configure its health check to `/actuator/health`. Production exposes health only and hides component details; metrics are not exposed over HTTP in the `prod` profile.
