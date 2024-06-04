#!/bin/bash
# Despliega aplicacion con proyecto JAR
# Compila los proyectos Java
# Polucion
java -jar ./polucionsql/target/polucionsql.jar --spring.profiles.active=local &
java -jar ./polucionmongo/target/polucionmongo.jar --spring.profiles.active=local &
java -jar ./polucion/target/polucion.jar --spring.profiles.active=local &

# Bicicletas
java -jar ./bicicletassql/target/bicicletassql.jar --spring.profiles.active=local &
java -jar ./bicicletasmongo/target/bicicletasmongo.jar --spring.profiles.active=local &
java -jar ./bicicletas/target/bicicletas.jar --spring.profiles.active=local &

# Ayuntamiento
java -jar ./ayuntamientomongo/target/ayuntamientomongo.jar --spring.profiles.active=local &
java -jar ./ayuntamiento/target/ayuntamiento.jar --spring.profiles.active=local &

# Autenticacion
java -jar ./autenticacion/target/autenticacion.jar --spring.profiles.active=local &

# Servicio
java -jar ./service/target/service.jar &