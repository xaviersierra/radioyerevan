#!/usr/bin/env bash
optspec=":hv-:"
RADIO_YEREVAN_VERSION=$( mvn -q -Dexec.executable=echo -Dexec.args='${project.version}' --non-recursive exec:exec )
SOURCE_PATH=$(pwd)

while getopts "$optspec" optchar; do
  case "${optchar}" in
    -)
      case "${OPTARG}" in
        sourcepath)
          val="${!OPTIND}"; OPTIND=$(( $OPTIND + 1 ))
          SOURCE_PATH=${val}
          ;;
        *)
          if [ "$OPTERR" = 1 ] && [ "${optspec:0:1}" != ":" ]; then
              echo "Unknown option --${OPTARG}" >&2
          fi
          ;;
      esac;;
    *)
      if [ "$OPTERR" != 1 ] || [ "${optspec:0:1}" = ":" ]; then
          echo "Non-option argument: '-${OPTARG}'" >&2
      fi
      ;;
  esac
done
echo "Building native image builder"
docker build --no-cache -t radio-yerevan-builder .
echo "Compiling radioyerevan version $RADIO_YEREVAN_VERSION"
docker run --rm -v "$SOURCE_PATH:/radioyerevan" radio-yerevan-builder mvn clean package

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

