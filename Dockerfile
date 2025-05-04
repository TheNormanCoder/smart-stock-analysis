# Fase di build
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /app

COPY pom.xml ./
COPY .mvn/ .mvn
COPY src/ ./src/

RUN mvn clean package -DskipTests

# Fase di esecuzione
FROM eclipse-temurin:17-jre

RUN apt-get update && apt-get install -y \
    libstdc++6 \
    libgfortran5 \
    libquadmath0 \
    libopenblas0 \
    wget \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
