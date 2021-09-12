#!/usr/bin/env bash

./gradlew --info clean build

docker build -t rest-api-app .

