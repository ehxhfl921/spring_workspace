<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<style>
  
	.title{
		max-width:400px;
	}

</style>

<script>
	let msg = '${msg}';
	if(msg != ''){
		alert(msg);
	}
</script>

<main class="flex-grow-1">
  <div class="container my-5">
	  <c:if test="${!empty pm}">
	    <!-- 검색 / 옵션 영역 -->
	    <div class="card shadow mb-4">
	      <div class="card-body">
	        <form class="row g-3 align-items-center" action="${path}/board/searchList">
	          <!-- 검색 타입 -->
	          <div class="col-auto">
	            <select name="searchType" class="form-select">
	              <option value="n" ${pm.criteria.searchType eq 'n' ? "selected" : "" }>--------------</option>
	              <option value="t" ${pm.criteria.searchType eq 't' ? "selected" : "" }>TITLE</option>
	              <option value="c" ${pm.criteria.searchType eq 'c' ? "selected" : "" }>CONTENT</option>
	              <option value="w" ${pm.criteria.searchType eq 'w' ? "selected" : "" }>WRITER</option>
	              <option value="tc" ${pm.criteria.searchType eq 'tc' ? "selected" : "" }>TITLE &amp; CONTENT</option>
	              <option value="tw" ${pm.criteria.searchType eq 'tw' ? "selected" : "" }>TITLE &amp; WRITER</option>
	              <option value="tcw" ${pm.criteria.searchType eq 'tcw' ? "selected" : "" }>TITLE &amp; WRITER &amp; CONTENT</option>
	            </select>
	          </div>
	          <!-- 검색어 입력 -->
	          <div class="col-auto">
	            <input type="text" name="keyword" value="${pm.criteria.keyword}" class="form-control" placeholder="검색어 입력"/>
	          </div>
	          <!-- 검색 버튼 -->
	          <div class="col-auto">
	            <button type="submit" class="btn btn-primary">검색</button>
	          </div>
	          <!-- 페이지당 게시글 수 -->
	          <div class="col-auto">
	            <select name="perPageNum" class="form-select" onchange="this.form.submit();"> 
	              <c:forEach var="i" begin="10" end="30" step="5">
	                <option value="${i}" ${pm.criteria.perPageNum eq i ? 'selected' : ''}>${i}개씩 보기</option>
	              </c:forEach>
	            </select>
	          </div>
	        </form>
	      </div>
	    </div>
    </c:if>
    <c:if test="${!empty userInfo}">
	    <!-- 글작성 버튼 (로그인 상태일 경우) -->
	     <div class="mb-3 text-end">
	       <form action="${path}/board/register">
	         <input type="submit" class="btn btn-success" value="글작성" />
	       </form>
	     </div>
	</c:if>
    <!-- 게시글 목록 -->
    <div class="card shadow">
      <div class="card-header bg-dark text-white">
        <h5 class="mb-0">게시판 목록</h5>
      </div>
      <div class="card-body p-0">
        <table class="table table-hover mb-0 text-center align-middle">
          <thead class="table-light">
            <tr>
              <th>BNO</th>
              <th class="text-start">TITLE</th>
              <th>WRITER</th>
              <th>REGDATE</th>
              <th>VIEWCNT</th>
            </tr>
          </thead>
          <tbody>
          	<c:choose>
          		<c:when test="${!empty list}">
					<!-- 출력할 게시글 목록(list)이 존재하는 경우 -->
					<c:forEach var="board" items="${list}">
						<tr>
			                <td>${board.bno}</td>
			                <td class="text-start text-truncate title">
			                  <a href="readPage?bno=${board.bno}" class="text-decoration-none">
			                  	${board.title}
			                  </a>
			                </td>
			                 <td>${board.writer}</td>
			                <td>
			                	<f:formatDate value="${board.regdate}" pattern="yyyy-MM-dd (E) HH:mm" />
			               	</td>
			                <td>${board.viewcnt}</td>
			              </tr>
					</c:forEach>
          		</c:when>
          		<c:otherwise>
          			<!-- 게시글 목록이 존재하지 않을 경우 -->
          			<!-- 게시글 목록이 없을 경우 -->
	                <tr>
	                  <td colspan="5" class="text-center text-muted">등록된 게시물이 없습니다.</td>
	                </tr>
          		</c:otherwise>
          	</c:choose>
          	<tr>
				<td>9</td>
				<td class="text-start text-truncate title">
				  <a href="readPage?bno=${board.bno}" class="text-decoration-none">
				<span style="padding-left:20px;">↳ </span>
				         	답변 게시글
				  </a>
		       	</td>
		       	<td>언론인</td>
		       	<td>
		       		2025-01-13 (화) 09:20
		      	</td>
		       	<td>100</td>
			</tr>
              <!-- 삭제 게시글 -->
              <tr class="table-danger">
                  <td colspan="5">삭제된 게시물 입니다.</td>
              </tr>
              
          </tbody>
        </table>
      </div>

      <!-- 페이징 -->
      <c:if test="${!empty pm}">
	      <div class="card-footer text-center">
	        <nav>
	          <ul class="pagination justify-content-center mb-0">
	            <c:if test="${pm.moveFirst}">
	              <li class="page-item"><a class="page-link" href="${pm.makeQuery(1)}">처음</a></li>
	            </c:if>
	            <c:if test="${pm.movePrev}">
	              <li class="page-item"><a class="page-link" href="${pm.makeQuery(pm.prev)}">이전</a></li>
	            </c:if>
	            <c:forEach var="i" begin="${pm.startPage}" end="${pm.endPage}">
	              <li class="page-item ${pm.criteria.page eq i ? 'active' : ''}">
	                <a class="page-link" href="${pm.makeQuery(i)}">${i}</a>
	              </li>
	            </c:forEach>
	            <c:if test="${pm.moveNext}">
	              <li class="page-item"><a class="page-link" href="${pm.makeQuery(pm.next)}">다음</a></li>
	            </c:if>
	            <c:if test="${pm.moveLast}">
	              <li class="page-item"><a class="page-link" href="${pm.makeQuery(pm.last)}">마지막</a></li>
	            </c:if>
	          </ul>
	        </nav>
	      </div>
        </c:if>
    </div>
  </div>
</main>

<%@ include file="../common/footer.jsp" %>








