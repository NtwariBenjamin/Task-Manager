Project Overview(Under Development)

The Task Management System is a RESTful backend API designed to manage tasks and projects efficiently. Users can create projects, assign tasks, track status, and manage deadlines. The system supports authentication, role-based access, and secure API endpoints.

This project is ideal for learning and demonstrating backend development, REST API design, JWT authentication, and Spring Boot best practices.

Features

Create, update, delete, and retrieve tasks and projects.

Assign tasks to users and track their status.

Manage projects with multiple tasks.

User authentication with JWT (JSON Web Tokens).

Role-based authorization for secure access.

Unit and integration tests for reliability.

Designed using Java, Spring Boot, and JPA/Hibernate.

Tech Stack

Backend: Java, Spring Boot, Spring Security

Database: H2 / MySQL / PostgreSQL (configurable)

ORM: JPA / Hibernate

Authentication: JWT

Testing: JUnit, Mockito

API Documentation: Swagger / OpenAPI

Build Tool: Maven

API Endpoints
User

POST /api/users/register – Register a new user

POST /api/users/login – Authenticate and receive JWT

Projects

POST /api/projects – Create a project

GET /api/projects – List all projects

GET /api/projects/{id} – Get project details

PUT /api/projects/{id} – Update a project

DELETE /api/projects/{id} – Delete a project

Tasks

POST /api/tasks – Create a new task

GET /api/tasks – List all tasks

GET /api/tasks/{id} – Get task details

PUT /api/tasks/{id} – Update a task

DELETE /api/tasks/{id} – Delete a task

Assign tasks to users via PUT /api/tasks/{id}/assign

(Add more endpoints as per your implementation.)

Installation

Clone the repository:

git clone https://github.com/yourusername/task-management-system.git


Navigate into the project directory:

cd task-management-system


Configure the database in application.properties (H2/MySQL/PostgreSQL).

Build the project:

mvn clean install


Run the application:

mvn spring-boot:run


Access the API at:

http://localhost:8080/api


Access Swagger API documentation:

http://localhost:8080/swagger-ui.html


Future Enhancements

Add file attachments to tasks.

Implement project/task notifications via email.

Add search and filtering for tasks/projects.

Implement a frontend interface (React/Angular/Vue).

Au
