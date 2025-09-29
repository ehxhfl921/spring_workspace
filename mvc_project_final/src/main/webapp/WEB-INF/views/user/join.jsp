<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- /WEB-INF/views/user/join.jsp -->
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<style>
	
	/* profile image */
	#uploadImage{
		width:150px;
		height:150px;
		border-radius:75px;
		border:1px solid #ccc;	
	}

	/* 유효성 검사 버튼 */
	#sendSMS, #codeWrap, #acceptEmail, #emailCodeWrap{
		display:none;
	}
	
	div.alertDiv{
		display:none;
		position:fixed;
		width:100%;
		height:100%;
		background-color:rgba(0,0,0,0.8);
		top:0;
		left:0;
		bottom:0;
		right:0;
		justify-content: center;
		align-items: center;
		z-index:9999;
	}
	
	.contentWrap{
		border:1px solid white;
		border-radius:10px;
		width:430px;
		height:430px;
		color:white;
		display:flex;
		justify-content: center;
		align-items: center;
		flex-direction: column;
	}
	
	.textWrap{
		padding:15px;
	}
</style>

<!-- 메일 전송 시 로딩 알림창 -->
<div class="alertDiv" id="alertDiv">
	<div class="contentWrap">
		<img src="${path}/resources/img/loading.gif" width="300"/>
		<div class="textWrap">
			<span id="message"></span>
		</div>
	</div>
</div>

  <main class="flex-grow-1">
	<div class="container my-5">
		<div class="row d-flex justify-content-center">
			<div class="col-md-5">
			  <div class="card shadow">
			    <div class="card-header bg-dark text-white text-center">
			      <h3>JOIN PAGE</h3>
			    </div>
			    <div class="card-body">
			      <form id="joinForm" action="${path}/user/joinPost" method="POST" enctype="multipart/form-data">
			        
			        <!-- 프로필 이미지 -->
			        <div class="mb-3 text-center">
			          <img src="${path}/resources/img/profile.jpg" id="uploadImage" class="img-thumbnail"/>
			          <div class="mt-2">
			            <input class="form-control" type="file" id="profileImage" name="profileImage" accept="image/*"/>
			          </div>
			        </div>
			
			        <!-- 아이디 (이메일) -->
			        <div class="mb-3">
			          <label for="u_id" class="form-label">아이디 (이메일)</label>
			          <div class="input-group">
			            <input type="text" class="form-control" name="u_id" id="u_id" autocomplete="off">
			            <button type="button" class="btn btn-outline-secondary" id="acceptEmail">이메일 인증</button>
			          </div>
			          <div class="result text-danger small"></div>
			          <div id="emailCodeWrap" class="mt-2 d-flex gap-2">
			            <input type="text" class="form-control" id="emailCode" placeholder="인증번호 입력">
			            <input type="button" class="btn btn-success" id="emailAcceptBtn" value="인증" >
			          </div>
			        </div>
			
			        <!-- 비밀번호 -->
			        <div class="mb-3">
			          <label for="u_pw" class="form-label">비밀번호</label>
			          <input type="password" class="form-control" name="u_pw" id="u_pw">
			          <div class="result text-danger small"></div>
			        </div>
			
			        <!-- 비밀번호 확인 -->
			        <div class="mb-3">
			          <label for="re_pw" class="form-label">비밀번호 확인</label>
			          <input type="password" class="form-control" name="re_pw" id="re_pw">
			          <div class="result text-danger small"></div>
			        </div>
			
			        <!-- 이름 -->
			        <div class="mb-3">
			          <label for="u_name" class="form-label">이름 (한글 2~6자 이내)</label>
			          <input type="text" class="form-control" name="u_name" id="u_name">
			          <div class="result text-danger small"></div>
			        </div>
			
			        <!-- 생년월일 -->
			        <div class="mb-3">
			          <label for="u_birth" class="form-label">생년월일 (ex: 19950505)</label>
			          <input type="text" class="form-control" name="u_birth" id="u_birth">
			          <div class="result text-danger small"></div>
			        </div>
			
			        <!-- 주소 -->
			        <div class="mb-3">
			          <label class="form-label">주소</label>
			          <div class="input-group mb-2">
			            <input type="text" class="form-control" readonly name="u_addr_post" id="u_addr_post" placeholder="우편번호">
			            <button type="button" class="btn btn-outline-primary" id="findAddr">주소찾기</button>
			          </div>
			          <input type="text" class="form-control mb-2" readonly name="u_addr" id="u_addr" placeholder="주소">
			          <input type="text" class="form-control" name="u_addr_detail" id="u_addr_detail" placeholder="상세주소">
			        </div>
			
			        <!-- 전화번호 -->
			        <div class="mb-3">
			          <label for="u_phone" class="form-label">전화번호 (- 제외, 숫자만 입력)</label>
			          <div class="input-group">
				          <input type="text" class="form-control" name="u_phone" id="u_phone">
				          <input type="button" class="btn btn-outline-secondary" value="인증코드 전송" id="sendSMS"/>
			          </div>
			          <div class="result text-danger small"></div>
					  <div id="codeWrap"  class="mt-2 d-flex gap-2">
						<input type="text"  class="form-control" id="code"/>
						<input type="button" class="btn btn-success" id="acceptCode" value="인증"/>
					  </div>
			        </div>
			
			        <!-- 개인정보처리방침 -->
			        <div class="mb-3">
			          <label class="form-label">개인정보처리방침</label>
			          <textarea class="form-control" rows="4" readonly>당신의 개인정보는 언제든지 회사에서 팔아먹을 수 있으며 3자에게 항상 양도 됩니다. 그래도 이용하시겠습니까?</textarea>
			        </div>
			
			        <!-- 개인정보 동의 -->
			        <div class="mb-3 form-check">
			          <input type="checkbox" class="form-check-input" name="u_info" id="u_info" value="y">
			          <label class="form-check-label" for="u_info">개인정보 이용 동의</label>
			          <div class="result text-danger small"></div>
			        </div>
			
			        <!-- Submit -->
			        <div class="text-center">
			          <button type="button" id="joinBtn" class="btn btn-primary px-4">회원가입</button>
			        </div>
			
			      </form>
			    </div>
			  </div>
			</div>
		</div>
	</div>
</main>
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script>
		document.querySelector("#findAddr").onclick = function(){
		    new daum.Postcode({
		        oncomplete: function(data) {
		            console.log(data);
		            
		            var fullAddr = "";			// 최종 주소
		            var extraAddr = "";			// 조합형 주소
		            var postCode = "";			// 우편번호
					
		            if(data.userSelectedType == 'R'){
		            	// 도로명 주소
		            	fullAddr = data.roadAddress;
		            	
		            	// 법정동명
		            	if(data.bname !== ''){
		            		extraAddr += data.bname;
		            	}
		            	
		            	// 건물명
		            	if(data.buildingName !== ''){
		            		extraAddr += (extraAddr !== '' ? ', '+data.buildingName : data.buildingName);
		            	}
		            	
		            	fullAddr += extraAddr !== '' ? '('+extraAddr+')' : '';
		            	
		            }else{
		            	// 지번 주소
		            	fullAddr = data.jibunAddress;
		            }
		            // 우편번호
		            postCode = data.zonecode;
		            // 입력필드에 값 넣기
		            document.querySelector("#u_addr_post").value = postCode;
		            document.querySelector("#u_addr").value= fullAddr;
		            document.querySelector("#u_addr_detail").focus();
		        }
		    }).open();
		};
	</script>
	
	<script>
	  // 기본 프로필 이미지 경로
	  const uploadImage = document.getElementById("uploadImage");
	  const profileImage = document.getElementById("profileImage");
	  const imagePath = uploadImage.getAttribute("src");

	  // 파일 선택 시 이벤트
	  profileImage.onchange = function () {
	    const files = this.files;
	    console.log(files);
	    const file = files[0];

	    if (file && file.type.startsWith("image/")) {
	      // image 형식의 파일
	      console.log(file.type);
	      const path = URL.createObjectURL(file); // 로컬 미리보기 경로
	      uploadImage.setAttribute("src", path);
	    } else {
	      alert("이미지를 선택해주세요.");
	      this.value = ""; // 선택 초기화
	      uploadImage.setAttribute("src", imagePath); // 원래 이미지로 복원
	    }
	  };
	</script>

<!-- 검증 -->
<script>
	document.querySelector("#u_id").focus();
	
	//(메세지를 보여줄 요소,검사할 값,비교할 정규식,출력할메세지,비동기 통신 함수)
	function checkRegex(elP,valP,regexP,messageP,ajaxP){
		// 정규표현식이 일치하지 않을 때
		if(regexP.test(valP) === false){
			showMessage(elP,messageP,false);
			return false;
		// 정규표현식 일치하고 ajax가 존재하지 않을때
		}else if(ajaxP === null){
			showMessage(elP,messageP,true);
			return true;
		}else{
			ajaxP(elP,valP);
		}
	}
	
	// (메세지를 보여줄 요소, 메세지,성공 실패 여부)
	function showMessage(elP,messageP,isCheck){
		var html = "<span style='margin-left:5px;font-size:12px;";
		html += isCheck ? "color:green;" : "color:red;"
		html += "'>";
		html += messageP;
		html += "</span>";
		console.log(elP, html);
		elP.innerHTML = html;
	}
	
	// u_id 검증 여부 
	var boolUid = false;
	var regexEmail =/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;       // 이메일
	
	let u_id = document.querySelector("#u_id");
	
	// 입력태그에 값이 작성 될때
	u_id.oninput = function(){
		var tempVal = u_id.value;
		// var elP = u_id.parentElement.parentElement.querySelector(".result");
		var elP = u_id.closest(".mb-3").querySelector(".result");
		var message = "올바른 이메일 형식이 아닙니다.";
		document.getElementById("acceptEmail").style.display = "none";
		boolUid = checkRegex(elP,tempVal,regexEmail,message,checkUidAjax);
		if(boolUid) showMessage(elP, "사용가능한 아이디입니다." , true);
	};
	
	// 서버에 등록된 아이디 인지 확인 - ajax
	function checkUidAjax(elP,valP){
		fetch(`${path}/user/uidCheck?u_id=\${valP}`)
        .then(res=>res.json())
        .then(result=>{
        	console.log(result);
            boolUid = result;
            showMessage(elP, boolUid?"사용가능한 아이디입니다.":"이미 존재하는 아이디입니다.", boolUid);
            document.getElementById("acceptEmail").style.display = boolUid ? "flex" : "none";
        })
        .catch(err=>alert("서버 오류 발생"));
	} // checkUid
	

	/* 이메일 인증 */
	let emailCode = "";
	let boolEmailCode = false;
	let acceptEmail = document.getElementById("acceptEmail");
	let emailCodeWrap = document.getElementById("emailCodeWrap");

	acceptEmail.addEventListener("click", ()=>{
		
	    document.getElementById("message").innerText="메일 발송 중...";
	    
	    document.getElementById("alertDiv").style.display="flex";
	    
	    fetch(`${path}/checkEmail?u_id=\${u_id.value}`)
	    .then(res=>res.text())
	    .then(code=>{
	        document.getElementById("alertDiv").style.display="none";
	        if(code){
	            emailCode = code;
	            alert("메일 발송 완료\n메일을 확인해주세요.");
	            emailCodeWrap.style.display="block";
	        }else{
	            alert("메일 발송 실패 관리자에게 문의해주세요.");
	        }
	    })
	    .catch(()=>{
	        document.getElementById("alertDiv").style.display="none";
	        alert("메일 발송 실패 관리자에게 문의해주세요.");
	    });
	});


	let userCode = document.getElementById("emailCode");

	document.getElementById("emailAcceptBtn").addEventListener("click", ()=>{
	    let userCodeVal = userCode.value;
	    if(emailCode != "" && emailCode === userCodeVal){
	        boolEmailCode = true;
	        alert("이메일 인증 완료");
	        emailCodeWrap.style.display="none";
	    }else{
	        boolEmailCode = false;
	        alert("인증코드를 다시 확인해주세요.");
	    }
	});

	/* 비밀번호 */
	let boolPassword = false;
	let boolPassCheck = false;

	//특수문자 포함 비밀번호
	let regexPass=/^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$/;

	let u_pw = document.getElementById("u_pw");

	u_pw.addEventListener("input", e =>{
		
		var el = u_pw.closest(".mb-3").querySelector(".result");
	    
	    let msg = "특수문자 포함 영문/숫자 조합하여 8~16자 이내 작성";
	    
	    boolPassword = checkRegex(el,e.target.value,regexPass,msg,null);
	    
	    if(boolPassword) showMessage(el, "사용가능합니다.", true);
	});

	let re_pw = document.getElementById("re_pw");

	re_pw.addEventListener("input", e => {
	    let val = e.target.value;
	    let original = document.getElementById("u_pw").value;
	    var el = re_pw.closest(".mb-3").querySelector(".result");
	    if(boolPassword){
	        boolPassCheck = (val === original);
	        let message = boolPassCheck ? "비밀번호가 일치합니다.":"비밀번호가 일치하지 않습니다.";
	        showMessage(el, message , boolPassCheck);
	    }else{
	        showMessage(el,"비밀번호를 먼저 확인해 주세요.",false);
	    }
	});

	/* 이름 */
	let boolName = false;
	let regexName=/^[\uac00-\ud7a3]{2,6}$/;
	let u_name = document.getElementById("u_name");

	u_name.oninput = function(e){
		var el = u_name.closest(".mb-3").querySelector(".result");
	    boolName = checkRegex(el, u_name.value, regexName, "2~6글자 이내 한글로 작성", null);
	    if(boolName){
	    	showMessage(el, "사용가능합니다.", true);
	    }
	};

	/* 생년월일 */
	let boolBirth = false;
	let regexBirth = /^[0-9]{8}$/;

	let u_birth = document.getElementById("u_birth");

	u_birth.oninput = function(){
		var el = u_birth.closest(".mb-3").querySelector(".result");
	    boolBirth = checkRegex(el, u_birth.value, regexBirth, "19900505 형식으로 작성해주세요.", null);
	    if(boolBirth){
	    	showMessage(el, "사용가능합니다.", true);
	    }
	};

	/* 전화번호 */
	let boolPhone = false;
	let boolSMS = false;
	let smsCode = "";
	let regexMobile=/^[0-9]{10,11}$/;

	let u_phone = document.getElementById("u_phone");

	u_phone.addEventListener("input", e=>{
		var el = u_phone.closest(".mb-3").querySelector(".result");
	    boolPhone=checkRegex(el, u_phone.value, regexMobile, "- 제외 숫자만 입력해주세요.", null);
	    let sendSMSBtn = document.querySelector("#sendSMS");
	    if(boolPhone){
	    	sendSMSBtn.style.display = "flex";
	    	sendSMSBtn.disabled = false;
	        document.getElementById("codeWrap").style.display="none";
	    }else{
	    	sendSMSBtn.style.display = "none";
	    	sendSMSBtn.disabled = true;
	    }
	});

	/* SMS 발송 */

	let codeWrap = document.getElementById("codeWrap");
	 
	document.getElementById("sendSMS").addEventListener("click", ()=>{
	    if(!boolPhone){
	        alert("전화번호를 먼저 확인해 주세요.");
	        return;
	    }
	    document.getElementById("message").innerText="문자 발송 중...";
	    
	    document.getElementById("alertDiv").style.display="flex";
	    
	    fetch(`${path}/user/sendSMS`, {
	        method:"POST",
	        headers:{'Content-Type':'application/x-www-form-urlencoded'},
	        body : "userPhoneNumber="+u_phone.value
	    })
	    .then(res => res.json())
	    .then(data => {
	    	document.getElementById("alertDiv").style.display="none";
	    	console.log(data);
	        if(data.result == 2000){
	        	alert("인증 문자가 발송되었습니다.");
	            smsCode = data.code;
	            console.log(smsCode);
	            codeWrap.style.display = "block";
	        }
	    }).catch(error =>{
	    	alert("문자 발송이 실패하였습니다. 다시 확인해주세요.");
	    	document.getElementById("alertDiv").style.display="none";
	    	console.log(error);
	    });
	});

	/* SMS 코드 확인 */
	document.getElementById("acceptCode").addEventListener("click", ()=>{
	    let userCode = document.getElementById("code").value;
	    if(smsCode && userCode && smsCode===userCode){
	        boolSMS = true;
	        alert("인증 완료");
	        codeWrap.style.display="none";
	    }else{
	        alert("발송된 인증코드를 다시 확인해주세요.");
	    }
	});

	/* 가입 버튼 */
	document.getElementById("joinBtn").addEventListener("click", ()=>{
		
	    let addrInputs=document.querySelectorAll(".addr");
	    
	    let addrOk = true;
	    let addrObj = null;
	    let hint="";
	    
	    addrInputs.forEach(input=>{
	        if(!input.value && addrOk){
	            addrOk=false; 
	            addrObj=input; 
	            hint=input.placeholder;
	            return;
	        }
	    });

	    // test 시 이메일 인증, 문자 인증 확인 제외
	    boolEmailCode = true;
	    boolSMS = true;
	    
	    
	    if(!boolUid) {
	    	alert("아이디를 확인해 주세요.");
	    	u_id.focus();
	    	return;
	    }
	    
	    if(!boolEmailCode) {
	    	userCode.focus();
	    	alert("이메일 인증을 완료해 주세요.");
	    	return ;
	    }
	    
	    if(!boolPassword) {
	    	alert("비밀번호를 확인해 주세요.");
	    	u_pw.focus();
	    	return; 
	    }
	    
	    if(!boolPassCheck) {
	    	alert("비밀번호 일치여부를 확인해 주세요.");
	    	re_pw.focus();
	    	return; 
	    }
	    
	    if(!boolName) {
	    	u_name.focus();
	    	alert("이름 입력란을 확인해 주세요.");
	    	return;
	    }
	    
	    if(!boolBirth) {
	    	u_birth.focus();
	    	alert("생년월일을 확인해 주세요.");
	    	return;
	    }
	    
	    if(!addrOk){ 
	    	alert(hint+"를 확인해 주세요."); 
	    	addrObj.focus(); 
	    	return;
	   	}
	    
	    if(!boolPhone) {
	    	
	    	alert("전화번호를 확인해 주세요.");
	    	return;
	    }
	    
	    if(!boolSMS) {
	    	alert("전화번호 인증을 완료해 주세요.");
	    	return; 
	    }
	    
	    let u_info = document.getElementById("u_info");
	    if(!u_info.checked) {
	    	u_info.focus();
	    	alert("개인정보 이용동의를 확인해주세요.");
	    	return; 
	    }

	    document.getElementById("joinForm").submit();
	});
</script>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>









