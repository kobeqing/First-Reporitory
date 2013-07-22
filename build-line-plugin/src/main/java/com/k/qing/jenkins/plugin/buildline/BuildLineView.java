package com.k.qing.jenkins.plugin.buildline;

import com.k.qing.jenkins.plugin.buildline.util.*;
import hudson.Extension;
import hudson.model.*;
import jenkins.model.Jenkins;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import javax.servlet.ServletException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

/**
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
    
    private static String CI_ARCHIVE_DIR = "ci_archive";

    private static String Jenkins_ARCHIVE_DIR = "archive";
    
    private static String SUMMARY_REPORT_NAME = "summary.txt";


    @DataBoundConstructor
    public BuildLineView(final String name, final String buildViewTitle, final String initialJobs, final String viewHeaders, List<ProjectConfiguration> lineList) {
        super(name, Hudson.getInstance());
        this.initialJobs = initialJobs;
        this.buildViewTitle = buildViewTitle;
        this.viewHeaders = viewHeaders;
        this.lineList = lineList;
    }

    /**
     * Get all the information that view page uses.
     * @return
     */
    public Map<TableInfo, List<List<Object>>> getTableData() {
        
        Map<TableInfo, List<List<Object>>> allTableData = new HashMap<TableInfo, List<List<Object>>>();
        
        List<TableInfo> tableInfoList = this.getViewHeaderList();
        
        for(TableInfo tableInfo : tableInfoList) {
            List<List<Object>> tableData = new ArrayList<List<Object>>();
            List<String> headerList = tableInfo.getHeaderList();
            Map<String, Map<String, BuildLineBuild>> buildMap = this.getBuildMap(tableInfo);
            Map<String, String> firstJob2TitleValueMap = this.getFirstJob2TitleValue();
            
            List<String> titleList = new ArrayList<String>(firstJob2TitleValueMap.values());
            Collections.sort(titleList);

            for(String title : titleList) {
                Entry<?, ?> entry = this.getEntry(title, buildMap, firstJob2TitleValueMap);
                if(entry != null) {
                    List<Object> row = this.doOperation(entry, headerList, firstJob2TitleValueMap);
                    tableData.add(row);
                }
            }
            allTableData.put(tableInfo,tableData);
        }
        
//        allTableData.entrySet()
//        Map.Entry<?,?> ddd;
//        ddd.get
        return allTableData;
    }
    
    private Map<String, Map<String, BuildLineBuild>> getBuildMap(TableInfo tableInfo) {
        Map<String, Map<String, BuildLineBuild>> buildMap = new HashMap<String,  Map<String, BuildLineBuild>>();

        List<String> initialJobList = this.getInitialJobList(tableInfo);
        for(String initialJob : initialJobList) {
            List<AbstractBuild> buildList = new ArrayList<AbstractBuild>();
            AbstractBuild lastBuild = (AbstractBuild)this.getProject(initialJob).getLastBuild();
            buildList.add(lastBuild);

            if(this.downStreamBuildList != null) {
                this.downStreamBuildList.clear();
            } else {
                this.downStreamBuildList = new ArrayList<AbstractBuild>();
            }

            buildList.addAll(this.getDownStreamBuildList(this.getProject(initialJob).getDownstreamProjects(), lastBuild));

            Map<String, String> lineMap = this.getLineMap(initialJob);//key : jobName, value : header

            Map<String, BuildLineBuild> latestLineMap = new HashMap<String, BuildLineBuild>(); //key :header, value : build

            for (String jobName : lineMap.keySet()) {
                if (hasJob(jobName, buildList)) {
                    AbstractBuild build = this.getBuild(jobName, buildList);
                    if (build != null) {
                    	BuildLineBuild latestBuild = new BuildLineBuild(build);


                        String summary = this.getSummary(build).toString();
                        latestBuild.setSummary(summary);

                        latestLineMap.put(lineMap.get(jobName), latestBuild);
                    }
                } else {
                    latestLineMap.put(lineMap.get(jobName), null);
                }
            }

            buildMap.put(initialJob, latestLineMap);
        }

        return buildMap;
    }
    
    private Map.Entry<?, ?> getEntry(String title, Map<String, Map<String, BuildLineBuild>> buildMap,Map<String, String> firstJob2TitleValueMap) {
        for(Map.Entry entry : buildMap.entrySet()) {
            if(firstJob2TitleValueMap.get(entry.getKey()).equals(title)) {
                return entry;
            }
        }
        return null;
    }
    
    private List<Object> doOperation(Map.Entry entry, List<String> headerList, Map<String, String> firstJob2TitleValueMap) {
        List<Object> row = new ArrayList<Object>();

        for (int i = 0; i < headerList.size(); i++) {
            if (i == 0) {
                row.add(firstJob2TitleValueMap.get(entry.getKey()));
            } else {
                row.add(((Map<String, AbstractBuild>)entry.getValue()).get(headerList.get(i).trim()));
            }
        }

        return row;
    }

    private StringBuffer getSummary(AbstractBuild build) {
        File summary = new File(build.getRootDir() + File.separator + CI_ARCHIVE_DIR, SUMMARY_REPORT_NAME);

        if (!summary.exists()) {
            summary = new File(build.getRootDir() + File.separator + Jenkins_ARCHIVE_DIR, SUMMARY_REPORT_NAME);
        }

        StringBuffer sb = new StringBuffer();

        if (!summary.exists()) {
            return sb;
        }

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(summary));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n" + "</br>");
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb;
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

            if(items[1].split(":")[1].trim().equals(initialJob)) {
                for(String item : items) {
                    String header = item.split(":")[0].trim();
                    String jobName = item.split(":")[1].trim();
                    lineMap.put(jobName, header);
                }
            }
        }
        return lineMap;
    }

    private List<AbstractBuild> downStreamBuildList = new ArrayList<AbstractBuild>();
    private List<AbstractBuild> getDownStreamBuildList(List<Project> downstreamProjectList, AbstractBuild upstreamBuild) {
        if(downstreamProjectList.isEmpty()) {
            return downStreamBuildList;
        } else {
            for(Project project : downstreamProjectList) {
                List<Project> nextDownstreamProjectList = project.getDownstreamProjects();
                AbstractBuild build = BuildUtil.getDownstreamBuild(project, upstreamBuild);
                if(build != null) {
                    downStreamBuildList.add(build);
                }

                if(nextDownstreamProjectList.size() != 0) {
                    this.getDownStreamBuildList(nextDownstreamProjectList, build);
                }
            }
        }
        return this.downStreamBuildList;
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

    /**
     * Get first Job name list. The name is unique.
     * @param tableInfo
     * @return
     */
    private List<String> getInitialJobList(TableInfo tableInfo) {
        List<String> initialJobList = new ArrayList<String>();

        List<String> branchList = tableInfo.getBranchList();
        
        for(ProjectConfiguration pc : this.getLineList()) {
            if(branchList.isEmpty()) {
                initialJobList.add(pc.getProjectNames().split(",")[1].split(":")[1].trim());
            } else {
                String branchName = pc.getProjectNames().split(",")[0].split(":")[1].trim();
                if(branchName != null) {
                    if(branchList.contains(branchName)) {
                        initialJobList.add(pc.getProjectNames().split(",")[1].split(":")[1].trim());
                    }
                }
            }
        }

        return initialJobList;
    }

    private Map<String, String> getFirstJob2TitleValue() {
        Map<String, String> firstJob2TitleValueMap = new HashMap<String, String>();

        for(ProjectConfiguration pc : this.getLineList()) {
            firstJob2TitleValueMap.put(pc.getProjectNames().split(",")[1].split(":")[1].trim(), pc.getProjectNames().split(",")[0].split(":")[1].trim());
        }

        return firstJob2TitleValueMap;
    }
    
    /**
     * Get the view header information from main.jelly.
     * Input like following:
     * Branch, Build, Coverity, UT, Software Upgrade, Regression, Bullseye$xft1,xft2,xft3;
     * Branch, Regression, Bullseye$xft5,xft7;
     * @return
     */
    public List<TableInfo> getViewHeaderList() {
        
        List<TableInfo> tableInfoList = new ArrayList<TableInfo>();
        
        String[] tableInfos = this.viewHeaders.split(";");
        
        for(String tableInfo : tableInfos) {
            String[] header_branch = tableInfo.split("\\$");
            String headers = header_branch[0];
            
            TableInfo tableInfoObject = new TableInfo();
            
            String[] headerArray = headers.split(",");
            List<String> headerList = new ArrayList<String>();
            for(String header : headerArray) {
                if(header != null) {
                    headerList.add(header.trim());
                }
            }
            tableInfoObject.setHeaderList(headerList);
            
            if(header_branch.length > 1) {
                List<String> branchList = new ArrayList<String>();
                String branchs = header_branch[1];
                String[] branchArray = branchs.split(",");
                for(String branch : branchArray) {
                    if(branch != null) {
                        branchList.add(branch.trim());
                    }
                }
                tableInfoObject.setBranchList(branchList);
            }
            tableInfoList.add(tableInfoObject);
        }
        
        return tableInfoList;
//        List<String> viewHeaderList = new ArrayList<String>();
//        if(this.viewHeaders == null) {
//            return viewHeaderList;
//        }
//        String[] viewHeaderArray = this.viewHeaders.split(",");
//
//
//        for(String header : viewHeaderArray) {
//            if(header != null) {
//                viewHeaderList.add(header.trim());
//            }
//        }
//        return viewHeaderList;
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
        for(String projectName : projectNames) {
            ProjectConfiguration projectConfiguration = new ProjectConfiguration(projectName);
            projectConfigurationList.add(projectConfiguration);
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
        
        public DescriptorImpl() {
            super();
        }

        @Override
        public String getDisplayName() {
            return "Latest Build View";
        }
    }



}
