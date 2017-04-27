package net.sf.völundr;

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

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
                'java-heapdump:continous',
                'exportAntBuildFile',
                'aggregateTestReport',
                'aggregateJDependReport',
//                'aggregateCoverageReport',
                'aggregateFindbugsReport',
                'archiveAggregateReports'
            ]
            doLast { println "You've now license to commit, good work!" }
        }

        project.task("distributionPackage", type: GradleBuild, dependsOn: ['licenseToCommit']) { Task task ->
            group = 'Distribution'
            description = 'Distribution package for the whole thing including continous build.'
            buildFile = 'build.gradle'

            tasks << 'concurrent:clean'
            tasks << 'stronglytyped-sortedbag:clean'
            tasks << 'junit-utils:clean'
            tasks << 'classhelper:clean'
            tasks << 'linereader:clean'
            tasks << 'fileutil:clean'
            tasks << 'asynchronous-stream-reader:clean'
            tasks << 'stream-reader:clean'
            tasks << 'string-to-outputstream:clean'
            tasks << 'inputstream-to-string:clean'
            tasks << 'visiting-inputstreams:clean'
            tasks << 'string-to-bytes:clean'
            tasks << 'string-to-inputstream:clean'
            tasks << 'völundr-smithy:clean'
            tasks << 'statistics:clean'
            tasks << 'string-splitter:clean'
            tasks << 'bytes-to-string:clean'
            tasks << 'as-expected:clean'
            tasks << 'java-heapdump:clean'

            tasks << 'concurrent:release'
            tasks << 'stronglytyped-sortedbag:release'
            tasks << 'junit-utils:release'
            tasks << 'classhelper:release'
            tasks << 'linereader:release'
            tasks << 'fileutil:release'
            tasks << 'asynchronous-stream-reader:release'
            tasks << 'stream-reader:release'
            tasks << 'string-to-outputstream:release'
            tasks << 'inputstream-to-string:release'
            tasks << 'visiting-inputstreams:release'
            tasks << 'string-to-bytes:release'
            tasks << 'string-to-inputstream:release'
            tasks << 'völundr-smithy:release'
            tasks << 'statistics:release'
            tasks << 'string-splitter:release'
            tasks << 'bytes-to-string:release'
            tasks << 'as-expected:release'
            tasks << 'java-heapdump:release'

            tasks << 'makeDistributionPackage'

            doLast { println "Distribution package ready to be uploaded to the repository." }
        }
    }
}
