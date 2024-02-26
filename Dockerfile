# Usa una imagen base de Debian o Ubuntu
FROM openjdk:17-slim
ADD ./panaderia-0.0.1-SNAPSHOT.jar /java-app.jar
# Configura el punto de entrada para ejecutar tu aplicación Java
ENTRYPOINT ["java", "-jar", "/java-app.jar"]
