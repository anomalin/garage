#!/bin/bash

echo "Packaging..."
mvn package
echo "Running the application starting localhost..."
mvn spring-boot:run
