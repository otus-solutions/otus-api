# API

## BUILD IMAGE
sudo docker build --build-arg DEPLOY_USER=user DEPLOY_PASS=pass -t otus-api .

## RUN CONTAINER
sudo docker run -e DATABASE_NAME='otus' -e DATABASE_HOST='api-otus.dev.ccem.ufrgs.br' -e DATABASE_PORT='51001' -e DATABASE_USER='user' -e DATABASE_PASS='pass' -p 9990:9990 -p 8080:8080 otus-api

# DATABASE

## BUILD IMAGE
sudo docker build --build-arg INITDB_FILE='initdb.sh' -t otus-database .

## RUN CONTAINER
sudo docker run -v $(pwd)/persistence:/data/db -p 51002:27017 --name otus-database otus-database --wiredTigerCacheSizeGB='1.2'
