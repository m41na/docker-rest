FROM openjdk:11

WORKDIR /usr/local

COPY build/libs/rest-api-0.0.1-SNAPSHOT.jar .

EXPOSE 3000

ENTRYPOINT java -jar \
    -Dspring.profiles.active=default \
    -DDB_PASS=${POSTGRES_PASSWORD} \
    -DDB_USER=${POSTGRES_USER} \
    -DDB_HOST=${POSTGRES_HOST} \
    -DDB_PORT=${POSTGRES_PORT} \
    -DDB_DATABASE=${POSTGRES_DB} \
    -DJWT_AUTH_CLIENT=${JWT_AUTH_CLIENT} \
    rest-api-0.0.1-SNAPSHOT.jar