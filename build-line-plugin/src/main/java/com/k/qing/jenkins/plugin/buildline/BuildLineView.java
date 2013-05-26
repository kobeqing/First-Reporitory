package com.k.qing.jenkins.plugin.buildline;

import com.k.qing.jenkins.plugin.buildline.util.BuildUtil;
import hudson.Extension;
import hudson.model.*;
import jenkins.model.Jenkins;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: K.Qing
 * Date: 5/6/13
 * Time: 2:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildLineView extends View {

    private String initialJobs;
    private String buildViewTitle;
    private String viewHeaders;
    private List<ProjectConfiguration> lineList;


    @DataBoundConstructor
    public BuildLineView(final String name, final String buildViewTitle, final String initialJobs, final String viewHeaders, List<ProjectConfiguration> lineList) {
        super(name, Hudson.getInstance());
        this.initialJobs = initialJobs;
        this.buildViewTitle = buildViewTitle;
        this.viewHeaders = viewHeaders;
        this.lineList = lineList;
    }

    public List<AbstractBuild> getBuildList() {
        Project firstJob = null;
        List<Project> projectList = Jenkins.getInstance().getProjects();
        for (Project project : projectList) {
            if (this.initialJobs.equals(project.getName())) {
                firstJob = project;
            }
        }

        List<Project> firstProjectList = firstJob.getDownstreamProjects();
        AbstractBuild firstJobLastBuild = (AbstractBuild)firstJob.getLastBuild();

        List<AbstractBuild> buildList = new ArrayList<AbstractBuild>();
        buildList.add(firstJobLastBuild);
        buildList.add((AbstractBuild)firstProjectList.get(0).getLastBuild());

        firstJobLastBuild.getTime();
        firstJobLastBuild.getProject().getName();

        return buildList;
    }

    public Map<String, Map<String, AbstractBuild>> getBuildMap() {
        Map<String, Map<String, AbstractBuild>> buildMap = new HashMap<String,  Map<String, AbstractBuild>>();

        List<String> firstJobList = this.getFirstJobList();
        for(String firstJob : firstJobList) {
            List<AbstractBuild> buildList = new ArrayList<AbstractBuild>();
            AbstractBuild lastBuild = (AbstractBuild)this.getProject(firstJob).getLastBuild();
            if(lastBuild == null) { //do not build, continue
                continue;
            }
            buildList.add(lastBuild);
            String a =lastBuild.getUrl();

            if(this.downStreamProjectList != null) {
                this.downStreamProjectList.clear();
            } else {
                this.downStreamProjectList = new ArrayList<Project>();
            }
            List<Project> downStreamProjectList = this.getDownStreamProjects(this.getProject(firstJob));
            for(Project project : downStreamProjectList) {
                AbstractBuild downStreamBuild = BuildUtil.getDownstreamBuild(project, lastBuild);
                buildList.add(downStreamBuild);
                lastBuild = downStreamBuild;
            }

            Map<String, String> lineMap = this.getLineMap(firstJob);//key : jobName, value : header

            Map<String, AbstractBuild> latestLineMap = new HashMap<String, AbstractBuild>(); //key :header, value : build

            for(String jobName : lineMap.keySet()) {
                if(hasJob(jobName, buildList)) {
                    AbstractBuild build = this.getBuild(jobName, buildList);
                    if(build != null) {
                        latestLineMap.put(lineMap.get(jobName), build);
                    }
                } else {
                    latestLineMap.put(lineMap.get(jobName), null);
                }
            }



            buildMap.put(firstJob, latestLineMap);
        }

        return buildMap;
    }

    public List<List<Object>> getTableData() {
        List<List<Object>> tableData = new ArrayList<List<Object>>();
        List<String> headerList = this.getViewHeaderList();
        Map<String, Map<String, AbstractBuild>> buildMap = this.getBuildMap();

        for(Map.Entry entry : buildMap.entrySet()) {
            List<Object> row = new ArrayList<Object>();
            row.add(entry.getKey());

            for(String header : headerList) {
                row.add(((Map<String, AbstractBuild>)entry.getValue()).get(header.trim()));
            }

            tableData.add(row);
        }

        return tableData;

    }

    private boolean hasJob(String jobName, List<AbstractBuild> buildList) {
        for(AbstractBuild build :buildList) {
            String projectName = build.getProject().getName();
            if(jobName.equals(projectName)) {
                return true;
            }
        }
        return false;
    }

    private AbstractBuild getBuild(String jobName, List<AbstractBuild> buildList) {
        for(AbstractBuild build :buildList) {
            String projectName = build.getProject().getName();
            if(jobName.equals(projectName)) {
                return build;
            }
        }
        return null;
    }

    private Map<String, String> getLineMap(String initialJob) {
        Map<String, String> lineMap = new HashMap<String, String>();
        if(initialJob != null) {
            initialJob = initialJob.trim();
        } else {
            return lineMap;
        }

        for(ProjectConfiguration projectConfiguration : this.getLineList()) {

            String projectNames = projectConfiguration.getProjectNames();

            if(projectNames == null || projectNames.isEmpty()) {
                continue;
            }
            String[] items = projectConfiguration.getProjectNames().split(",");

            if(items[0].split(":")[1].trim().equals(initialJob)) {
                for(String item : items) {
                    String header = item.split(":")[0].trim();
                    String jobName = item.split(":")[1].trim();
                    lineMap.put(jobName, header);
                }
            }
        }
        return lineMap;
    }


    private List<Project> downStreamProjectList = new ArrayList<Project>();
    private List<Project> getDownStreamProjects(Project project) {
        List<Project> projectList = project.getDownstreamProjects();
        if(projectList.isEmpty()) {
            return this.downStreamProjectList;
        } else {
            this.downStreamProjectList.addAll(projectList);
            for(Project projectTmp : projectList) {
                getDownStreamProjects(projectTmp);
            }
        }
        return this.downStreamProjectList;
    }

    private Project getProject(String jobName) {
        List<Project> projectList = Jenkins.getInstance().getProjects();
        for (Project project : projectList) {
            if (jobName.equals(project.getName())) {
                return project;
            }
        }
        return null;
    }

    private List<String> getFirstJobList() {
        List<String> firstJobList = new ArrayList<String>();

        for(ProjectConfiguration pc : this.getLineList()) {
            firstJobList.add(pc.getProjectNames().split(",")[0].split(":")[1].trim());
        }

        return firstJobList;
    }

    public List<String> getViewHeaderList() {
        List<String> viewHeaderList = new ArrayList<String>();
        if(this.viewHeaders == null) {
            return viewHeaderList;
        }
        String[] viewHeaderArray = this.viewHeaders.split(",");


        for(String header : viewHeaderArray) {
            if(header != null) {
                viewHeaderList.add(header.trim());
            }
        }
        return viewHeaderList;
    }

    @Override
    public Collection<TopLevelItem> getItems() {
        return Hudson.getInstance().getItems();
    }

    @Override
    public boolean contains(TopLevelItem item) {
        return this.getItems().contains(item);
    }

    @Override
    public void onJobRenamed(Item item, String oldName, String newName) {
        System.out.println("This is my on job renamed.");
    }

    @Override
    protected void submit(StaplerRequest req) throws IOException, ServletException, Descriptor.FormException {
        this.initialJobs = req.getParameter("initialJobs");
        this.viewHeaders = req.getParameter("viewHeaders");
        this.buildViewTitle = req.getParameter("buildViewTitle");
        String[] projectNames = req.getParameterValues("projectNames");

        List<ProjectConfiguration> projectConfigurationList = new ArrayList<ProjectConfiguration>();
        if(projectNames != null) {
            for(String projectName : projectNames) {
                ProjectConfiguration projectConfiguration = new ProjectConfiguration(projectName);
                projectConfigurationList.add(projectConfiguration);
            }
        }

        this.lineList = projectConfigurationList;

    }

    @Override
    public Item doCreateItem(StaplerRequest req, StaplerResponse rsp) throws IOException, ServletException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getViewHeaders() {
        return viewHeaders;
    }

    public void setViewHeaders(final String viewHeaders) {
        this.viewHeaders = viewHeaders;
    }

    public String getBuildViewTitle() {
        return buildViewTitle;
    }

    public void setBuildViewTitle(final String buildViewTitle) {
        this.buildViewTitle = buildViewTitle;
    }

    public String getInitialJobs() {
        return initialJobs;
    }

    public void setInitialJobs(final String initialJobs) {
        this.initialJobs = initialJobs;
    }

    public List<ProjectConfiguration> getLineList() {
        return lineList;
    }

    public void setLineList(List<ProjectConfiguration> lineList) {
        this.lineList = lineList;
    }

    private void addLineList(ProjectConfiguration projectConfiguration) {
        this.lineList.add(projectConfiguration);
    }

    /**
     * This descriptor class is required to configure the View Page
     *
     */
    @Extension
    public static final class DescriptorImpl extends ViewDescriptor {

        @Override
        public String getDisplayName() {
            return "Build Line View";
        }
    }


}
