#Usa la imagen oficial de OpenJDK con Java 17
FROM openjdk:17-jdk

#Establece el directorio de trabajo en /app
WORKDIR /app

#Copia el archivo JAR de la aplicación en el contenedor
COPY target/bicicletas.jar /app/bicicletas.jar

#Define el comando de inicio de la aplicación
ENTRYPOINT ["java", "-jar", "bicicletas.jar", "--spring.profiles.active=prod"]