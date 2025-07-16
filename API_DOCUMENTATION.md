# User Management API

This is a REST API for managing users with a JSON file-based data source built with Spring Boot 4.0.0 and Kotlin 2.2.0.

## API Endpoints

### 1. Get All Users
- **URL**: `GET /api/users`
- **Description**: Retrieves all users from the JSON file
- **Response**: 200 OK with UsersResponse containing user list and pagination info

### 2. Get User by ID
- **URL**: `GET /api/users/{id}`
- **Description**: Retrieves a specific user by their ID
- **Path Parameters**: 
  - `id` (integer): The user ID
- **Responses**: 
  - 200 OK: User found
  - 404 Not Found: User not found

### 3. Create New User
- **URL**: `POST /api/users`
- **Description**: Creates a new user
- **Content-Type**: `application/json`
- **Request Body**: CreateUserRequest with the following required fields:
  - `firstName` (string): User's first name
  - `lastName` (string): User's last name
  - `age` (integer): User's age (must be positive)
  - `gender` (string): User's gender
  - `email` (string): Valid email address
  - `phone` (string): Phone number
  - `username` (string): Username
  - `password` (string): Password
  - `birthDate` (string): Birth date
- **Responses**: 
  - 201 Created: User created successfully
  - 400 Bad Request: Invalid input data

### 4. Update User Age
- **URL**: `PATCH /api/users/{id}/age`
- **Description**: Updates the age of a specific user
- **Path Parameters**: 
  - `id` (integer): The user ID
- **Content-Type**: `application/json`
- **Request Body**: UpdateUserAgeRequest
  - `age` (integer): New age (must be positive)
- **Responses**: 
  - 200 OK: User age updated successfully
  - 400 Bad Request: Invalid age value
  - 404 Not Found: User not found

### 5. Delete User
- **URL**: `DELETE /api/users/{id}`
- **Description**: Deletes a specific user by their ID
- **Path Parameters**: 
  - `id` (integer): The user ID
- **Responses**: 
  - 204 No Content: User deleted successfully
  - 404 Not Found: User not found

## Example Usage

### Create a New User
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "age": 30,
    "gender": "male",
    "email": "john.doe@example.com",
    "phone": "+1-555-123-4567",
    "username": "johndoe",
    "password": "password123",
    "birthDate": "1994-01-01"
  }'
```

### Get User by ID
```bash
curl http://localhost:8080/api/users/1
```

### Update User Age
```bash
curl -X PATCH http://localhost:8080/api/users/1/age \
  -H "Content-Type: application/json" \
  -d '{"age": 35}'
```

### Delete User
```bash
curl -X DELETE http://localhost:8080/api/users/1
```

## Error Handling

The API includes comprehensive error handling:

- **400 Bad Request**: Invalid input data or validation errors
- **404 Not Found**: Resource not found
- **500 Internal Server Error**: Server-side errors

All error responses include:
- `timestamp`: When the error occurred
- `status`: HTTP status code
- `error`: Error type
- `message`: Error description
- `details`: Additional error information (for validation errors)

## Swagger Documentation

The API documentation is also available via Swagger UI at:
`http://localhost:8080/swagger-ui.html`

## Running the Application

1. Build the project: `./gradlew build`
2. Run the application: `./gradlew bootRun`
3. The API will be available at `http://localhost:8080`

## Data Persistence

The user data is stored in `src/main/resources/users.json` and is automatically updated when users are created, updated, or deleted.
