<!-- resData.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Response Data</title>
</head>
<body>
	<a href="testJSON">testJSON</a> <br>
	<a href="sampleList">sampleList</a> <br>
	
	<hr>
	
	<form action="sample" method="POST">
		<input type="text" name="name"> <br>
		<input type="number" name="price"> <br>
		<button>전송</button>
	</form>
</body>
</html>