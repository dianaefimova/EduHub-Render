# Use an OpenJDK base image
FROM openjdk:17-jdk-slim

# Author information (optional)
LABEL authors="diana.efimova"

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build context into the image
COPY target/*.jar app.jar

# Expose the port your app will run on
EXPOSE 8080

# Set the command to run the JAR file
CMD ["java", "-jar", "app.jar"]
