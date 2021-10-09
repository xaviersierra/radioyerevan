#!/usr/bin/env sh
RADIO_YEREVAN_VERSION=$(mvn -q \
  -Dexec.executable=echo \
  -Dexec.args='${project.version}' \
  --non-recursive \
  exec:exec)

echo "Compiling radio yerevan version $RADIO_YEREVAN_VERSION"
mvn clean package

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

