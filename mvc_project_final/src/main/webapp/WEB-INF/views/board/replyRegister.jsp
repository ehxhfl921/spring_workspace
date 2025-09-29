<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>
<main class="flex-grow-1">
  <div class="container my-5">
    <div class="row d-flex justify-content-center">
      <div class="col-md-8">
        <div class="card shadow">
          <div class="card-header bg-dark text-white text-center">
            <h3 class="mb-0">REPLY REGISTER BOARD</h3>
          </div>
          <div class="card-body">
            <form id="registerForm" action="replyRegister" method="POST">
              <!-- 제목 -->
              <div class="mb-3">
                <label for="title" class="form-label">제목</label>
                <input type="text" id="title" name="title" class="form-control" required/>
              </div>

              <!-- 작성자 -->
              <div class="mb-3">
                <label class="form-label">작성자</label>
                <input type="text" class="form-control" value="" readonly/>
              </div>

              <!-- 내용 -->
              <div class="mb-3">
                <label for="content" class="form-label">내용</label>
                <textarea id="content" name="content" class="form-control" rows="10"></textarea>
              </div>

              <!-- 버튼 -->
              <div class="text-center">
                <button type="submit" id="saveBtn" class="btn btn-primary px-4">등록</button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</main>
</body>
</html>












