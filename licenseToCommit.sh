#!/bin/bash
set -eu
./gradlew spotlessCheck
./gradlew -Dorg.gradle.parallel=true build test --info
./gradlew -Dorg.gradle.parallel=false aggregateTestReport jacocoTestReport jacocoAggregateReport
./gradlew -Dorg.gradle.parallel=false --info dependencyUpdates -DoutputFormatter=html
./gradlew -Dorg.gradle.parallel=false --info dependencyCheckAggregate -Dcom.sun.security.enableAIAcaIssuers=true
./gradlew -Dorg.gradle.parallel=false archiveAggregateReports
