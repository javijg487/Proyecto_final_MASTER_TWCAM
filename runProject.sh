#!/bin/bash

# Ejecuta docker-compose up en segundo plano
docker-compose up -d

# Función para verificar y listar los contenedores que están en funcionamiento
containers_up() {
  docker-compose ps | grep "Up"
}

# Espera a que todos los contenedores se inicien
echo "Esperando a que los contenedores de Docker se inicien..."

# Variable para controlar el tiempo de espera
max_wait_time=60  # Tiempo máximo de espera en segundos
waited=0
interval=5  # Intervalo de verificación en segundos

while [[ $waited -lt $max_wait_time ]]; do
  up_containers=$(containers_up)
  if [[ -n "$up_containers" ]]; then
    echo "Los siguientes contenedores están en funcionamiento:"
    echo "$up_containers"
    break
  else
    echo "Esperando a que los contenedores se inicien..."
    sleep $interval
    waited=$((waited + interval))
  fi
done

if [[ $waited -ge $max_wait_time ]]; then
  echo "Tiempo de espera agotado. Algunos contenedores no se iniciaron correctamente."
fi


# Ejecuta la aplicación Java
# Polucion
java -jar ./polucionsql/target/polucionsql.jar --spring.profiles.active=local
java -jar ./polucionmongo/target/polucionmongo.jar --spring.profiles.active=local
java -jar ./polucion/target/polucion.jar --spring.profiles.active=local

# Bicicletas
java -jar ./bicicletassql/target/bicicletassql.jar --spring.profiles.active=local
java -jar ./bicicletamongo/target/bicicletasmongo.jar --spring.profiles.active=local
java -jar ./bicicletas/target/bicicletas.jar --spring.profiles.active=local

# Ayuntamiento
java -jar ./ayuntamientomongo/target/ayuntamientomongo.jar --spring.profiles.active=local
java -jar ./ayuntamiento/target/ayuntamiento.jar --spring.profiles.active=local

# Autenticacion
java -jar ./autenticacion/target/autenticacion.jar --spring.profiles.active=local

# Servicio
java -jar ./autenticacion/target/service.jar 