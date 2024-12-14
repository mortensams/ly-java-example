# Temperature Analysis Service

A Quarkus-based REST service for analyzing and aggregating temperature time series data. This service provides endpoints for data aggregation with flexible time windows and resolutions.

## Features

- Temperature data aggregation with configurable time windows
- Flexible resolution for data aggregation
- Support for both ambient and device temperature analysis
- Health check endpoint
- Containerized deployment
- Comprehensive test coverage
- GitHub Actions CI/CD pipeline

## Prerequisites

- Java 17 or later
- Maven 3.8+
- Docker (for containerization)
- Git

## Building the Application

```bash
# Clone the repository
git clone <repository-url>
cd ly-java-example

# Build the application
mvn clean package

# Run tests
mvn test

# Generate test coverage report
mvn verify
```

## Running the Application

### Local Development

```bash
# Run in development mode
mvn quarkus:dev

# Generate sample data
mvn exec:java -Dexec.mainClass="com.example.temperature.util.DataGenerator"
```

### Docker Container

```bash
# Build the container
docker build -t temperature-service .

# Run the container
docker run -p 8080:8080 temperature-service
```

## API Endpoints

### Health Check
```
GET /api/v1/temperature/health
```

### Aggregate Temperature Data
```
GET /api/v1/temperature/aggregate
```

Query Parameters:
- `startTime`: Start time in ISO 8601 format
- `endTime`: End time in ISO 8601 format
- `resolution`: Aggregation resolution in seconds (default: 60)

## CI/CD Pipeline

The project includes a GitHub Actions pipeline that:
1. Performs static code analysis
2. Runs all tests and generates coverage reports
3. Builds the application and creates a Docker image
4. Pushes the Docker image to GitHub Container Registry

## Development

### Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/example/temperature/
│   │       ├── model/
│   │       ├── resource/
│   │       ├── service/
│   │       └── util/
│   └── resources/
│       └── application.properties
├── test/
│   └── java/
│       └── com/example/temperature/
└── pom.xml
```

### Running Tests

```bash
# Run unit tests
mvn test

# Run integration tests
mvn verify

# Generate coverage report
mvn jacoco:report
```

## Container Labels

The Docker image includes the following labels for traceability:
- `org.opencontainers.image.source`: GitHub repository URL
- `org.opencontainers.image.revision`: Git commit SHA
- `org.opencontainers.image.version`: Project version
- `org.opencontainers.image.created`: Build timestamp

## License

This project is licensed under the MIT License - see the LICENSE file for details.