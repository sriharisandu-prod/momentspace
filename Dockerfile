FROM eclipse-temurin:23-jdk

WORKDIR /app

# Copy project files
COPY . .

# Give mvnw permission to run
RUN chmod +x mvnw

# Build the JAR inside the container
RUN ./mvnw clean package -DskipTests

# Run the JAR
ENTRYPOINT ["java","-jar","target/memorieshub-0.0.1-SNAPSHOT.jar"]


