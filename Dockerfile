# Use Eclipse Temurin JDK 23 (official OpenJDK builds)
FROM eclipse-temurin:23-jdk

WORKDIR /app

# Copy your built JAR into the container
COPY target/memorieshub-0.0.1-SNAPSHOT.jar app.jar

# Run the JAR
ENTRYPOINT ["java","-jar","app.jar"]
