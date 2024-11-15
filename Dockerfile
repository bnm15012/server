# Base image with Maven and OpenJDK for building the app
FROM maven:3.9.9-amazoncorretto-17 AS build

WORKDIR /studio_backend

# Copy Maven configuration and source code
COPY pom.xml .
COPY src ./src

# Build the application (skip tests for quicker build)
RUN mvn clean package -DskipTests

# Use a slim OpenJDK base image to run the app
FROM openjdk:17-slim-bullseye

WORKDIR /studio_backend

# Copy the built JAR from the 'build' stage
COPY --from=build /studio_backend/target/studio_backend-0.0.1-SNAPSHOT.jar studio_backend.jar

# Expose the port the app will run on
EXPOSE 7000

# Define the command to run the app
ENTRYPOINT ["java", "-jar", "app.jar"]