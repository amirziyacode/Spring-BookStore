# Use an official Java 17 image
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy everything to the container
COPY . .

# Build the application
RUN ./mvnw package -DskipTests

# Run the application
CMD ["java", "-jar", "target/Book-Store-App-0.0.1-SNAPSHOT.jar"]
