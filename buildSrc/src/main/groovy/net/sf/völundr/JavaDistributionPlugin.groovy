package net.sf.völundr

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.file.FileTree
import org.gradle.api.tasks.bundling.Tar
import org.gradle.logging.StyledTextOutputFactory
import org.gradle.logging.StyledTextOutput.Style

public class JavaDistributionPlugin implements Plugin<Project>{

    @Override
    public void apply(final Project project) {
        project.task("copyDistributionSourceJars") {
            group = 'Distribution'
            description = "Copies project specific source jars to the distribution sources directory."
            doLast {
                def FileTree tree=project.fileTree("${project.projectDir}")
                tree.include('**/build/libs/*-sources.jar')
                project.copy {
                    from tree.getFiles()
                    into project.file('distribution/sources')
                }
            }
        }

        project.task("copyDistributionArchives") { Task task ->
            task.dependsOn("copyDistributionSourceJars")
            group = 'Distribution'
            description = 'Copies project specific distribution archives to distribution.'
            doLast {
                def FileTree tree=project.fileTree("${project.projectDir}")
                tree.include '**/build/distributions/*.zip'
                project.copy {
                    from 'COPYING'
                    from tree.getFiles()
                    into project.file('distribution')
                }
            }
        }

        project.task("archiveDistribution", type: Tar )  { Tar task ->
            task.dependsOn("copyDistributionArchives")
            group = 'Distribution'
            description = "Makes the distribution artifact and copies it to ${project.properties.distributionDir}."
            task.outputs.upToDateWhen { false }
            def String artifactRevision="${project.properties.artifactVersion}"
            def String distributionName = project.properties.distributionBasename
            from project.file('distribution')
            destinationDir = project.file("${project.properties.distributionDir}/artifact-${artifactRevision}")
            baseName = "$distributionName"
            version ="${artifactRevision}"
            extension = 'tar'
            doLast {
                def outputFactory = services.get(StyledTextOutputFactory).create("deployment.archiveDistribution")
                outputFactory.withStyle(Style.Info).println("Archived distribution package can be found from file://${destinationDir}/${distributionName}-${artifactRevision}.tar")
            }
        }

        project.task ("copyDistributionFiles") { Task task ->
            task.dependsOn("archiveDistribution")
            task.outputs.upToDateWhen { false }
            group = 'Distribution'
            description = "Copies the distribution artifact and copies it under ${project.properties.distributionDir}."
            task.doLast {
                def artifactRevision="${project.properties.artifactVersion}"
                def targetDir ="${project.properties.distributionDir}/distribution-${artifactRevision}"
                project.file("${targetDir}").mkdirs()
                project.copy {
                    from project.file('distribution')
                    into "${targetDir}"
                }
                def outputFactory = services.get(StyledTextOutputFactory).create("deployment.copyDistributionFiles")
                outputFactory.withStyle(Style.Info).println("Exploded path for the distribution can be found from file://${targetDir}")
            }
        }

        project.task ("makeDistributionPackage") { Task task ->
            task.dependsOn( "copyDistributionFiles")

            group = 'Distribution'
            description = "Makes the distribution package."
            doLast {
                project.delete("distribution")
                def outputFactory = services.get(StyledTextOutputFactory).create("deployment.makeDistributionPackage")
                outputFactory.withStyle(Style.Info).println( "Distribution package done.")
            }
        }
    }
}

