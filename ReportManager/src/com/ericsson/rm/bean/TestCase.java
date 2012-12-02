package com.ericsson.rm.bean;

public class TestCase {
	private String name;
	private String path;
	private String feature;
	private String runTimes;
	private String failTimes;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFeature() {
		return feature;
	}
	public void setFeature(String feature) {
		this.feature = feature;
	}
	public String getRunTimes() {
		return runTimes;
	}
	public void setRunTimes(String runTimes) {
		this.runTimes = runTimes;
	}
	public String getFailTimes() {
		return failTimes;
	}
	public void setFailTimes(String failTimes) {
		this.failTimes = failTimes;
	}
}
