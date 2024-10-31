# Use a Maven image to build the app
FROM maven:3.8.5-openjdk-17 AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Start a new stage to reduce image size
FROM openjdk:17-slim

# Copy the JAR file into the service directory from the previous stage
COPY --from=build /app/target/order-service.jar order-service.jar

# Expose the port your app runs on
EXPOSE 8080

# Command to run the app
ENTRYPOINT ["java", "-jar", "order-service.jar"]
