<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${empty path}">
	<c:set var="path" value="${pageContext.request.contextPath}" scope="session" />
</c:if>
<!doctype html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Spring Final Project</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="${path}/resources/css/common.css" rel="stylesheet">
</head>
<body class="d-flex flex-column min-vh-100">
	<header>
		<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
			<div class="container">
				<a class="navbar-brand" href="${path}">Spring Final Project</a>
				<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
					<span class="navbar-toggler-icon"></span>
				</button>
				<div class="collapse navbar-collapse" id="navbarNav">
					<ul class="navbar-nav ms-auto align-items-center">
						<c:choose>
							<c:when test="${empty userInfo}">
								<li class="nav-item">
									<a class="nav-link"	href="${path}/user/join">회원가입</a>
								</li>
								<li class="nav-item">
									<a class="nav-link"	href="${path}/user/login">로그인</a>
								</li>
							</c:when>
							<c:otherwise>
								<li class="nav-item">
									<a class="nav-link" href="#">
										<c:if test="${!empty userInfo.u_profile}">
											<img src="${path}/displayFile?fileName=${userInfo.u_profile}" class="profileImage"/>
										</c:if>
										<c:if test="${empty userInfo.u_profile}">
											<img src="${path}/resources/img/profile.jpg" class="profileImage"/>
										</c:if>
										${userInfo.u_name} 님 방가
									</a>
								</li>
								<li class="nav-item">
									<a class="nav-link" href="${path}/user/logout">로그아웃</a>
								</li>
								<li class="nav-item">
									<a class="nav-link" href="#">chat</a>
								</li>
							</c:otherwise>
						</c:choose>
						<li class="nav-item">
							<a class="nav-link" href="${path}/board/searchList">게시판</a>
						</li>
						
						<!-- 날씨 정보 출력 -->
						<c:if test="${! empty weather}">
							<li class="nav-item ms-3">
								<div class="d-flex align-items-center text-white small">
									<c:if test="${! empty weather.src}">
										<img src="${weather.src}" alt="날씨 아이콘" style="width:24px;height:24px;margin-right:5px;">
									</c:if>
									<!-- 지역 상태 온도 -->
									<span>${weather.location} ${weather.status} ${weather.temperature}</span>
								</div>
							</li>
						</c:if>
					</ul>
				</div>
			</div>
		</nav>
	</header>