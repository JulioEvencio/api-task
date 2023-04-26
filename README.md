# API Task

This project is a REST API of a task manager

## Requirements

- Java 17
- Spring Boot 3
- PostgreSQL 15

## How to use

You can send requests to the API using the available routes. The routes are:

- POST `/api/auth/register`
- POST `/api/auth/login`
- GET `/api/users/me`
- GET `/api/users` - Only ADMIN
- GET `/api/tasks`
- GET `/api/tasks/{id}`
- GET `/api/tasks/title/{title}`
- GET `/api/tasks/completed/{completed}`
- POST `/api/tasks`
- PUT `/api/tasks/{id}`
- DELETE `/api/tasks/{id}`

Some endpoints require authentication via a JWT access token.

## Swagger Documentation

The API documentation is available on Swagger. To access it, follow these steps:

1. Start the API using the command `./mvnw spring-boot:run`
2. Open a web browser and navigate to `http://localhost:8080/swagger-ui/index.html`
3. The API documentation should be displayed in Swagger.

## How to run

1. Clone this repository
2. create a database named `db_api_task`
3. Configure your database in the application.yml file
4. Run `./mvnw spring-boot:run`

## Contribution

Please feel free to send pull requests and report issues.
