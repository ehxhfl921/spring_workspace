<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>uploadResult.jsp</title>
</head>
<body>
	<h1>Upload Result</h1>
	<h3><a href="uploadForm">upload form</a></h3>
	업로드된 파일 이름 : ${savedName} <br>
	<a href="upload${savedName}" download="${savedName}" target="_blank">${savedName}</a>
	
	<h3>author : ${author}</h3>
	<h3>content : ${content}</h3>
	<hr>
	<c:if test="${!empty saves}">
		<h1>Upload Multiple File</h1>
		
		<c:forEach var="name" items="${saves}">
			<h4>${name}</h4>
			<c:set var="origin" value="${fn:substringAfter(name, '_')}" scope="page" />
			<h4>${origin}</h4>
		</c:forEach>
	</c:if>
</body>
</html>