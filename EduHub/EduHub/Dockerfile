# Use OpenJDK base image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Install Maven inside the container (if not included in the base image)
RUN apt-get update && apt-get install -y maven

# Copy your source code into the container
COPY . .

# Build the project inside the container
RUN mvn clean package

# Expose the port your app will run on
EXPOSE 8080

# Run the JAR file
CMD ["java", "-jar", "target/your-app-name.jar"]

