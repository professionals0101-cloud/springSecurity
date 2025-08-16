# Step 1: Use a base image with JDK for building
FROM eclipse-temurin:21-jdk as builder

# Set working directory
WORKDIR /app

# Copy Maven/Gradle wrapper & project files
COPY . .

# If using Maven:
RUN ./mvnw clean package -DskipTests

# If using Gradle:
# RUN ./gradlew bootJar --no-daemon

# Step 2: Use a slim JRE image for running
FROM eclipse-temurin:21-jre

# Set working directory
WORKDIR /app

# Copy the built jar from builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose
EXPOSE 8181

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]