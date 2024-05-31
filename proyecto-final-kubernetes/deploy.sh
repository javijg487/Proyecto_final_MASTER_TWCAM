#!/bin/bash

# Aplicar Namespace si existe
if [ -f Namespace.yml ]; then
    kubectl apply -f Namespace.yml
fi

# Aplicar PersistentVolumes
for pv in $(find . -name "*-Pv.yml"); do
    kubectl apply -f "$pv"
done

# Aplicar PersistentVolumeClaims
for pvc in $(find . -name "*-Pvc.yml"); do
    kubectl apply -f "$pvc"
done

# Aplicar Deployments
for deployment in $(find . -name "*-deployment.yml"); do
    kubectl apply -f "$deployment"
done

# Aplicar Services
for service in $(find . -name "*-service.yml"); do
    kubectl apply -f "$service"
done
# Aplicar Ingress (si existe)
if [ -f Ingress-nginx.yml ]; then
    kubectl apply -f Ingress-nginx.yml
fi

sleep 15

# Aplicar Ingress (si existe)
if [ -f Ingress.yml ]; then
    kubectl apply -f Ingress.yml
fi

if [ -f CronJob.yml ]; then
    kubectl apply -f CronJob.yml
fi

echo "Despliegue completado."
