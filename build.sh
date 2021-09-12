#!/usr/bin/env bash

working_dir=$(pwd)
echo "working directory: $working_dir"

cd ./rest-api
./dockerize.sh

cd -

cd ./rest-jwt
./dockerize.sh

