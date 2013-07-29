package com.k.qing.jenkins.plugin.buildline;

import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import org.kohsuke.stapler.DataBoundConstructor;

import java.util.ArrayList;
import java.util.List;

public class TableInfo extends AbstractDescribableImpl<TableInfo> {

    private String headers;
    private String branches;
	private List<String> headerList = new ArrayList<String>();
	private List<String> branchList = new ArrayList<String>();

    @DataBoundConstructor
    public TableInfo(String headers, String branches) {
        this.headers = headers;
        this.branches = branches;
        if(headers != null && !headers.isEmpty()) {
            String[] headerArray = headers.split(",");
            for(String header : headerArray) {
                if(header != null) {
                    headerList.add(header.trim());
                }
            }
        }
        if(branches != null && !branches.isEmpty()) {
            String[] branchArray = branches.split(",");
            for(String branch : branchArray) {
                if(branch != null) {
                    branchList.add(branch.trim());
                }
            }
        }
    }

	public List<String> getHeaderList() {
		return headerList;
	}

	public void setHeaderList(List<String> headerList) {
		this.headerList = headerList;
	}

	public List<String> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<String> branchList) {
		this.branchList = branchList;
	}

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
        if(headers != null && !headers.isEmpty()) {
            String[] headerArray = headers.split(",");
            for(String header : headerArray) {
                if(header != null) {
                    headerList.add(header.trim());
                }
            }
        }
    }

    public String getBranches() {
        return branches;
    }

    public void setBranches(String branches) {
        this.branches = branches;
        if(branches != null && !branches.isEmpty()) {
            String[] branchArray = branches.split(",");
            for(String branch : branchArray) {
                if(branch != null) {
                    branchList.add(branch.trim());
                }
            }
        }
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((branchList == null) ? 0 : branchList.hashCode());
		result = prime * result
				+ ((headerList == null) ? 0 : headerList.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TableInfo other = (TableInfo) obj;
		if (branchList == null) {
			if (other.branchList != null)
				return false;
		} else if (!branchList.equals(other.branchList))
			return false;
		if (headerList == null) {
			if (other.headerList != null)
				return false;
		} else if (!headerList.equals(other.headerList))
			return false;
		return true;
	}

    @Extension
    public static final class DescriptorImpl extends Descriptor<TableInfo> {

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