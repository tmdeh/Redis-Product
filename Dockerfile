# Base image
FROM openjdk:17-jdk-alpine

# Work directory
WORKDIR /app

# Copy JAR file
COPY build/libs/RedisProduct-0.0.1-SNAPSHOT.jar /app/redis-product.jar

# Expose port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "redis-product.jar", "--spring.profiles.active=prod"]
