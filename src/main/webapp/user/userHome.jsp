<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String url =request.getScheme()+"://"+ request.getServerName()+":"+request.getServerPort()+getServletContext().getContextPath() ; %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>result page</title>
</head>
<body>
<h2>一、通过id进行增删改查</h2>
<ol>
<li><a href=<%=url+"/user/saveUser" %>>增</a></li>
<li><a href=<%=url+"/user/deleteUser?id=2" %>>删</a></li>
<li><a href=<%=url+"/user/updateUser" %>>改</a></li>
<li><a href=<%=url+"/user/getUser?id=2" %>>查单个</a></li>
<li><a href=<%=url+"/user/listAll" %>>查所有</a></li>
</ol>

<h2>二、通过name进行增删改查</h2>
<table border=1 width="100%">
<tr>
<td>
<h1>查询</h1>
<form action=<%=url+"/user/queryUserByName" %> method="post">
用户名：<input type="text"  name="name"/><br>
<input type="submit" value="提交" />
<input type="reset" value="重置" />
</form>

</td>
<td>

<h1>新增</h1>
<form action=<%=url+"/user/addUser" %> method="post">
用户名：<input type="text"  name="name"/><br>
密&nbsp&nbsp&nbsp码：<input type="password"  name="password" /><br>
<input type="submit" value="提交" />
<input type="reset" value="重置" />
</form>
</td>
</tr>

<tr>
<td>
<h1>删除</h1>
<form action=<%=url+"/user/deleteUserByName" %> method="post">
用户：<input type="text"  name="name"/><br>
<input type="submit" value="提交" />
<input type="reset" value="重置" />
</form>
</td>
<td>

<h1>修改</h1>
<form action=<%=url+"/user/updateUserByName" %> method="post">
用户名：<input type="text"  name="name"/><br>
密&nbsp&nbsp&nbsp码：<input type="password"  name="password" /><br>
<input type="submit" value="提交" />
<input type="reset" value="重置" />
</form>
</td>
</tr>
</table>
</body>
</html>