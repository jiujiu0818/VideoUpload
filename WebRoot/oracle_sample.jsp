<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!-- This import is necessary for JDBC -->
<%@ page import="java.sql.*"%>
<%@ page import="oracle.jdbc.pool.OracleDataSource"%>

<!-- Database lookup -->
<%
		Connection conn = null; 
		ResultSet rset = null; 
		String error_msg = ""; 
		try {
		OracleDataSource ods = new OracleDataSource();
		ods.setURL("jdbc:oracle:thin:qj2131/YddgaHVC@//w4111f.cs.columbia.edu:1521/ADB"); 
		conn = ods.getConnection();
		Statement stmt = conn.createStatement();
		rset = stmt.executeQuery("select * from Character");
} catch (SQLException e) 
		{ error_msg = e.getMessage(); if( conn != null ) {
		conn.close(); }
}
%>


<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>JSP Sample</title>
</head>
<body>
	<H2>Tables</H2>
	<TABLE>
		<tr>
			<td>TABLE</td>
		</tr>
		<tr>
			<td><b>----------</b></td>
		</tr>
 
	<%
		if(rset != null) {
 			while(rset.next()) {
	 			out.print("<tr>");
	 			out.print("<td>" + rset.getString(2) + "</td>");
	 			out.print("</tr>");
 			}
 		} else {
 			out.print(error_msg);
 		}
	
		if( conn != null ) {
			conn.close();
		}
	%>
	</TABLE>
</body>
</html>