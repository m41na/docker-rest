FROM openjdk:11

WORKDIR /usr/local

COPY build/libs/docker-rest-0.0.1-SNAPSHOT.jar ./rest-api-app.jar

EXPOSE 3000

USER 1001

ENTRYPOINT java -jar \
    -Dspring.profiles.active=default \
    -DDB_PASS=${POSTGRES_PASSWORD} \
    -DDB_USER=${POSTGRES_USER} \
    -DDB_HOST=${POSTGRES_HOSTNAME} \
    -DDB_PORT=${POSTGRES_PORT} \
    -DDB_DATABASE=${POSTGRES_DB} \
    rest-api-app.jar