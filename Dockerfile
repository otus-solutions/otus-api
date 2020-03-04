FROM jboss/wildfly:14.0.1.Final AS api

ARG DEPLOY_USER=admin
ARG DEPLOY_PASS=admin
ARG EAR_FILE=source/otus-ear/target/otus-ear.ear

ENV DATABASE_NAME "otus"
ENV DATABASE_HOST "otus-database"
ENV DATABASE_PORT "27017"
ENV DATABASE_USER "otus"
ENV DATABASE_PASS "otus"
ENV DBDISTRIBUTION_PORT "8080"
ENV DBDISTRIBUTION_HOST "db-distribution-service"
ENV OUTCOMES_HOST "outcomes-service"
ENV OUTCOMES_PORT "8080"
ENV DCM_HOST "dcm-service"
ENV DCM_PORT "8080"
ENV COMMUNICATION_HOST "communication-service"
ENV COMMUNICATION_PORT "8080"

ENV DEBUG_MODE "false"
ENV JVM_MEMORY "3G"
USER root

ADD server/api/standalone-custom.xml /opt/jboss/wildfly/standalone/configuration/

ADD server/api/standalone.sh /opt/jboss/wildfly/bin/
ADD ${EAR_FILE} /opt/jboss/wildfly/standalone/deployments/
RUN /opt/jboss/wildfly/bin/add-user.sh ${DEPLOY_USER} ${DEPLOY_PASS} --silent
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-c", "standalone-custom.xml", "-b", "0.0.0.0", "-bmanagement", "127.0.0.1"]

EXPOSE 8080 9990 8787
