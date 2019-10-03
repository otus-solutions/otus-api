FROM mongo:4.0.4 AS database

ARG INITDB_FILE=server/database/initdb.sh

ENV OTUS_USER="otus"
ENV OTUS_PASS="otus"

ENV DOMAIN_USER="domain"
ENV DOMAIN_PASS="domain"

ENV MONGO_INITDB_ROOT_USERNAME="admin"
ENV MONGO_INITDB_ROOT_PASSWORD="admin"
ENV MONGO_INITDB_DATABASE="admin"

USER root

COPY server/database/initdb /initdb
COPY ${INITDB_FILE} /docker-entrypoint-initdb.d/

EXPOSE 27017

FROM jboss/wildfly:14.0.1.Final AS api

ARG DEPLOY_USER=admin
ARG DEPLOY_PASS=admin
ARG EAR_FILE=source/otus-ear/target/otus-ear.ear

ENV DATABASE_NAME="otus"
ENV DATABASE_HOST="otus-database"
ENV DATABASE_PORT="27017"
ENV DATABASE_USER="otus"
ENV DATABASE_PASS="otus"

USER root

ADD server/api/standalone-custom.xml /opt/jboss/wildfly/standalone/configuration/
ADD ${EAR_FILE} /opt/jboss/wildfly/standalone/deployments/
RUN /opt/jboss/wildfly/bin/add-user.sh ${DEPLOY_USER} ${DEPLOY_PASS} --silent
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-c", "standalone-custom.xml", "-b", "0.0.0.0", "-bmanagement", "127.0.0.1"]
EXPOSE 8080 9990
