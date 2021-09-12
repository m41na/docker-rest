FROM openjdk:11

WORKDIR /usr/local

COPY build/libs/rest-jwt-0.0.1-SNAPSHOT.jar .

EXPOSE 3000

ENTRYPOINT java -jar \
    -Dspring.profiles.active=default \
    rest-jwt-0.0.1-SNAPSHOT.jar