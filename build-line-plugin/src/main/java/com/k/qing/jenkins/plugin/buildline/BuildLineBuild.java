package com.k.qing.jenkins.plugin.buildline;

import hudson.model.AbstractBuild;

public class BuildLineBuild {
	
	private AbstractBuild build;
	private String summary;
	
	public AbstractBuild getBuild() {
		return build;
	}
	public void setBuild(AbstractBuild build) {
		this.build = build;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
}
