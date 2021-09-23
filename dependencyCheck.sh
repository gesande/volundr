#!/bin/bash
./gradlew --no-daemon dependencyCheckAggregate -Dcom.sun.security.enableAIAcaIssuers=true --stacktrace
