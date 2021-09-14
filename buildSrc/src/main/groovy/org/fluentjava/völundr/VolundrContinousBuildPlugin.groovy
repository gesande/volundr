package org.fluentjava.völundr

import net.sf.mygradlebuild.plugins.CodeAnalysis
import net.sf.mygradlebuild.plugins.ContinousPlugin
import net.sf.mygradlebuild.plugins.EclipseClasspathPlugin
import net.sf.mygradlebuild.plugins.ForkEclipseJdtPlugin
import net.sf.mygradlebuild.plugins.ForkPmdSettings
import net.sf.mygradlebuild.plugins.JDependWithXmlReports
import net.sf.mygradlebuild.plugins.JavaProjectArtifactPlugin
import net.sf.mygradlebuild.plugins.ProjectVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.testing.jacoco.plugins.JacocoPlugin
import org.gradle.testing.jacoco.tasks.JacocoMerge

//TODO: clean
public class VolundrContinousBuildPlugin implements Plugin<Project>, GroovyObject {

    @Override
    public void apply(final Project project) {
        project.plugins.apply(JavaProjectArtifactPlugin)
        project.plugins.apply(ForkPmdSettings)
        project.plugins.apply(JDependWithXmlReports)
        project.plugins.apply(CodeAnalysis)
        project.plugins.apply(ContinousPlugin)
        project.plugins.apply(ForkEclipseJdtPlugin)
        project.plugins.apply(EclipseClasspathPlugin)
        project.plugins.apply(ProjectVersion)

        project.plugins.apply(JacocoPlugin)

        project.task("jacocoMerge", type: JacocoMerge) {
            executionData project.tasks.withType(Test)

            doFirst {
                executionData = files(executionData.findAll { it.exists() })
            }
        }
    }
}
