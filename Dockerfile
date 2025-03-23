
# ------------ Build Stage ------------
FROM openjdk:23 AS builder

WORKDIR /src

# Copy Maven wrapper and project files
COPY mvnw .
COPY pom.xml .
COPY .mvn .mvn
COPY src src

# Make the Maven wrapper executable and build the project
RUN chmod +x mvnw && ./mvnw package -Dmaven.test.skip=true

# ------------ Runtime Stage ------------
FROM openjdk:23

WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /src/target/poopal-server-0.0.1-SNAPSHOT.jar app.jar

# Set default server port
ENV PORT=8080

# Expose the application port
EXPOSE 8080

# Set shell and entrypoint to run the app
SHELL ["/bin/sh", "-c"]
ENTRYPOINT SERVER_PORT=${PORT} java -jar app.jar