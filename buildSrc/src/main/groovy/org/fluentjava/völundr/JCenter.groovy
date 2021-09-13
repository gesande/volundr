package org.fluentjava.völundr

import org.gradle.api.Plugin
import org.gradle.api.Project

class JCenter implements Plugin<Project> {

    @Override
    public void apply(final Project project) {
        project.allprojects { repositories { jcenter() } }
    }
}
