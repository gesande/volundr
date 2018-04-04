package org.fluentjava.völundr;

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.GradleBuild
import net.sf.mygradlebuild.plugins.DefaultContinousBuildPlugin
import org.gradle.testing.jacoco.plugins.JacocoPlugin
import org.gradle.testing.jacoco.tasks.JacocoMerge
import org.gradle.api.tasks.testing.Test

public class VolundrContinousBuildPlugin extends DefaultContinousBuildPlugin {

 	@Override
    public void apply(final Project project) {
    	super.apply(project);
        project.plugins.apply(JacocoPlugin);
        
        project.task("jacocoMerge", type: JacocoMerge) {
	        executionData project.tasks.withType(Test)

	        doFirst {
	            executionData = files(executionData.findAll { it.exists() })
	        }
	    }
    }
}
