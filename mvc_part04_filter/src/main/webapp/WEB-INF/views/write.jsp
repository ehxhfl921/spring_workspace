<!-- /WEB-INF/views/write.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 작성</title>
</head>
<body>
	<h1>게시글 작성</h1>
	<h3>${requestScope.write}</h3>
	<form method="post">
		제목 : <input type="text" name="title"> <br>
		작성자 : <input type="text" name="writer"> <br>
		내용 : <textarea name="content"></textarea> <br>
		<button>WRITE</button>
	</form>
</body>
</html>