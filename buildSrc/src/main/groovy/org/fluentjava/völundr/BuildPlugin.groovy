package org.fluentjava.völundr;

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.GradleBuild

public class BuildPlugin implements Plugin<Project> {

    @Override
    public void apply(final Project project) {
        project.task("distributionPackage", type: GradleBuild, dependsOn: ['build']) { Task task ->
            group = 'Distribution'
            description = 'Distribution package for the whole thing including continous build.'
            buildFile = 'build.gradle'
            tasks = [
                'concurrent:clean',
                'stronglytyped-sortedbag:clean',
                'junit-utils:clean',
                'classhelper:clean',
                'linereader:clean',
                'fileutil:clean',
                'asynchronous-stream-reader:clean',
                'stream-reader:clean',
                'string-to-outputstream:clean',
                'inputstream-to-string:clean',
                'visiting-inputstreams:clean',
                'string-to-bytes:clean',
                'string-to-inputstream:clean',
                'völundr-smithy:clean',
                'statistics:clean',
                'string-splitter:clean',
                'bytes-to-string:clean',
                'as-expected:clean',
                'statistics-valueprovider:clean',
                'graph-jfreechart:clean',
                'graph-jfreechart-api:clean',
                'concurrent:release',
                'stronglytyped-sortedbag:release',
                'junit-utils:release',
                'classhelper:release',
                'linereader:release',
                'fileutil:release',
                'asynchronous-stream-reader:release',
                'stream-reader:release',
                'string-to-outputstream:release',
                'inputstream-to-string:release',
                'visiting-inputstreams:release',
                'string-to-bytes:release',
                'string-to-inputstream:release',
                'völundr-smithy:release',
                'statistics:release',
                'string-splitter:release',
                'bytes-to-string:release',
                'as-expected:release',
                'statistics-valueprovider:release',
                'graph-jfreechart:release',
                'graph-jfreechart-api:release',
                'völundr-osmo-tester:release',
                'osmo-testing-statistics:release',
                'makeDistributionPackage'
            ]

            doLast { println "Distribution package ready to be uploaded to the repository of your choosing." }
        }
    }
}
