<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String url =request.getScheme()+"://"+ request.getServerName()+":"+request.getServerPort()+getServletContext().getContextPath() ; %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="refresh" content="1;url=<%=url+"/user/userHome.jsp" %> ">
<body>
<h2>Hello World!</h2>
<a href=<%=url+"/user/userHome.jsp" %>>正在跳转到测试页面</a>
</body>
</html>
