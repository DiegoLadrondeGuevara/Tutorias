FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copiamos el jar generado por Maven al contenedor
COPY target/tutorias-simple-1.0-SNAPSHOT.jar app.jar

# Exponemos el puerto que usa tu aplicación
EXPOSE 8080

# Comando para correr la aplicación Java
ENTRYPOINT ["java", "-jar", "app.jar"]
