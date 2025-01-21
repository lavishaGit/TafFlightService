FROM openjdk:17-jdk-slim
# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot JAR file to the container
# Replace `userms.jar` with the name of your JAR file
COPY build/libs/flights-0.0.1-SNAPSHOT.jar /app.jar

# Copy the configuration file (if separate from the application)
# Uncomment if needed
# COPY application.properties /app/application.properties

# Expose the port your application listens on
EXPOSE 8083
EXPOSE 3306
# Run the Spring Boot application
CMD ["java", "-jar", "/app.jar"]

