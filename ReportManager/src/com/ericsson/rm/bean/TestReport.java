package com.ericsson.rm.bean;

public class TestReport {
	private String caseName;
	private String cathPath;
	private String startTime;
	private String endTime;
	private String caseResult;
	private String logPath;
	
	public String getCaseName() {
		return caseName;
	}
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	public String getCathPath() {
		return cathPath;
	}
	public void setCathPath(String cathPath) {
		this.cathPath = cathPath;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getCaseResult() {
		return caseResult;
	}
	public void setCaseResult(String caseResult) {
		this.caseResult = caseResult;
	}
	public String getLogPath() {
		return logPath;
	}
	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}
}
