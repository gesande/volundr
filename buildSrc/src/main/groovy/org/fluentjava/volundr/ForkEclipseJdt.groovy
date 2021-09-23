package org.fluentjava.volundr

import org.gradle.api.Plugin
import org.gradle.api.Project

class ForkEclipseJdtPlugin implements Plugin<Project> {

    @Override
    public void apply(final Project project) {
        project.extensions.create("forkJdt", ForkEclipseJdtExtension)
        project.plugins.apply("eclipse")
        project.gradle.projectsEvaluated {
            project.eclipseJdt {
                def jdtInputFile = "${project.forkJdt.jdtInputFile}"
                inputFile = project.file(jdtInputFile)
                doLast { println "Forked eclipseJdt.inputFile from ${jdtInputFile} for project '${project.name}'" }
            }
        }
    }
}

class ForkEclipseJdtExtension {
    String jdtInputFile
}
