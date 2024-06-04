# Obtener el puerto
PORT=$(kubectl get svc -n proyecto-final ingress-nginx-controller -o jsonpath='{.spec.ports[?(@.port==80)].nodePort}')
TOKEN=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiaXNzIjoiYXV0aC1zZXJ2ZXIiLCJleHAiOjE3MTgzNjk0MDd9._IP7uqM4OVKppA6T4Fov0onqHlD3bBADazTHwqz0bAE
# Host: bicicletas.proyecto.final.com
echo -e "Request to /api/v1/aparcamientos"
curl -H "Host: bicicletas.proyecto.final.com" http://192.168.56.3:$PORT/api/v1/aparcamientos
echo -e ""

echo -e "Request to /api/v1/aparcamiento/2/status"
curl -H "Host: bicicletas.proyecto.final.com" http://192.168.56.3:$PORT/api/v1/aparcamiento/2/status
echo -e ""

# Host: polucion.proyecto.final.com
echo -e "Request to /api/v1/estaciones"
curl -H "Host: polucion.proyecto.final.com" http://192.168.56.3:$PORT/api/v1/estaciones
echo -e""

echo -e "Request to /api/v1/estacion/3/status"
curl -H "Host: polucion.proyecto.final.com" "http://192.168.56.3:$PORT/api/v1/estacion/3/status?from=2020-03-23T17:00:45.000Z&to=2025-04-23T17:00:45.000Z"
echo -e ""

# Host: ayuntamiento.proyecto.final.com
echo -e "Request to /api/v1/aparcamientoCercano"
curl -H "Host: ayuntamiento.proyecto.final.com" "http://192.168.56.3:$PORT/api/v1/aparcamientoCercano?lat=37.71&lon=-3.00"
echo -e ""

echo -e "Request to /api/v1/aggregateData"
curl -X GET -H "Host: ayuntamiento.proyecto.final.com" -H "Authorization: Bearer $TOKEN" http://192.168.56.3:$PORT/api/v1/aggregateData
echo -e ""

echo -e "Request to /api/v1/aggregatedData"
curl -H "Host: ayuntamiento.proyecto.final.com" http://192.168.56.3:$PORT/api/v1/aggregatedData
echo -e ""

# Host: autenticacion.proyecto.final.com
echo -e "Request to /api/v1/login"
curl -i -H "Host: autenticacion.proyecto.final.com" -F "username=admin" -F "password=1234" http://192.168.56.3:$PORT/api/v1/login
