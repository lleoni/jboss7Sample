#!/bin/bash
echo " ==========  Building ========="
pushd .
cd ../services
mvn -o clean install
cd ../beans
mvn -o clean install
popd
mvn clean package 


