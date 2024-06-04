# Obtener la dirección IP del nodo
NODE_IP=$(kubectl get nodes -o jsonpath='{.items[0].status.addresses[?(@.type=="InternalIP")].address}')

# Obtener el puerto del NodePort para el Ingress Controller
NODE_PORT=$(kubectl get service -n proyecto-final ingress-nginx-controller -o jsonpath='{.spec.ports[?(@.port==80)].nodePort}')

# Obtener los hosts del Ingress
HOSTS=$(kubectl get ingress proyecto-final-ingress -n proyecto-final -o jsonpath='{.spec.rules[*].host}' | tr ' ' ',')
 
# Imprimir el endpoint del Ingress
echo "El endpoint del Ingress es: http://$NODE_IP:$NODE_PORT"
echo "Hosts: $HOSTS"