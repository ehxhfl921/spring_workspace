<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Form Upload</title>
</head>
<body>
	<h1>FileUtil file upload</h1>
	<form action="uploadUtil" method="POST" enctype="multipart/form-data">
		<input type="file" name="file">
		<button>확인</button>
	</form>
	<hr>

	<h1>Upload Form</h1>
	<form action="upload" method="post" enctype="multipart/form-data">
		<input type="file" name="file">
		<button>확인</button>
	</form>
	
	<h1>Upload Multiple</h1>
	<form action="uploadMultiple" method="post" enctype="multipart/form-data">
		<input type="file" name="files" multiple>
		<button>전송</button>
	</form>
	
	<hr>
	<h1>Request Parameter Upload File</h1>
	<form action="uploadWithParam" method="POST" enctype="multipart/form-data">
		<input type="text" name="author" placeholder="작성자"> <br>
		<textarea name="content" placeholder="내용"></textarea> <br>
		<input type="file" name="profile" accept="image/*"> <br>
		<input type="file" name="files" multiple> <br>
		<button>전송</button>
	</form>
	
	<hr>
	<h1>Request Parameter Upload File with DTO</h1>
	<form action="uploadDTO" method="POST" enctype="multipart/form-data">
		<input type="text" name="author" placeholder="작성자"> <br>
		<textarea name="content" placeholder="내용"></textarea> <br>
		<input type="file" name="profile" accept="image/*"> <br>
		<input type="file" name="files" multiple> <br>
		<button>전송</button>
	</form>
</body>
</html>