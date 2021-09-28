#!/usr/bin/env bash

export PGPASSWORD=Super-e3cret

function await_running_state {
    local attempts=5
    local container_name="$1"
    echo "awaiting container to be in a running state"
    while (( --attempts >= 0 )); do
      sleep 3
      if [ "$(docker container inspect -f '{{.State.Status}}' "$container_name" )" == "running" ]; then
        echo "$container_name container is now running"
        break
      fi

      if (( attempts < 0 )); then
       echo "checking running status - permissible attempts exceeded"
       exit 1
      fi
    done
}

function retrieve_auth_token {
  local attempts=5
  local tokenResponse
  local token
  while (( --attempts >= 0 )); do
    tokenResponse="$(curl --location --request POST 'http://localhost:3000/v1/jwt/authenticate' \
             --header 'Content-Type: application/json' \
             --data-raw '{
                 "username": "user",
                 "password": "user_pass"
             }')"

    if [[ -z $tokenResponse ]]; then
      sleep 3
      continue;
    else
      token=$(echo "$tokenResponse" | jq .token | cut -d'"' -f 2)
      echo "$token"
      break;
    fi
  done
}

function validate_auth_token {
  local attempts=5
  local validateResponse
  local valid
  while (( --attempts >= 0 )); do
    validateResponse="$(curl --location --request POST 'http://localhost:3000/v1/jwt/validate' \
             --header 'Content-Type: text/plain' \
             --data-raw "$1"
             )"

    if [[ -z $validateResponse ]]; then
      sleep 3
      continue
    else
      valid=$(echo "$validateResponse" | jq '.valid')
      echo "$valid"
      break
    fi
  done
}

function hours_worked {
  local attempts=5
  while (( --attempts >= 0 )); do
    local result
    local auth_header="Authorization: Bearer $1"
    result=$(curl --location --request GET 'http://localhost:3000/v1/users/1/worked_hours' --header "${auth_header}")

    if [[ -z $result ]]; then
      sleep 3
      continue
    else
      echo "$result"
      break
    fi
  done
}

function all_users {
  local attempts=5
  while (( --attempts >= 0 )); do
    local result
    local auth_header="Authorization: Bearer $1"
    result=$(curl --location --request GET 'http://localhost:3000/v1/users' --header "${auth_header}")

    if [[ -z $result ]]; then
      sleep 3
      continue
    else
      echo "$result"
      break
    fi
  done
}

function updated_hours {
  local attempts=5
  while (( --attempts >= 0 )); do
    local result
    local auth_header="Authorization: Bearer $1"
    result=$(curl --location --request POST 'http://localhost:3000/v1/users/1/worked_hours' \
              --header "${auth_header}" \
              --header 'Content-Type: application/json' \
              --data-raw '{"date": "2021-01-11","hours":5.24}')

    if [[ -z $result ]]; then
      sleep 3
      continue
    else
      echo "$result"
      break
    fi
  done
}

function assert_equals {
  local expected=$1
  local actual=$2
  if [[ "$actual" != "$expected" ]]; then
    echo "Failed: Expected $expected but found $actual"
  else
    echo "Passed: Expected $expected"
  fi
}

#checking tools needs
jq_version=$(jq --version)

if [[ $jq_version != jq-* ]]; then
  echo "you need to install jq first"
  exit 1
fi

echo "found jq version $jq_version"

echo "stopping any containers that may be running"
docker-compose down

echo "starting pg database container"
docker-compose up -d --remove-orphan db
pg_container_name=take-home-db
await_running_state "$pg_container_name"

echo "connect to pg container and execute initialization script"
pg_host_ip="$(docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' $pg_container_name)"
echo "ph host is: $pg_host_ip"

psql -h "$pg_host_ip" -p 5432 -d userhours_dev -U userhours_user -f sql/schema.sql
psql -h "$pg_host_ip" -p 5432 -d userhours_dev -U userhours_user -f sql/data.sql

echo "database primed and ready"

echo "creating docker image for rest api app"
./dockerize.sh

echo "starting rest api app container"
docker-compose --env-file ./.env.dev up -d rest
await_running_state rest-api-app

echo
echo "retrieve auth token"
token=$(retrieve_auth_token)
echo "token retrieved is $token"

echo
echo "validate auth token"
valid=$(validate_auth_token "$token")

if [[ $valid != true ]]; then
  echo "token is not valid"
  exit 1
fi

echo
echo "checking hours worked"
hoursWorked=$(hours_worked "$token")

if [[ -z $hoursWorked ]]; then
  echo "hours data is not available"
  exit 1
fi

size=$(echo "$hoursWorked" | jq '.data | length')

#assert expected value
assert_equals 6 "$size"

echo
echo "checking number of users"
allUsers=$(all_users "$token")

if [[ -z $allUsers ]]; then
  echo "users data is not available"
  exit 1
fi

size=$(echo "$allUsers" | jq '.data | length')

#assert expected value
assert_equals 10 "$size"

echo
echo 'checking updating hours worked'
updateHours=$(updated_hours "$token")

if [[ -z $updateHours ]]; then
  echo "user hours update result is not available"
  exit 1
fi

status=$(echo "$updateHours" | jq '.data')

#assert expected value
assert_equals 1 "$status"

echo
echo "test completed. stopping all containers"

docker-compose down