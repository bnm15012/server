# =========================
# Stage 1 - Build
# =========================
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build

WORKDIR /app

# Copy pom first for caching
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline

# Copy source
COPY src ./src

# Build jar
RUN mvn clean package -DskipTests

# =========================
# Stage 2 - Runtime
# =========================
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy jar from builder
COPY --from=build /app/target/*.jar app.jar

# App port
EXPOSE 8080

# JVM optimizations for containers
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
