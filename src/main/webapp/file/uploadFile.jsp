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
<h2>单个文件上传</h2>
<form action="<%=url+"/file/upload" %>" enctype="multipart/form-data"  method="post">
文件：<input type="file" name="file" />
<input type="submit" value="上传" /><br>
</form>
<h2>多个文件上传</h2>
<form action="<%=url+"/file/batchUpload" %>" enctype="multipart/form-data"  method="post">
文件1：<input type="file" name="file" /><br>
文件2：<input type="file" name="file" /><br>
文件3：<input type="file" name="file" /><br>
<input type="submit" value="上传" /><br>
</form>
</body>
</html>