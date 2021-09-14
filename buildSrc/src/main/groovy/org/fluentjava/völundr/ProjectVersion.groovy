package org.fluentjava.völundr

import org.gradle.api.Plugin
import org.gradle.api.Project

class ProjectVersion implements Plugin<Project>{

    @Override
    public void apply(final Project project) {

        project.task("showVersion") {
            group 'Miscelleneous'
            description 'Prints out the version.'
            doLast {  println project.version}
        }
    }
}