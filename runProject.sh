#!/bin/bash

# Compila y ejecuta los proyectos Java
# Polucion
cd polucionsql 
mvn spring-boot:run -Dactive.profile=local &

cd ../polucionmongo 

mvn spring-boot:run -Dactive.profile=local &
cd ../polucion && mvn spring-boot:run -Dactive.profile=local &

# Bicicletas
cd ../bicicletassql && mvn spring-boot:run -Dactive.profile=local &
cd ../bicicletasmongo && mvn spring-boot:run -Dactive.profile=local &
cd ../bicicletas && mvn spring-boot:run -Dactive.profile=local &

# Ayuntamiento
cd ../ayuntamientomongo && mvn spring-boot:run -Dactive.profile=local &
cd ../ayuntamiento && mvn spring-boot:run -Dactive.profile=local &

# Autenticacion
cd ../autenticacion && mvn spring-boot:run -Dactive.profile=local &

# Servicio
cd ../service && mvn spring-boot:run &
