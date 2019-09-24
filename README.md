# DATABASE

## BUILD IMAGE
sudo docker build --build-arg INITDB_FILE="initdb.sh" -t otus-database .

## RUN CONTAINER
sudo docker run -v $(pwd)/persistence:/data/db -p 51001:27017 -e OTUS_PASS="Teste123" -e MONGO_INITDBROOT_USERNAME="admin" -e MONGO_INITDB_ROOT_PASSWORD="admin123" --name otus-database otus-database --wiredTigerCacheSizeGB='1.2'

# API

## BUILD IMAGE
sudo docker build --build-arg DEPLOY_USER="admin" --build-arg DEPLOY_PASS="admin" --build-arg EAR_FILE="otus-ear/target/otus-ear.ear" -t otus-api .

## RUN CONTAINER
sudo docker run -v $(pwd)/persistence:/opt/wildfly/standalone/log -e DATABASE_NAME="otus" -e DATABASE_HOST="localhost" -e DATABASE_PORT="51001" -e DATABASE_USER="otus" -e DATABASE_PASS="otus" -p 51002:8080 -p 51003:9990 --name otus-api otus-api
