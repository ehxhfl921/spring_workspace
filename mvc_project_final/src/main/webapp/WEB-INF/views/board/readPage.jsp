<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<style>
	.uploadList{
		width:100%;
	}
	
	.uploadList li{
		text-align:center;
		display:inline-block;
		padding:20px;
		list-style:none;
	}
</style>
</head>
<body>

<main class="flex-grow-1">
  <div class="container my-5">
    <div class="row d-flex justify-content-center">
      <div class="col-md-8">
        <div class="card shadow">
          <!-- 헤더 -->
          <div class="card-header bg-dark text-white text-center">
            <h3 class="mb-0">READ BOARD</h3>
          </div>

          <!-- 본문 -->
          <div class="card-body">
            <!-- 리스트 목록 이동 -->
            <div class="text-end mb-3">
              <a href="${path}/board/searchList" class="btn btn-secondary btn-sm">리스트 목록</a>
            </div>

            <!-- 게시글 정보 -->
            <table class="table table-bordered">
              <tr>
                <th class="bg-light" style="width: 20%;">제목</th>
                <td>${board.title}</td>
              </tr>
              <tr>
                <th class="bg-light">작성자</th>
                <td>${board.writer}</td>
              </tr>
              <tr>
                <th class="bg-light">내용</th>
                <td style="white-space: pre-line;">${board.content}</td>
              </tr>
            </table>

			<c:if test="${!empty board.files}">
	            <!-- 첨부파일 -->
	            <div class="mb-3">
	              <h5 class="mt-4">첨부파일</h5>
	              <ul class="uploadList list-group">
	              	<c:forEach var="file" items="${board.files}">
                    <li class="list-group-item d-flex align-items-center" data-src=''>
                          <c:choose>
                          	<c:when test="${fn:contains(file, 's_')}">
                          		<!-- 썸네일 이미지 파일 -->
                          		<img src="${path}/displayFile?fileName=${file}"
                          			 alt="첨부 이미지" class="img-thumbnail me-3 rounded"
                          			 style="width:40px;height:40px;object-fit:cover;">
                        		<a href="${path}/displayFile?fileName=${fn:replace(file, 's_', '')}"
                        		   class="text-decoration-none" target="_blank">
                        		   ${fn:substringAfter(fn:substringAfter(file, "s_"), "_")}
                        		</a>
                          	</c:when>
                          	<c:otherwise>
                          		<!-- 일반 파일 -->
	                          	<img src="${path}/resources/img/file.png" 
	                               	alt="파일 아이콘" 
	                               	class="me-3" 
	                               	style="width:40px;"/>
	                          	<a href="${path}/displayFile?fileName=${file}" class="text-decoration-none"
	                          	   target="_blank">
	                            	${fn:substringAfter(file, "_")}
	                          	</a>
                          	</c:otherwise>
                          </c:choose>
                    </li>
                    </c:forEach>
	              </ul>
	            </div>
			</c:if>
            <!-- 수정 / 삭제 버튼 -->
            <div class="text-center mt-4">
            	<c:if test="${!empty userInfo}">
	            	<!-- 원본글 일 경우 -->
	            	<button type="button" id="replyBtn" class="btn btn-primary me-2">답변 달기</button>
	                <c:if test="${board.u_no eq userInfo.u_no}">
	                	<!-- 상세 보기 한 현재 사용자가 작성한 게시물 -->
		                <button type="button" id="modifyBtn" class="btn btn-warning me-2">수정</button>
		                <button type="button" id="removeBtn" class="btn btn-danger">삭제</button>
	                </c:if>
                </c:if>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</main>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>