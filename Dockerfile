FROM openjdk:8u111-jdk-alpine
VOLUME /tmp
ADD ./target/intact-search-interactors-ws-1.0.3-SNAPSHOT.jar intact-search-interactors-ws.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/intact-search-interactors-ws.jar"]