# Users API Service

A Spring Boot application written in Kotlin that provides a comprehensive REST API for user management, with full CRUD operations and JSON file-based data persistence.

## Features

- Spring Boot 4.0.0
- Kotlin 2.2.0
- Complete CRUD operations for user management
- JSON file-based data source with persistence
- Input validation with detailed error responses
- Swagger/OpenAPI documentation
- Comprehensive user model with detailed fields
- Thread-safe ID generation
- Global exception handling

## API Endpoints

### User Management
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `POST /api/users` - Create a new user
- `PUT /api/users/{id}/age` - Update user's age
- `DELETE /api/users/{id}` - Delete user by ID

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

# Swagger
http://localhost:8080/swagger-ui/index.html
```

### Running Tests
```bash
./gradlew test
```

The application will start on port 8080 by default.

## Testing the API

Once the application is running, you can test all CRUD endpoints:

### Get All Users
```bash
curl http://localhost:8080/api/users
```

### Get User by ID
```bash
curl http://localhost:8080/api/users/1
```

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
    "phone": "+1 555-123-4567",
    "username": "johndoe",
    "password": "securepass123",
    "birthDate": "1993-1-15",
    "image": "https://example.com/profile.jpg",
    "bloodGroup": "A+",
    "height": 175.5,
    "weight": 70.0,
    "eyeColor": "Brown",
    "hair": {
      "color": "Black",
      "type": "Straight"
    },
    "ip": "192.168.1.1",
    "address": {
      "address": "123 Main St",
      "city": "New York",
      "state": "New York",
      "stateCode": "NY",
      "postalCode": "10001",
      "coordinates": {
        "lat": 40.7128,
        "lng": -74.0060
      },
      "country": "United States"
    },
    "macAddress": "00:11:22:33:44:55",
    "university": "Harvard University",
    "bank": {
      "cardExpire": "12/25",
      "cardNumber": "1234567890123456",
      "cardType": "Visa",
      "currency": "USD",
      "iban": "US123456789012345678"
    },
    "company": {
      "department": "Engineering",
      "name": "Tech Corp",
      "title": "Software Engineer"
    },
    "ein": "12-3456789",
    "ssn": "123-45-6789",
    "userAgent": "Mozilla/5.0...",
    "crypto": {
      "coin": "Bitcoin",
      "wallet": "1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa",
      "network": "Bitcoin"
    },
    "role": "user"
  }'
```

### Update User Age
```bash
curl -X PUT http://localhost:8080/api/users/1/age \
  -H "Content-Type: application/json" \
  -d '{
    "age": 31
  }'
```

### Delete User
```bash
curl -X DELETE http://localhost:8080/api/users/1
```

### Testing with Pretty JSON
```bash
# Get all users with formatted JSON
curl http://localhost:8080/api/users | jq

# Get user by ID with formatted JSON
curl http://localhost:8080/api/users/1 | jq
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
│   │       │   └── UserController.kt                      # REST controller with CRUD operations
│   │       ├── service/
│   │       │   └── UserService.kt                         # Service layer with business logic
│   │       ├── model/
│   │       │   ├── User.kt                                # User data models
│   │       │   └── UserDto.kt                             # DTOs for create/update operations
│   │       ├── exception/
│   │       │   └── GlobalExceptionHandler.kt              # Global error handling
│   │       └── config/
│   │           └── SwaggerConfig.kt                       # Swagger/OpenAPI configuration
│   └── resources/
│       ├── application.properties                         # Application configuration
│       └── users.json                                     # User data source (persisted)
└── test/
    └── kotlin/
        └── com/bootcamps/Prompt/engineering/bootcamps/
            ├── PromptEngineeringBootcampsApplicationTests.kt # Application tests
            ├── controller/
            │   └── UserControllerIntegrationTest.kt        # Integration tests for API endpoints
            └── service/
                └── UserServiceTest.kt                      # Unit tests for service layer
```

## Data Model

The Users API provides comprehensive user management with detailed information including:

### User Fields
- **Personal Details**: firstName, lastName, maidenName, age, gender, birthDate
- **Contact Information**: email, phone, address (with coordinates)
- **Authentication**: username, password
- **Professional Details**: company information, university
- **Financial Information**: bank details, EIN, SSN
- **Physical Attributes**: height, weight, eyeColor, hair details, bloodGroup
- **Technical Information**: IP address, MAC address, userAgent
- **Cryptocurrency**: wallet and network information
- **System**: role, profile image

### Input Validation
The API includes comprehensive validation for:
- Required fields (firstName, lastName, email, etc.)
- Email format validation
- Age range validation (0-150)
- String length validation (username, password, etc.)
- Custom validation for complex nested objects

### Error Handling
- Detailed validation error messages
- HTTP status codes (200, 201, 400, 404, 500)
- Structured error responses with field-specific details
- Global exception handling for consistent error format

## CRUD Operations Details

### Create User (POST /api/users)
- Automatically assigns unique ID
- Validates all required fields
- Thread-safe ID generation
- Returns 201 Created with user data

### Read Operations
- **GET /api/users**: Returns paginated list of all users
- **GET /api/users/{id}**: Returns specific user or 404 if not found

### Update User Age (PUT /api/users/{id}/age)
- Updates only the age field
- Validates age range (0-150)
- Returns updated user data or 404 if user not found

### Delete User (DELETE /api/users/{id})
- Removes user from the system
- Returns 204 No Content on success
- Returns 404 if user not found

### Data Persistence
- All changes are persisted to the JSON file
- Thread-safe operations
- Maintains data integrity across application restarts

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
