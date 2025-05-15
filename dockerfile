FROM eclipse-temurin:17-jre-alpine AS base
WORKDIR /app

# Copy the built Quarkus JAR file
COPY target/quarkus-app/lib/ /app/lib/
COPY target/quarkus-app/*.jar /app/
COPY target/quarkus-app/app/ /app/app/
COPY target/quarkus-app/quarkus/ /app/quarkus/

# Set JVM options (if needed)
ENV JAVA_OPTS="-Djava.net.preferIPv4Stack=true"

# Expose the required application port
EXPOSE 8080

# Run the Quarkus application
CMD ["java", "-jar", "/app/quarkus-run.jar"]