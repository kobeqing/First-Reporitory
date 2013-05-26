package com.k.qing.jenkins.plugin.buildline;

import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * Created with IntelliJ IDEA.
 * User: ejiaqsu_local
 * Date: 5/23/13
 * Time: 1:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProjectConfiguration extends AbstractDescribableImpl<ProjectConfiguration> {
    private String projectNames;

    @DataBoundConstructor
    public ProjectConfiguration(final String projectNames) {
        this.projectNames = projectNames;
    }

    public String getProjectNames() {
        return projectNames;
    }

    public void setProjectNames(String projectNames) {
        this.projectNames = projectNames;
    }

    @Extension
    public static class DescriptorImpl extends Descriptor<ProjectConfiguration> {
        public String getDisplayName() {
            return "";
        }
    }
}
