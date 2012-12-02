package com.ericsson.rm.service;

import com.ericsson.rm.bean.TestReport;

public class ReportService implements IReportService {

	public void addReport(TestReport testReport) {
		String caseName = testReport.getCaseName();
	}

}
