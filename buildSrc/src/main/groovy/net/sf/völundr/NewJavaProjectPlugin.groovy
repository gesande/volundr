package net.sf.völundr

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.GradleBuild

class NewJavaProjectPlugin implements Plugin<Project> {

    @Override
    public void apply(final Project project) {
        project.plugins.apply("java");

        project.subprojects { apply plugin: NewJavaSubProject }

        project.task("newJavaProject", type: GradleBuild) { GradleBuild task ->
            group ='Development'
            description ="Creates a new Java project, this task required -PnewJavaProject -argument."
            def javaProject =  task.project.properties.newJavaProject
            println("newJavaProject = ${javaProject}")
            //File projDir = task.project.mkdir("${javaProject}")
            String createJavaDirs = "${javaProject}:createJavaDirs"
            String eclipseSettingsFor = "${javaProject}:eclipseSettingsFor"
            String buildGradle = "${javaProject}:buildGradleForJavaProject"
            task.tasks = [
                createJavaDirs,
                eclipseSettingsFor,
                buildGradle
            ]
            doLast { println("New Java project can now be found from ${projDir.name}") }
        }

        project.task("newJavaLibProject", type: GradleBuild) { GradleBuild task ->
            group ='Development'
            description ="Creates a new Java library project, this task required -PnewJavaProject -argument."
            def javaProject =  task.project.properties.newJavaProject
            String createJavaDirs = "${javaProject}:createJavaDirs"
            String eclipseSettingsFor = "${javaProject}:eclipseSettingsFor"
            String createLibDirs="${javaProject}:createLibDirs"
            String buildGradle = "${javaProject}:buildGradleForJavaLibProject"
            task.tasks=[
                createJavaDirs,
                eclipseSettingsFor,
                createLibDirs,
                buildGradle
            ]

            doLast { println("New Java project can now be found from ${task.project.buildDir}/${javaProject}") }
        }
    }
}

