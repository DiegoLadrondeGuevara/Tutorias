FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY target/tutorias-simple-1.0-SNAPSHOT.jar app.jar

EXPOSE 4567

ENTRYPOINT ["java", "-jar", "app.jar"]
