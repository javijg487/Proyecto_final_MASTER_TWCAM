#Usa la imagen oficial de OpenJDK con Java 17
FROM openjdk:17-jdk

#Establece el directorio de trabajo en /app
WORKDIR /app

#Copia el archivo JAR de la aplicación en el contenedor
COPY target/ayuntamiento.jar /app/ayuntamiento.jar

#Define el comando de inicio de la aplicación
ENTRYPOINT ["java", "-jar", "ayuntamiento.jar", "--spring.profiles.active=prod"]