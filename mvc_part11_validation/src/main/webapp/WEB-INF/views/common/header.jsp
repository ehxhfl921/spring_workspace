<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}" scope="session" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${pageContext.request.contextPath}</title>
</head>
<body>

	
	<header>
		<a href="${path}">HOME</a> | 
		<a href="${path}/regex">정규 표현식</a> | 
		<a href="${path}/user/join">회원 가입</a> | 
		<a href="">로그인</a>
	</header>
	
	
	
</body>
</html>