<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String realpath = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>视频播放</title>
		<script type="text/javascript"
			src="<%=realpath%>/video/js/ckplayer/ck.js?57" charset="utf-8"></script>
	</head>

	<body>
		<div id="videoShow">
		</div>
		<script type="text/javascript">
    	C.K(
    		{
    			f:'<%=realpath%>/video/downloadfile.jsp?id=<%=request.getParameter("id")%>',
						p : 0,
						v : 100,
						e : 2
					}, "videoShow", 640, 480);
</script>
	</body>
</html>
