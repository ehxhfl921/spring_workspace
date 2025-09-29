<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp"%>
<!-- Main Content -->
<main class="flex-grow-1">
	<div class="container py-4" id="content">
		<h1 class="mb-4">HOME PAGE MAIN TITLE</h1>
		<div class="container my-5">
			<div class="card shadow">
				<div class="card-header bg-dark text-white text-center">
					<h3>언론사별 실시간 많이 본 뉴스</h3>
				</div>
				<div class="card-body">
				<c:choose>
					<c:when test="${!empty jsoupNews}">
						<ul class="list-unstyled">
							<c:forEach var="news" items="${jsoupNews}">
								<li class="mb-3 d-flex align-items-center">
									<img src="${news.img}" class="me-2 rounded">
									<a href="${news.link}" target="_blank">${news.title}</a>
								</li>
							</c:forEach>
						</ul>				
					</c:when>
					<c:otherwise>
						<!-- content 추가 -->
						<p>CONTENT 가 없어용</p>
					</c:otherwise>
				</c:choose>
				</div>
			</div>
		</div>
	</div>
</main>
<%@ include file="/WEB-INF/views/common/footer.jsp"%>