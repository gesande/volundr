package org.fluentjava.volundr

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.bundling.Jar

class JavaProjectArtifactPlugin implements Plugin<Project> {

    @Override
    public void apply(final Project project) {
        project.plugins.apply("java");

        project.task("sourcesJar", type: Jar, dependsOn: "classes") {
            group = 'Distribution'
            description = "Makes the project specific sourceJar."
            archiveClassifier = 'sources'
            from project.sourceSets.main.allSource
        }

        project.task("testSourcesJar", type: Jar, dependsOn: "classes") {
            group = 'Distribution'
            description = "Makes the project specific testSourceJar."
            archiveClassifier = 'tests'
            from project.sourceSets.test.allSource
        }

        project.task("javadocJar", type: Jar, dependsOn: "javadoc") {
            group = 'Distribution'
            description = "Makes the project specific javadoc jar."
            archiveClassifier = 'javadoc'
            from project.javadoc.destinationDir
        }
    }
}
