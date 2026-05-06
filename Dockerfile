# Stage 1: Build the application using Maven
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app

# Copy the wrapper and pom.xml first to download dependencies
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
# Ensure mvnw has execution permissions
RUN chmod +x mvnw
# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy the source code and build
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Stage 2: Create a lightweight image for running the app
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copy the JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the app with the 'deploy' profile active
ENTRYPOINT ["java", "-Dspring.profiles.active=deploy", "-jar", "app.jar"]
