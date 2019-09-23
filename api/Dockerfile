FROM jboss/wildfly:14.0.1.Final

ARG DEPLOY_USER=admin
ARG DEPLOY_PASS=admin
ARG EAR_FILE=otus-ear/target/otus-ear.ear

ENV DATABASE_NAME="otus"
ENV DATABASE_HOST="localhost"
ENV DATABASE_PORT="51001"
ENV DATABASE_USER="otus"
ENV DATABASE_PWD="otus"

USER root

ADD standalone-custom.xml /opt/jboss/wildfly/standalone/configuration/
ADD ${EAR_FILE} /opt/jboss/wildfly/standalone/deployments/
RUN /opt/jboss/wildfly/bin/add-user.sh ${DEPLOY_USER} ${DEPLOY_PASS} --silent
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-c", "standalone-custom.xml", "-b", "0.0.0.0", "-bmanagement", "127.0.0.1"]
EXPOSE 8080 9990
