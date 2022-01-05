package org.fluentjava.volundr

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.bundling.Compression
import org.gradle.api.tasks.bundling.Tar

public class ReportingPlugin implements Plugin<Project> {

    @Override
    public void apply(final Project project) {
        project.configurations { antClasspath }
        project.dependencies { antClasspath 'org.apache.ant:ant-junit:1.10.12' }

        project.task("reportingOptions") {
            group = 'Reporting'
            description = "Prints out reporting plugin options"
            doLast {
                println "reportDir = ${project.buildDir}/reports"
            }
        }

        project.task("aggregateTestReport") {
            group = 'Reporting'
            description = "Makes aggregate test report with ant-junit."

            doLast {
                ClassLoader antClassLoader = org.apache.tools.ant.Project.class.classLoader
                project.configurations.antClasspath.each { File f ->
                    antClassLoader.addURL(f.toURI().toURL())
                }
                def targetDir = new File("${project.buildDir}/reports", 'junit')
                targetDir.mkdirs()
                def resultsDir = targetDir.getPath()
                println 'Creating test report...'

                ant.taskdef(
                        name: 'junitreport',
                        classname: 'org.apache.tools.ant.taskdefs.optional.junit.XMLResultAggregator',
                        classpath: project.configurations.antClasspath.asPath
                )

                ant.junitreport(todir: resultsDir) {
                    fileset(dir: "${project.projectDir}", includes: '**/build/test-results/**/TEST-*.xml')
                    report(todir: targetDir, format: "frames")
                }

                println("Aggregate test report can be found from file://${targetDir}/index.html")
            }
        }

        project.gradle.projectsEvaluated { addTasksAfterProjectsEvaluated(project) }
    }

    static void addTasksAfterProjectsEvaluated(final Project project) {
        project.task("archiveAggregateReports", type: Tar) { Tar task ->
            group = 'Archive'
            description = 'Archive aggregate reports including junit tests/pmd'
            def timestamp = new Date(System.currentTimeMillis()).format("yyyyMMdd-HHmmss")
            from("${project.buildDir}/reports")
            // Set destination directory.
            File parent = project.file("${project.buildDir}/reports").getParentFile()
            task.destinationDirectory = parent
            // Set filename properties.
            task.archiveBaseName = "report-artifacts-${timestamp}"
            archiveExtension = 'tar.gz'
            compression = Compression.GZIP
            doLast {
                String tarFile = "${parent}/${task.archiveFileName.get()}"
                println("Report artifact archive can be found from file://$tarFile")
            }
        }
    }
}
