#!/bin/bash
./gradlew -Dorg.gradle.parallel=true build test --info
./gradlew aggregateTestReport
./gradlew jacocoTestReport jacocoAggregateReport
./gradlew archiveAggregateReports
