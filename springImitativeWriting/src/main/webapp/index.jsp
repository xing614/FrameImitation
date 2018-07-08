<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<body>
<h2>Hello World!</h2>
<form action="${pageContext.request.contextPath}/Login/fix.do">
	用户名：<input type="text" value="${param.username}" name="username">
            <br/>
            密码：<input type="text" value="${param.pwd}" name="pwd">
            <br/>
            <input type="submit" value="登录"/>
</form>
</body>
</html>
