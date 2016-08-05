<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <% String url =request.getScheme()+"://"+ request.getServerName()+":"+request.getServerPort()+getServletContext().getContextPath() ; %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<ol>
<li><a href=<%=url+"/file/uploadFile.jsp" %>>单文件上传</a></<li>
<li><a href=<%=url+"/file/batchUploadFile.jsp" %>>多文件上传</a></<li>
<li><a href=<%=url+"/file/listFile.jsp" %>>测试文件上传</a></<li>
</ol>
</body>
</html>