FROM jboss/wildfly:14.0.1.Final

ARG DEPLOY_USER=admin
ARG DEPLOY_PASS=admin
ARG BIND_IP=0.0.0.0
ARG BIND_IP_CONSOLE=0.0.0.0
ARG EAR_FILE=otus-ear/target/otus-ear.ear
ARG CUSTOM_STANDALONE=standalone-custom.xml

ENV ENV_BIND_IP=${BIND_IP}
ENV ENV_BIND_IP_CONSOLE=${BIND_IP_CONSOLE}

USER root

ADD ${EAR_FILE} /opt/jboss/wildfly/standalone/deployments/
RUN /opt/jboss/wildfly/bin/add-user.sh ${DEPLOY_USER} ${DEPLOY_PASS} --silent
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]

EXPOSE 8080 9990
