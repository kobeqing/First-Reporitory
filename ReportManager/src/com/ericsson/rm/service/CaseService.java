package com.ericsson.rm.service;

import java.util.ArrayList;
import java.util.List;

import com.ericsson.rm.bean.TestCase;

public class CaseService {
	
	public List<TestCase> getTestCaseList() {
		List<TestCase> testCaseList = new ArrayList<TestCase>();
		
		TestCase tc = new TestCase();
		tc.setName("bbat11010101");
		tc.setPath("/mpls/01.01.01");
		tc.setFailTimes("10");
		tc.setRunTimes("200");
		
		testCaseList.add(tc);
		
		return testCaseList;
	}
	
}
