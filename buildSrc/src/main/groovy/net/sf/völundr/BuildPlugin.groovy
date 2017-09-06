package net.sf.völundr;

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.GradleBuild

public class BuildPlugin implements Plugin<Project> {

    @Override
    public void apply(final Project project) {
        project.task("licenseToCommit", type: GradleBuild) { Task task ->
            group = 'Verification'
            description ='If this passed you have license to commit your changes.'
            buildFile = 'build.gradle'

            tasks = [
                'concurrent:continous',
                'stronglytyped-sortedbag:continous',
                'linereader:continous',
                'fileutil:continous',
                'junit-utils:continous',
                'classhelper:continous',
                'asynchronous-stream-reader:continous',
                'stream-reader:continous',
                'string-to-outputstream:continous',
                'inputstream-to-string:continous',
                'visiting-inputstreams:continous',
                'string-to-bytes:continous',
                'string-to-inputstream:continous',
                'völundr-smithy:continous',
                'statistics:continous',
                'string-splitter:continous',
                'bytes-to-string:continous',
                'as-expected:continous',
                'statistics-valueprovider:continous',
                'exportAntBuildFile',
                'aggregateTestReport',
                'aggregateJDependReport',
                'aggregateFindbugsReport',
                'archiveAggregateReports'
            ]
            doLast { println "You've now license to commit, good work!" }
        }

        project.task("distributionPackage", type: GradleBuild, dependsOn: ['licenseToCommit']) { Task task ->
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
                'makeDistributionPackage'
            ]

            doLast { println "Distribution package ready to be uploaded to the repository." }
        }
    }
}
