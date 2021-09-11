FROM openjdk:11

ENV DB_PASS=Super-e3cret
ENV DB_USER=userhours_user
ENV DDB_HOST=take-home-db
ENV DDB_PORT=5432
ENV DDB_DATABASE=userhours_dev

WORKDIR /usr/local

COPY build/libs/rest-api-0.0.1-SNAPSHOT.jar .

ENTRYPOINT java -jar \
    -Dspring.profiles.active=dev \
    -DDB_PASS=${DB_PASS} \
    -DDB_USER=${DB_USER} \
    -DDB_HOST=${DB_HOST} \
    -DDB_PORT=${DB_PORT} \
    -DDB_DATABASE=${DB_DATABASE} \
    rest-api-0.0.1-SNAPSHOT.jar