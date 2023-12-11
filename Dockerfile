FROM openjdk:11-jdk-stretch
VOLUME /tmp
ADD /target/intact-search-interactors-ws.jar intact-search-interactors-ws.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/intact-search-interactors-ws.jar"]