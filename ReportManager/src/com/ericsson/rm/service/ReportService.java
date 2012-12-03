package com.ericsson.rm.service;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

import com.ericsson.rm.bean.TestReport;

public class ReportService implements IReportService {
	
	private static Logger logger = Logger.getLogger(ReportService.class);
	
	public void addReport(TestReport testReport) {
		String caseName = testReport.getCaseName();
		String casePath = testReport.getCathPath();
		String caseResult = testReport.getCaseResult();
		String logPath = testReport.getLogPath();
		
		if (caseName == null || caseName.isEmpty()) {
			
		}
		
	}
	
}
