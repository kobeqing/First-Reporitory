package com.k.qing.jenkins.plugin.buildline;

import org.kohsuke.stapler.DataBoundConstructor;

/**
 * User: k.qing
 * Date: 7/22/13
 * Time: 4:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class TableHeader {
    private String headers;
    private String branches;

    @DataBoundConstructor
    public TableHeader(String headers, String branches) {
        this.headers = headers;
        this.branches = branches;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public String getBranches() {
        return branches;
    }

    public void setBranches(String branches) {
        this.branches = branches;
    }
}
