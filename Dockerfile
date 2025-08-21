# Step 1: Build the application
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app

# Install Maven
RUN apt-get update && apt-get install -y maven

# Copy project files
COPY . .

# Build the app with Maven
RUN mvn clean package -DskipTests

# Step 2: Use a slim JRE image for running
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the built jar from builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose port
EXPOSE 8181

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
