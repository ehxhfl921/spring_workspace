<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Home</title>
</head>
<body>
	<h3>result : ${result}</h3>
	<a href="test1">test1</a> <br>
	<hr>
	<form action="test2" method="post">
		<input type="number" name="a"> <br>
		<input type="text" name="b"> <br>
		<button>확인</button>
	</form>
	<hr>
	
	<a href="test3">test3</a> <br>
	<hr>
	<a href="test4">test4</a>
	
</body>
</html>
<%
	System.out.println("-------------- HOME JSP 출력 완료 --------------");
	request.setAttribute("home", "homeJSP Value");
%>