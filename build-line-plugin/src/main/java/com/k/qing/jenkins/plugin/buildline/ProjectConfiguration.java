package com.k.qing.jenkins.plugin.buildline;

import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * User: K.Qing
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
    public static final class DescriptorImpl extends Descriptor<ProjectConfiguration> {
        
        public DescriptorImpl() {
            super();
        }

        @Override
        public String getDisplayName() {
            // TODO Auto-generated method stub
            return null;
        }
    }
}
