#!/bin/bash

# Set environment variables
export ENV=
export SPRING_PROFILES_ACTIVE="prod"
export JAR_FILE="target/studio_backend-0.0.1-SNAPSHOT.jar"
export LOG_FILE="logs/application.log"

# Set JVM options
export JAVA_OPTS="-Xms512m -Xmx512m -XX:+UseG1GC"

# Optional: Add any other environment-specific configurations
export LOG_DIR="logs"
export APP_NAME="studio-service"