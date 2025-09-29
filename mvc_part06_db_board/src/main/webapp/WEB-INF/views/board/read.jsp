<!-- read.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>READ</title>
</head>
<body>
	<!-- model : boardVO -->
	<h1>READ BOARD - ${result}</h1>
	<form method="POST">
		<div>
			<label>TITLE</label>
			<input type="text" name="title" readonly value="${boardVO.title}">
		</div>
		<div>
			<label>CONTENT</label>
			<textarea name="content" rows="5" cols="20" readonly>${boardVO.content}</textarea>
		</div>
		<div>
			<label>WRITER</label>
			<input type="text" name="writer" readonly value="${boardVO.writer}">
		</div>
		<div>
			<a href="modify?bno=${boardVO.bno}">MODIFY</a> | 
			<a href="remove?bno=${boardVO.bno}">DELETE</a> | 
			<a href="listPage">LIST</a>
		</div>
	</form>
</body>
</html>