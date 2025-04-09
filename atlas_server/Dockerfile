# Use Eclipse Temurin OpenJDK 21 as the base image
FROM eclipse-temurin:21-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven-built JAR file from the target directory
COPY target/atlas_server-1.0-SNAPSHOT.jar app.jar

# Expose port 8080 for the Spring Boot app
EXPOSE 9090

# Set the entrypoint to run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]