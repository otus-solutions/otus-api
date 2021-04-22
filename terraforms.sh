#!/bin/sh
set -e

rm terraform.tfstate && rm terraform.tfstate.backup &&
terraform init terraform/build-image &&
terraform apply --auto-approve --var='otus-api-mvnbuild=mvn clean install -DskipTests' terraform/build-image &&
terraform init terraform/run-container &&
terraform apply --auto-approve terraform/run-container
