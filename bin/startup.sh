#!/bin/bash

# Check if the application is already running
if [ -f "logs/app.pid" ]; then
  EXISTING_PID=$(cat logs/app.pid)
  if ps -p $EXISTING_PID > /dev/null 2>&1; then
    echo "Stopping existing application with PID: $EXISTING_PID"
    kill $EXISTING_PID
    if [ $? -eq 0 ]; then
      echo "Application stopped successfully."
    else
      echo "Error: Failed to stop the application. Please check manually."
      exit 1
    fi
  fi
  rm -f logs/app.pid
fi

# Ensure the script is executed from the project root
cd "$(dirname "$0")/.." || exit

# Verify we are in the correct directory
if [ ! -f "pom.xml" ]; then
  echo "Error: Could not find pom.xml. Please ensure the script is in the correct location."
  exit 1
fi

# Source the setenv.sh file if it exists
if [ -f "bin/setenv.sh" ]; then
  echo "Sourcing environment variables from setenv.sh..."
  source bin/setenv.sh
fi

# Build the project using Maven
echo "Building the project..."
mvn clean package -DskipTests
if [ $? -ne 0 ]; then
  echo "Error: Maven build failed. Please check the logs for details."
  exit 1
fi

# Check if the JAR file exists
if [ ! -f "$JAR_FILE" ]; then
  echo "Error: JAR file not found at $JAR_FILE. Please build the project first."
  exit 1
fi

# Create logs directory if it doesn't exist
mkdir -p logs

# Run the application
echo "Starting the application in $ENV environment..."
nohup java $JAVA_OPTS -Dspring.profiles.active=$ENV -jar "$JAR_FILE" > "$LOG_FILE" 2>&1 &

# Get the process ID of the application
APP_PID=$!
echo "Application started with PID: $APP_PID"
echo "Logs are being written to $LOG_FILE"

# Save the PID to a file for later use
echo $APP_PID > logs/app.pid