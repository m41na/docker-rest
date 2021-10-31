#Setup necessary

## These resources should be created manually before deploying the other kubernetes resources

kubectl create secret generic pg-port --from-literal POSTGRES_PORT=5432
kubectl create secret generic pg-host --from-literal POSTGRES_HOST=localhost
kubectl create secret generic pg-hostname --from-literal POSTGRES_HOSTNAME=pghost
kubectl create secret generic pg-database --from-literal POSTGRES_DB=userhours_dev
kubectl create secret generic pg-username --from-literal POSTGRES_USER=userhours_user
kubectl create secret generic pg-password --from-literal POSTGRES_PASSWORD=Super-e3cret

kubectl create configmap pg-scripts-config --from-file=sql-k8

docker-entrypoint-initdb.d

kubectl exec -it postgres-deployment-7df94b9987-z9wzd bash
