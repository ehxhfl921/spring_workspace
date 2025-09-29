<!-- register.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>REGISTER</title>
</head>
<body>
	<h1>REGISTER BOARD</h1>
	<form method="POST">
		<div>
			<label>TITLE</label>
			<input type="text" name="title" required>
		</div>
		<div>
			<label>CONTENT</label>
			<textarea name="content" rows="5" cols="20" required></textarea>
		</div>
		<div>
			<label>WRITER</label>
			<input type="text" name="writer" required>
		</div>
		<div>
			<input type="submit" value="글 작성">
		</div>
	</form>
</body>
</html>