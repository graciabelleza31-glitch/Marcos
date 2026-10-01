# Etapa 1: Compilar la aplicación con Maven
FROM maven:3.9.8-eclipse-temurin-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Crear la imagen final, más ligera
FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/proyecto-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]