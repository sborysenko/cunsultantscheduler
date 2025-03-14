# Stage 1: Build Vite Frontend
FROM node:18 AS build-frontend
WORKDIR /app
COPY frontend/ .
RUN npm install && npm run build

# Stage 2: Build Spring Boot Backend
FROM maven:3.9.9-eclipse-temurin-23 AS build-backend
WORKDIR /app
COPY backend/ .
COPY --from=build-frontend /app/dist/ src/main/resources/static/
RUN mvn clean package -DskipTests

# Stage 3: Create the Final Docker Image
FROM openjdk:23-jdk
WORKDIR /app
COPY --from=build-backend /app/target/cunsultantscheduler-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
