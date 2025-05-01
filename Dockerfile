# Immagine base con Java 17 e Maven
FROM maven:3.9-eclipse-temurin-17 as builder

# Directory di lavoro
WORKDIR /app

# Copia pom.xml e file di configurazione Maven
COPY pom.xml ./
COPY .mvn/ ./.mvn/

# Scarica le dipendenze
RUN mvn dependency:go-offline -B

# Copia il codice sorgente
COPY src/ ./src/

# Compila e crea il jar
RUN mvn package -DskipTests

# Immagine finale
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copia il jar dall'immagine builder
COPY --from=builder /app/target/*.jar app.jar

# Espone la porta 8080
EXPOSE 8080

# Variabile ambiente per OpenAI API key
ENV OPENAI_API_KEY=""

# Comando per avviare l'applicazione
ENTRYPOINT ["java", "-jar", "app.jar"]