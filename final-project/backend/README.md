# Time Traveler's Diary

A simple, practical Spring Boot application that manages time travel diary entries, handles timeline paradoxes asynchronously using Kafka, and prevents duplicate entries.

## Project Structure

The project has been refactored into two microservices:
* **backend**: The main service handling authentication, users, and accepting new diary entries.
* **anomaly-service**: An asynchronous microservice (Kafka consumer) that validates timeline rules and saves valid entries. Note: This folder is currently inside `backend` but can be moved outside to the project root.

## Features

- **Authentication**: JWT-based authentication with short-lived access tokens and long-lived refresh tokens.
- **Async Validation**: Diary entries are submitted to Kafka and validated by the `anomaly-service` asynchronously.
- **Paradox Detection**: Prevents travelers from being at the exact same location at the exact same time.
- **Duplicate Prevention**: Prevents creating duplicate entries with the same content.
- **Audit Logging**: Simple and transparent logging of user actions (`logs` table).

## Requirements

- Java 17+
- Docker & Docker Compose (for PostgreSQL and Kafka)
- Maven

## How to Run

1. **Start Infrastructure (PostgreSQL & Kafka)**
   ```bash
   cd backend
   docker-compose up -d
   ```

2. **Run Backend Service**
   ```bash
   cd backend
   mvn spring-boot:run
   ```

3. **Run Anomaly Service**
   ```bash
   cd backend/anomaly-service
   mvn spring-boot:run
   ```

## Usage

1. Register a new traveler: `POST /auth/register`
2. Login to get tokens: `POST /auth/login`
3. Submit a diary entry: `POST /entries` (Requires Bearer Token)
4. View your entries: `GET /entries` (Requires Bearer Token)
5. Refresh your token: `POST /auth/refresh`
6. Logout securely: `POST /auth/logout`

*Note: All business endpoints require authentication. Attempting to login/register while authenticated will be blocked.*
