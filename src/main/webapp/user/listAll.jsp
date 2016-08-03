<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="user" class="com.bkdwei.model.User"></jsp:useBean>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>all users page</title>
</head>
<body>
	<p>
	<table border=1 padding=0 margin=0>
		<tr>
			<td>id</td>
			<td>name</td>
			<td>password</td>
			<td>create_time</td>
			<td>update_time</td>
		</tr>
		<c:forEach items="${userList}" var="user">
			<tr>
				<td>${user.id }</td>
				<td>${user.name}</td>
				<td>${user.password}</td>
				<td>${user.createTime}</td>
				<td>${user.updateTime}</td>
			</tr>
		</c:forEach>
	</table>
	</p>

</body>
</html>