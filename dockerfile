# Use a base image with the JVM installed
FROM eclipse-temurin:17-jre AS runtime

# Set the working directory inside the container
WORKDIR /app

# Copy the Quarkus application JAR file to the container
COPY target/graalVm-quarkus-project-1.0.0-SNAPSHOT-runner.jar app.jar

# Expose the port your application listens on (change as per your configuration)
EXPOSE 8080

# Run the application using the java command
ENTRYPOINT ["java", "-jar", "app.jar"]