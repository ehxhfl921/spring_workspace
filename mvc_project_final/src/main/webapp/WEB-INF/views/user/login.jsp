<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
  <main class="flex-grow-1">
	<div class="container my-5">
		<div class="row d-flex justify-content-center">
			<div class="col-md-5">
			  <div class="card shadow">
			    <div class="card-header bg-dark text-white text-center">
			      	<h1>LOGIN PAGE</h1>
			      	<h2>
			      		${message}
			      		<c:if test="${!empty time}">
			      			<span id="time" class="badge bg-primary">${time}</span>
			      		</c:if>
			      	</h2>
			    </div>
			    <div class="card-body">
					<form action="${path}/user/login" method="post">
						<div class="mb-3">
				          <label for="u_id" class="form-label">아이디</label>
				          <input type="email" class="form-control" name="u_id">
				        </div>
						 <div class="mb-3">
				          <label for="u_pw" class="form-label">비밀번호</label>
				          <input type="password" class="form-control" name="u_pw">
				        </div>
	        	        <div class="mb-3 form-check">
				          <input type="checkbox" class="form-check-input" name="rememberme" id="rememberme">
				          <label class="form-check-label" for="rememberme">로그인 정보 저장</label>
				        </div>
						 <div class="text-center">
				         	 <button type="submit" class="btn btn-primary px-4">로그인</button>
				         	 <button type="button" class="btn btn-primary px-4" onclick="location.href='${path}/user/join';">회원가입</button>
				        </div>
					</form>
		 		</div>
			  </div>
		  </div>
	  </div>
	</div>
</main>

<c:if test="${!empty time}">
	<script>
	// interval 을 저장할 변수
	let updateInterval;

	let remainingTime = ${time};
	
	function formatTime(seconds){
		let time = new Date(seconds);
		let minutes = time.getMinutes();
		minutes = minutes < 10 ? "0" + minutes : minutes;
		let second = time.getSeconds();
		second = second < 10 ? "0" + second : second;
		return minutes + ":" + second;
	}
	
	function updateTimer(){
		
		if(remainingTime < 0){
			clearInterval(updateInterval); // interval 제거
		}
		
		document.getElementById("time").textContent = formatTime(remainingTime);
		remainingTime = remainingTime - 1000;
		
	}
	
	updateTimer(); // 초기 화면
	
	// updateTimer 함수를 1000(1초) 간격으로 반복 실행
	updateInterval = setInterval(updateTimer, 1000);
		
	</script>
</c:if>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>