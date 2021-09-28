# How to configure and run

## Assumptions
1. Your system can execute a bash script

## Required software for integration testing
1. Docker engine
2. Docker compose
3. psql client
4. jq (for parsing json content)
5. jdk 11 (since the API is written using kotlin)

To run the integration tests, simply execute the following (from the project's root folder)

_./integration-test.sh_

## For local development in your IDE
1. Add the following to your run configuration
   1. VM arguments
      1. -Dspring.profiles.active=local
   2. Environment variables
      1. DB_USER=userhours_user;
      2. DB_PASS=Super-e3cret;
2. To deploy the application using docker, run
   1. docker-compose --env-file ./.env.dev up

## Docker components
There are three containers defined in the docker-compose file
1. db - the postgres server
2. rest - the REST application (port 3000)
3. haproxy - the REST proxy (port 3001)

You can use either port 3000 and 3001 to interact with the REST api application

## From lack of specificity
1. I included the haproxy container as a placeholder for concerns like TLS and load-balancing
2. I did not attempt to use any particular method here to obscure plain-text secrets

## Summary of available endpoints
The REST api application ships with Swagger documentation which can be reached through

_http://localhost:3000/v2/api-docs_

The documentation UI is reachable through

_http://localhost:3000/swagger-ui.html_

The high level summary of the endpoints is as shown below

**RestJwtController**
1. GET /v1/jwt/health
2. POST /v1/jwt/authenticate
3. POST /v1/jwt/validate

**RestApiController**
1. GET /v1/users
2. GET /v1/users/{userId}/worked_hours
3. POST /v1/users/{userId}/worked_hours

## Accessing endpoints
There are three users in the mock database you can use to generate a JWT token (see UserJwtService)
"user" with password "user_pass",
"admin" with password "admin_pass",
"guest" with password "guest_pass"

The **RestApiController** endpoints require a JWT token. To generate one, use the _**/v1/jwt/authorize**_ endpoint

Add the JWT token as a header parameter to your request

--header 'Authorization: Bearer <jwt>

