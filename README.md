# NETWORK
sudo docker network create -d bridge otus-platform-network

# DATABASE - BUILD IMAGE
sudo docker build --target database -t otus-database .

# DATABASE - BUILD CONTAINER
sudo docker run -v $(pwd)/persistence:/data/db --network=otus-platform-network -p 51001:27017 --name otus-database otus-database --wiredTigerCacheSizeGB='1.2'

# API - BUILD IMAGE
sudo docker build --target api -t otus-api .

# API - BUILD CONTAINER
sudo docker run -v $(pwd)/persistence:/opt/wildfly/standalone/log --network=otus-platform-network -p 51002:8080 -p 51003:9990 --name otus-api otus-api
