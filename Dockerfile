# Use Eclipse Temurin JDK 23 (official OpenJDK builds)
FROM eclipse-temurin:23-jdk

WORKDIR /app

# Copy Maven wrapper and project files
COPY . .

# Build the JAR inside the container
RUN ./mvnw clean package -DskipTests

# Run the JAR
ENTRYPOINT ["java","-jar","target/memorieshub-0.0.1-SNAPSHOT.jar"]

