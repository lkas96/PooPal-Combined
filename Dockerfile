
# ------------ Build Stage ------------
FROM openjdk:23 AS builde
WORKDIR /sr
# Copy Maven wrapper and project files
COPY mvnw .
COPY pom.xml .
COPY .mvn .mvn
COPY src sr
# Make the Maven wrapper executable and build the project
RUN chmod +x mvnw && ./mvnw package -Dmaven.test.skip=tru
# ------------ Runtime Stage ------------
FROM openjdk:2
WORKDIR /ap
# Copy the built JAR from the builder stage
COPY --from=builder /src/target/poopal-server-0.0.1-SNAPSHOT.jar app.ja
# Set default server port
ENV PORT=808
# Expose the application port
EXPOSE 808
# Set shell and entrypoint to run the app
SHELL ["/bin/sh", "-c"]
ENTRYPOINT SERVER_PORT=${PORT} java -jar app.jarF