FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/Book-Store-App-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]