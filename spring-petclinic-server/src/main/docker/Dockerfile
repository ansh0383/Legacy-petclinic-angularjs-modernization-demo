FROM eclipse-temurin:17-jre
LABEL maintainer="Antoine Rey <antoine.rey@free.fr>"
# Spring Boot application creates working directories for Tomcat by default
VOLUME /tmp
ADD petclinic.jar petclinic.jar
RUN sh -c 'touch /petclinic.jar'
ENTRYPOINT ["java","-jar","/petclinic.jar"]
