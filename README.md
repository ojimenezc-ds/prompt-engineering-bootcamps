# Hello World Kotlin Service

A simple Spring Boot application written in Kotlin that provides a Hello World REST API.

## Features

- Spring Boot 3.2.0
- Kotlin 1.9.20
- REST API endpoints for greeting
- Unit tests included

## API Endpoints

- `GET /api/hello` - Returns "Hello, World!"
- `GET /api/hello/{name}` - Returns "Hello, {name}!"

## Running the Application

### Prerequisites
- Java 17 or higher
- Gradle (or use the included Gradle wrapper)

### Build and Run
```bash
# Using Gradle wrapper (recommended)
./gradlew bootRun

# Or build and run the JAR
./gradlew build
java -jar build/libs/hello-world-service-0.0.1-SNAPSHOT.jar
```

### Running Tests
```bash
./gradlew test
```

The application will start on port 8080 by default.

## Testing the API

Once the application is running, you can test the endpoints:

```bash
# Test hello endpoint
curl http://localhost:8080/api/hello

# Test hello with name endpoint
curl http://localhost:8080/api/hello/John
```

## Project Structure

```
src/
├── main/
│   ├── kotlin/
│   │   └── com/example/helloworldservice/
│   │       ├── HelloWorldServiceApplication.kt
│   │       └── controller/
│   │           └── HelloWorldController.kt
│   └── resources/
│       └── application.properties
└── test/
    └── kotlin/
        └── com/example/helloworldservice/
            ├── HelloWorldServiceApplicationTests.kt
            └── controller/
                └── HelloWorldControllerTest.kt
```
