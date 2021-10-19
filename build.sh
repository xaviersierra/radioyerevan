#!/bin/sh
RADIO_YEREVAN_VERSION=$( mvn -q -Dexec.executable=echo -Dexec.args='${project.version}' --non-recursive exec:exec )

echo "Building native image builder"
docker build -t radio-yerevan-builder .
echo "Compiling radioyerevan version $RADIO_YEREVAN_VERSION"
docker run --rm -v "D:/workspaces/radioyerevan:/radioyerevan" radio-yerevan-builder mvn clean package

echo "Building postgres migrator docker image"
docker build \
  -t "xavsierra/radioyerevan-pg-migrator:dev" \
  -f postgres-migrator/src/main/docker/Dockerfile \
  .

echo "Building joke publisher docker image"
docker build \
  -t "xavsierra/radioyerevan-joke-publisher:dev" \
  -f joke-publisher/src/main/docker/Dockerfile \
  .

