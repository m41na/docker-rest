#!/usr/bin/env bash

./gradlew clean build

docker build -t rest-api-app .

