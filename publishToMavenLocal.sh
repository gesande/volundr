#!/bin/bash
set -eu
version=$1
gradle clean -Pgroup=com.github.gesande -Pversion=$version -xtest build publishToMavenLocal
