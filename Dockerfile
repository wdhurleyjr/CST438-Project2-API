# Use an official BellSoft Liberica runtime as a parent image
FROM bellsoft/liberica-openjdk-alpine:17

# Set the working directory inside the container
WORKDIR /app

# Copy the project JAR file into the container
COPY target/Project2-0.0.1-SNAPSHOT.jar app.jar

# Expose the default port used by Spring Boot (Heroku will dynamically set PORT)
EXPOSE 8080

# Set environment variable for the port
ENV PORT=8080

# Run the jar file
ENTRYPOINT ["java", "-Dserver.port=${PORT}", "-jar", "/app/app.jar"]







