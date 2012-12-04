package com.ericsson.rm.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ericsson.rm.bean.TestCase;
import com.ericsson.rm.service.CaseService;

/**
 * Servlet implementation class CaseServlet
 */
public class CaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private CaseService caseService = new CaseService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CaseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<TestCase> testCaseList = caseService.getTestCaseList();
		request.setAttribute("testCaseList", testCaseList);
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

}
