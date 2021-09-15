#!/bin/bash
./gradlew -Dorg.gradle.parallel=true build test --info
./gradlew -Dorg.gradle.parallel=false aggregateTestReport jacocoTestReport jacocoAggregateReport archiveAggregateReports
