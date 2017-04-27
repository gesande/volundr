package net.sf.völundr

import net.sf.mygradlebuild.tasks.ExportGradleBuildFileForJavaLibraryProject
import net.sf.mygradlebuild.tasks.ExportGradleBuildFileForNewJavaProject
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

public class NewJavaSubProject implements Plugin<Project> {

    @Override
    public void apply(final Project project) {

        project.task("createLibDirs") { Task task ->
            group 'Development'
            description 'Creates lib and lib-sources -directories under the given project.'
            task.doLast {
                def libDir = new File(task.project.projectDir, 'lib')
                libDir.mkdirs()
                def libSourcesDir = new File(task.project.projectDir, 'lib-sources')
                libSourcesDir.mkdirs()
            }
        }
        project.task("createJavaDirs") { Task task ->
            group 'Development'
            description 'Create directory structures for a Java project.'
            task.doLast {
                project.sourceSets*.java.srcDirs*.each { it.mkdirs() }
                project.sourceSets*.resources.srcDirs*.each { it.mkdirs() }
            }
        }

        project.task("buildGradleForJavaProject",type: ExportGradleBuildFileForNewJavaProject) { ExportGradleBuildFileForNewJavaProject task ->
            task.parent = task.project.projectDir
            task.projectName = task.project.name
            task.applyFroms = ["\"\$emmaPlugin\""]
            task.applyPlugins = [
                "net.sf.mygradlebuild.plugins.JavaProjectDistribution"
            ]
        }

        project.task("buildGradleForJavaLibProject",type: ExportGradleBuildFileForJavaLibraryProject) { ExportGradleBuildFileForJavaLibraryProject task ->
            task.parent = task.project.projectDir
            task.projectName = task.project.name
            task.applyFroms = [
                "net.sf.mygradlebuild.plugins.JavaLibraryProject"
            ]
            task.library = task.project.name
        }
    }
}
