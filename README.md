# Users API Service

A Spring Boot application written in Kotlin that provides a REST API for user management, reading user data from a JSON file.

## Features

- Spring Boot 4.0.0
- Kotlin 2.2.0
- REST API for user management
- JSON file-based data source
- Swagger/OpenAPI documentation
- Comprehensive user model with detailed fields

## API Endpoints

- `GET /api/users` - Returns all users from the users.json file

### API Documentation
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Running the Application

### Prerequisites
- Java 17 or higher
- Gradle (or use the included Gradle wrapper)

### Setting up the Java dependency

With brew:
```bash
brew install openjdk@17
export JAVA_HOME=/opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk/Contents/Home

java -version
export PATH="/opt/homebrew/opt/openjdk@17/bin:$PATH"
```

### Build and Run
```bash
# Using Gradle wrapper (recommended)
./gradlew bootRun

# Or build and run the JAR
./gradlew build
java -jar build/libs/Prompt-engineering-bootcamps-0.0.1-SNAPSHOT.war
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
