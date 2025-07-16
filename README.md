# Users API Service

A Spring Boot application written in Kotlin that provides a comprehensive REST API for user management with full CRUD operations, reading initial user data from a JSON file.

## Features

- Spring Boot 4.0.0
- Kotlin 2.2.0
- **Complete CRUD REST API** for user management
- JSON file-based initial data source
- **In-memory storage** for runtime operations
- Swagger/OpenAPI documentation
- Comprehensive user model with detailed fields
- **Thread-safe operations** with atomic ID generation
- **Proper HTTP status codes** and error handling

## CRUD Operations Implemented

✅ **CREATE A NEW USER** - `POST /api/users`
✅ **GET USER BY ID** - `GET /api/users/{id}` 
✅ **UPDATE USER'S AGE** - `PATCH /api/users/{id}/age`
✅ **DELETE USER BY ID** - `DELETE /api/users/{id}`

## API Endpoints

### User Management
- `GET /api/users` - Returns all users from the system
- `GET /api/users/{id}` - Returns a specific user by ID
- `POST /api/users` - Creates a new user
- `PATCH /api/users/{id}/age` - Updates a user's age
- `DELETE /api/users/{id}` - Deletes a user by ID

### API Documentation
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Request/Response Examples

### Create User
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
    "password": "securepass",
    "birthDate": "1994-01-15"
  }'
```

### Update User Age
```bash
curl -X PATCH http://localhost:8080/api/users/1/age \
  -H "Content-Type: application/json" \
  -d '{"age": 31}'
```

### Delete User
```bash
curl -X DELETE http://localhost:8080/api/users/1
```

## Running the Application

### Prerequisites
- Java 17 or higher
- Gradle (or use the included Gradle wrapper)

### Setting up Java (macOS with Homebrew)

If you get "Unable to locate a Java Runtime" error, install and configure Java:

```bash
# Install OpenJDK 17
brew install openjdk@17

# Set environment variables (add to your ~/.zshrc for persistence)
export JAVA_HOME=/opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk/Contents/Home
export PATH="/opt/homebrew/opt/openjdk@17/bin:$PATH"

# Verify installation
java -version
```

### Build and Run
```bash
# Using Gradle wrapper (recommended)
JAVA_HOME=/opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk/Contents/Home ./gradlew bootRun

# Or build and run the JAR
./gradlew build
java -jar build/libs/Prompt-engineering-bootcamps-0.0.1-SNAPSHOT.war

# Access Swagger Documentation
http://localhost:8080/swagger-ui/index.html
```

### Running Tests
```bash
./gradlew test
```

The application will start on port 8080 by default.

## Testing the API

Once the application is running, you can test the endpoints:

```bash
# Test users endpoint - Get all users
curl http://localhost:8080/api/users

# Or with pretty JSON formatting
curl http://localhost:8080/api/users | jq

# Test endpoint with specific headers
curl -H "Accept: application/json" http://localhost:8080/api/users
```

You can also access the interactive API documentation at: `http://localhost:8080/swagger-ui/index.html`

## Project Structure

```
src/
├── main/
│   ├── kotlin/
│   │   └── com/bootcamps/Prompt/engineering/bootcamps/
│   │       ├── PromptEngineeringBootcampsApplication.kt    # Main application class
│   │       ├── controller/
│   │       │   └── UserController.kt                      # REST controller for users
│   │       ├── service/
│   │       │   └── UserService.kt                         # Service layer for user logic
│   │       ├── model/
│   │       │   └── User.kt                                # User data models
│   │       └── config/
│   │           └── SwaggerConfig.kt                       # Swagger/OpenAPI configuration
│   └── resources/
│       ├── application.properties                         # Application configuration
│       └── users.json                                     # User data source
└── test/
    └── kotlin/
        └── com/bootcamps/Prompt/engineering/bootcamps/
            └── PromptEngineeringBootcampsApplicationTests.kt # Application tests
```

## Data Model

The Users API returns detailed user information including:
- Personal details (name, age, gender, etc.)
- Contact information (email, phone, address)
- Professional details (company, university)
- Banking information
- Physical attributes
- And more...

## Setup Instructions

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd prompt-engineering-bootcamps
   ```

2. **Ensure Java 17+ is installed**
   ```bash
   java -version
   ```

3. **Run the application**
   ```bash
   ./gradlew bootRun
   ```

4. **Access the API**
   - Users endpoint: `http://localhost:8080/api/users`
   - Swagger UI: `http://localhost:8080/swagger-ui/index.html`
