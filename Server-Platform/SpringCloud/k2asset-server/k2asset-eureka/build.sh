#!/usr/bin/env bash

mvn clean

mvn package -Dmaven.test.skip=true

mv ./target/k2Asset-eureka-1.0-SNAPSHOT.jar ./target/k2Asset-eureka.jar

echo "Build success. target: ./target/k2Asset-eureka.jar"