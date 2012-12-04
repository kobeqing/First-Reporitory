<%@page import="com.ericsson.rm.bean.TestCase"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<form id="myform" name="myform" method="post" action="/ImTracker/releaseManage/ReleaseManageQuery?method=queryAll">
		<center>
		<table border="0" cellspacing="0" cellpadding="0">
			<tr><td><br></td></tr>
			<tr bgcolor="#00285e">
				<td width=20% align="left"><img src="/ImTracker/images/ericsson_logo_notagline.gif"><br></td>
				<td width=60% align="center"><font size=+2 color="#ffffff"><b>&nbsp;&nbsp;SP R1 Test Case Management</b> </font></td>
				<td width=20%>&nbsp;</td>
			</tr>
			<tr>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td colspan=2 align='left'><a href="/ImTracker/imtracker.jsp">
					<font color='blue'><b><u><i>Access Menu Page</i></u></b></font>
					</a>
				</td>
				<td colspan=2 align='right'><a href="/ImTracker/releaseManage/brickInstallerTool.jsp">
					<font color='blue'><b><u><i>Download brick installer</i></u></b></font>
					</a>
				</td>
			</tr>
		</table>
		<p align="center"></p>
		<table border='0' cellspacing='2' cellpadding='2'>
			<tr>
				<td>
					<font color='darkgreen'> <b>Release:</b></font>
					<select name="release" style='font-size: 8pt'>
					</select>     
				</td>
				<td>
					<font color='darkgreen'> <b>IM Label:</b></font>
					<select id='imLabel' name='imLabel' style='font-size: 8pt; width: 130pt'>
						<option selected>All</option>
					</select>
				</td>
				<td><INPUT TYPE=SUBMIT name="postform_submit" style="font-weight: bold;" Value="Submit"></td>
			</tr>
		</table>
		</center>
		<hr>
		<br>
		<%
			List<TestCase> testCaseList = (List<TestCase>)request.getAttribute("testCaseList");
		%>
		<div align="center">
			<table class="solidtable" border="2" cellspacing="2" cellpadding="2" id="mainTable">
				<tr>
					<th class="subheading" width="120">Case Name</th>
					<th class="subheading" width="200">Case Path</th>
<!-- 					<th class="subheading">Simulator Label</th> -->
					<th class="subheading" width="100">Pass Times</th>
					<th class="subheading" width="100">Fail Times</th>
					<th class="subheading" width="100">Total Times</th>
					<th class="subheading" width="100">Pass Rate</th>
					<!--<th class="subheading">Operation</th>-->
				</tr>
				<%for(TestCase testCase : testCaseList) { %>
				<tr class="trodd" height="30px">
					<td align="center"><%=testCase.getName() %></td>
					<td align="center"><%=testCase.getPath() %></td>
					<td align="center"><%=testCase.getFailTimes() %></td>
					<td align="center"><%=testCase.getFailTimes() %></td>
					<td align="center"><%=testCase.getRunTimes() %></td>
					<td align="center">Modify</td>
				</tr>
				<%} %>
				<tr>
					<td colspan="6" align="right">
						<a href="/ImTracker/releaseManage/BrickReleaseManagement?page=1">first</a>
						<a href="/ImTracker/releaseManage/BrickReleaseManagement?page=1">previous</a>
						<a href="/ImTracker/releaseManage/BrickReleaseManagement?page=1">next</a>
						<a href="/ImTracker/releaseManage/BrickReleaseManagement?page=1">last</a>
					</td>
				</tr>
			</table>
		</div>
	</form>

</body>
</html>