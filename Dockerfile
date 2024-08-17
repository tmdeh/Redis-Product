# Base image
FROM openjdk:17-jdk-alpine

# Work directory
WORKDIR /app

# Copy JAR file
COPY target/redis-product.jar /app/redis-prduct.jar

# Expose port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "redis-prduct.jar"]
