#!/bin/bash
set -eu
version=$1
./gradlew clean -Pgroup=com.github.gesande -Pversion="$version" -xtest build publishToMavenLocal --warning-mode all
