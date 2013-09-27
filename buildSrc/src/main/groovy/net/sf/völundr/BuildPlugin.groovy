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
           
            tasks << 'concurrent:continous'
            tasks << 'stronglytyped-sortedbag:continous'
            tasks << 'linereader:continous'
            tasks << 'fileutil:continous'
            tasks << 'junit-utils:continous'
            tasks << 'classhelper:continous'
            
            tasks << 'exportAntBuildFile'
            tasks << 'aggregateTestReport'
            tasks << 'aggregateJDependReport'
            tasks << 'aggregateCoverageReport'
            tasks << 'aggregateFindbugsReport'
            tasks << 'archiveAggregateReports'

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
            
            tasks << 'concurrent:release'
            tasks << 'stronglytyped-sortedbag:release'
            tasks << 'junit-utils:release'
            tasks << 'classhelper:release'
            tasks << 'linereader:release'
            tasks << 'fileutil:release'
            
            tasks << 'makeDistributionPackage'

            doLast { println "Distribution package ready to be uploaded to the repository." }
        }
    }
}
