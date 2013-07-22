package com.k.qing.jenkins.plugin.buildline;

import java.text.SimpleDateFormat;
import java.util.Date;

import hudson.model.Result;
import hudson.model.AbstractBuild;

public class BuildLineBuild {

	private AbstractBuild build;
	private String summary;

	private String table = null;

	public BuildLineBuild(final AbstractBuild build) {
		this.build = build;
	}

	public BuildLineBuild(String table) {
		this.table = table;
	}

	public final Date getTime() {
		return this.build.getTime();
	}

	public final String getStartTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = this.getTime();
		if (date != null) {
			return sdf.format(date);
		}
		return null;
	}

	public final String getUrl() {
		return this.build.getUrl();
	}

	public final String getProjectName() {
		return this.build.getProject().getName();
	}

	public final Result getResult() {
		return this.build.getResult();
	}

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

	public String getTable() {
		return this.table;
	}

	@Override
	public String toString() {
		return build.toString();
	}

}
