FROM jboss/base-jdk:8

ADD target/rh-sso-service-broker-swarm.jar /opt/jboss/rh-sso-service-broker-swarm.jar
COPY audit.log /opt/jboss/audit.log
CMD ["/usr/bin/java", "-jar", "/opt/jboss/rh-sso-service-broker-swarm.jar"]
