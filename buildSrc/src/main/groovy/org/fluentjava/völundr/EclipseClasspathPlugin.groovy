package org.fluentjava.völundr

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

class EclipseClasspathPlugin implements Plugin<Project>{

    @Override
    public void apply(final Project project) {
        project.plugins.apply("eclipse")

        project.apply {
            project.eclipse.classpath.defaultOutputDir = project.file('target/classes')
        }

        project.task("removeTarget") { Task task ->
            group = 'Eclipse'
            description = "Deletes project specific eclipse target -directory."
            doLast {
                project.delete('target')
                println( "Target -directory deleted from project ${task.project.name}.")
            }
        }

        project.task("removeEclipseClasses") { Task task ->
            group = 'Eclipse'
            description = "Deletes project specific eclipse target/classes -directory."
            doLast {
                project.delete('target/classes')
                println( "Classes -directory deleted from project ${task.project.name}/target.")
            }
        }
    }
}