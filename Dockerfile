# Build stage
FROM maven:3.9.5-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src src
RUN mvn package -DskipTests

# Runtime stage
FROM eclipse-temurin:21.0.1_12-jre-alpine
WORKDIR /app

# Add labels for better traceability
LABEL org.opencontainers.image.source="https://github.com/${GITHUB_REPOSITORY}"
LABEL org.opencontainers.image.revision="${GITHUB_SHA}"
LABEL org.opencontainers.image.version="${PROJECT_VERSION}"
LABEL org.opencontainers.image.created="${BUILD_DATE}"

# Create non-root user
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Copy the built artifact
COPY --from=build /app/target/quarkus-app/lib/ /app/lib/
COPY --from=build /app/target/quarkus-app/*.jar /app/
COPY --from=build /app/target/quarkus-app/app/ /app/app/
COPY --from=build /app/target/quarkus-app/quarkus/ /app/quarkus/

# Switch to non-root user
USER appuser

EXPOSE 8080
ENV JAVA_OPTS="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
ENV JAVA_APP_JAR="/app/quarkus-run.jar"

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar $JAVA_APP_JAR" ]