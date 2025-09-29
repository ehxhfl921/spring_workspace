<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<style>
	table{
		max-width: 500px;
		margin:0 auto;
	}
	
	#acceptEmail, #emailCodeWrap{
		display:none;
	}
	
	.result span{
		margin-left: 3px;
		font-size: 12px;
	}
	
	.uploadImage{
		width:100px;
		height:100px;
		border-radius: 50px;
		border: 1px solid #ccc;
	}
	
</style>
<form id="joinForm" action="${path}/user/joinPost" method="post" enctype="multipart/form-data">
	<table border="1">
		<tr>
			<th colspan="2"><h1>JOIN PAGE</h1></th>
		</tr>
		<tr>
			<td>프로필 이미지</td>
			<td style="text-align:center; padding:5px;">
				<img src="${path}/resources/img/profile.jpg" class="uploadImage"><br>
				<input type="file" name="profileImage" accept="image/*" id="profileImage">
			</td>
		</tr>
		<tr>
			<td>아이디(email)</td>
			<td>
				<input type="text" name="u_id" id="u_id" autocomplete="off">
				<input type="button" id="acceptEmail" value="이메일 인증">
				<div class="result"></div> 
				<div id="emailCodeWrap">
					<input type="text" id="userCode" placeholder="코드 입력">
					<button type="button" id="emailAcceptBtn">인증 완료</button>
				</div>
			</td>
		</tr>
		<tr>
			<td>비밀번호</td>
			<td>
				<input type="password" name="u_pw" id="u_pw">
				<div class="result"></div>
			</td>
		</tr>
		<tr>
			<td>비밀번호 확인</td>
			<td>
				<input type="password" id="re_pw">
				<div class="result"></div>
			</td>
		</tr>
		<tr>
			<td>이름(한글 2~6자 이내)</td>
			<td>
				<input type="text" name="u_name" id="u_name">
				<div class="result"></div>
			</td>
		</tr>
		<tr>
			<td>전화번호(- 제외 숫자만 입력)</td>
			<td>
				<input type="text" name="u_phone" id="u_phone" placeholder="전화번호 입력">
				<input type="button" value="인증 코드 전송" id="sendSMS">
				<div class="result"></div>
				<div id="codeWrap">
					<input type="text" id="code" placeholder="인증 코드 작성">
					<input type="button" id="acceptCode" value="인증">
				</div>
			</td>
		</tr>
		<tr>
			<td>생년월일(20010205) 형식</td>
			<td>
				<input type="text" name="u_birth" id="u_birth">
				<div class="result"></div>
			</td>
		</tr>
		<tr>
			<td>주소</td>
			<td>
				<div>
					<input type="text" name="u_addr_post" id="u_addr_post" class="addr"
						   placeholder="우편번호" readonly>
					<input type="button" value="주소 찾기" onclick="daumApiCode();">
				</div>
				<input type="text" name="u_addr" id="u_addr" class="addr"
						   placeholder="주소" readonly>
				<input type="text" name="u_addr_detail" id="u_addr_detail" class="addr"
						   placeholder="상세주소">
			</td>
		</tr>
		<tr>
			<td>개인 정보 이용 동의</td>
			<td>
				<h4>개인 정보 이용 약관</h4>
				<textarea readonly rows="5" cols="50">당신의 개인 정보 어쩌구 제3자에게 제공이 저쩌구..
				</textarea>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<label>
					<input type="checkbox" name="u_info" id="u_info" value="y"> 개인 정보 이용 동의
				</label>
			</td>
		</tr>
		<tr>
			<th colspan="2">
				<input type="button" id="joinBtn" value="회원 등록">
			</th>
		</tr>
	</table>
	
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script>
		function daumApiCode(){
			// 함수 실행 시 주소 검색창 오픈
		    new daum.Postcode({
		        oncomplete: function(data) {
		            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분입니다.
		            // 예제를 참고하여 다양한 활용법을 확인해 보세요.
		            console.log(data);
		            
		            let fullAddr = ""; 	// 최종 주소
		            let extraAddr = "";	// 조합형 주소(참조 항목이 있을 시 주소에 추가)
		            
		            if(data.userSelectedType == "R"){
		            	// 도로명 주소
		            	fullAddr = data.roadAddress;
		            	
		            	if(data.bname != ''){
		            		// 법정 동명이 존재하면
		            		extraAddr += data.bname;
		            	}
		            	
		            	if(data.buildingName != ''){
		            		extraAddr += extraAddr != '' ? ", " + data.buildingName : data.buildingName;
		            	}
		            	
		            	fullAddr += (extraAddr != '') ? "(" +extraAddr+")" : "";
		            }else{
		            	// 지번 주소 선택
		            	fullAddr = data.jibunAddress;
		            }
		            
		            
		            document.getElementById("u_addr_post").value = data.zonecode;
		            document.getElementById("u_addr").value = fullAddr;
		            document.getElementById("u_addr_detail").focus();
		            
		        } // end oncomplete
		    }).open();
		}
	</script>
	
	<!-- 프로필 이미지 미리보기 -->
	<script>
		let uploadImage = document.querySelector(".uploadImage");
		let profileImage = document.getElementById("profileImage");
		
		// 기본 프로필 이미지 경로 저장
		let imagePath = uploadImage.getAttribute("src");
		
		profileImage.onchange = function(){
			// 프로필 이미지 선택 입력 태그 요소 변경
			let files = this.files;
			console.log(files);
			let chooseFile = files[0];
			console.log(chooseFile);
			
			if(chooseFile && chooseFile.type.startsWith("image/")){
				// image 형식의 프로필 사용 가능한 파일 선택
				// chooseFile로 선택된 사용자 PC의 파일 경로를
				// 브라우저가 인식할 수 있는 URL 경로 생성
				let path = URL.createObjectURL(chooseFile);
				// 선택한 파일 경로로 이미지 출력 경로 변경
				uploadImage.setAttribute("src", path);
				
			}else{
				alert("이미지를 선택해 주세요.");
				this.value = ""; // 선택 파일 초기화
				uploadImage.setAttribute("src", imagePath); // 기본 이미지로 변경
			}
		
		};
	</script>
    
    <script>
    	
    	/*
    		정규식 검사 및 AJAX 결과 메세지 출력 함수
    	*/
    	// checkRegex(결과를 출력할 요소, 검증할 문자열, 정규 표현식, 출력 메세지, 서버 요청 ajax 함수)
    	function checkRegex(el, val, regex, message, ajax){
    		if(!regex.test(val)){
    			// 메세지 출력
    			showMessage(el, message, false);
    			return false;
    		}else if(!ajax){
    			// 메세지 출력
    			showMessage(el, message, true);
    			return true;
    		}else{
    			ajax(el, val);
    		}
    	} // checkRegex

    	// 유효성 검증 메세지 출력
    	// showMessage(메세지 출력 태그 요소, 출력 메세지, 사용 가능 여부)
    	function showMessage(el, msg, isCheck){
    		el.innerHTML = `<span style="\${isCheck ? 'color:green;' : 'color:red;'}">\${msg}</span>`;
    	}
    	
    	// 아이디 검증
    	let boolUid = false;	// 사용할 수 있는 아이디인지 여부를 저장할 변수
    	
    	let u_id = document.getElementById("u_id");
    	
    	var regexEmail =/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
    	
    	u_id.addEventListener("input", function(e){
    		// input : 키보드가 눌러졌을 때 e - keyEvent
    		let val = u_id.value;
    		console.log(val);
    		let el = e.target.parentElement.querySelector(".result");
    		console.log(el);
    		
    		let message = "올바른 이메일 형식이 아닙니다.";
    		
    		// 아이디(이메일) 작성란에 데이터를 변경하면 이메일 인증 버튼 비활성화
    		document.querySelector("#acceptEmail").style.display = "none";
    		
    		boolUid = checkRegex(el, val, regexEmail, message, checkUidAjax);
    		
    		/*
    		boolUid = regexEmail.test(val);
    		console.log("boolUid", boolUid);
    		let result = document.querySelector(".result");
    		result.innerHTML = boolUid ? `<span>사용할 수 있는 이메일입니다.</span>` : `<span>올바른 이메일 형식이 아닙니다.</span>`;
    		
    		if(boolUid){
    			// 중복 체크
    			checkUidAjax(result, val);
    		}
    		*/
    	});

    	// 중복 아이디 검사
    	function checkUidAjax(element, email){
    		// element : 결과를 출력할 태그 요소
    		// email : 사용 가능(중복) 검사할 이메일 주소
    		fetch("${path}/user/uidCheck?u_id="+email)
    		.then(res => res.json())
    		.then(result => {
    			// 중복 검사 결과
    			console.log(result);
    			boolUid = result;
    			let msg = boolUid ? "사용 가능한 아이디입니다." : "이미 존재하는 아이디입니다.";
    			// 이메일 버튼 활성화 여부 스타일 지정
    			let style = boolUid ? "inline-block" : "none";
    			
    			document.querySelector("#acceptEmail").style.display = style;
    			
    			showMessage(element, msg, boolUid);
    			
    		}).catch(err => alert("서버 오류"));
    	} // end checkUidAjax
    	
    	// 이메일 코드 발송 및 결과 비교 확인
    	let emailCode = ""; // 발신 코드 번호
    	let boolEmailCode = false; // code 확인 여부
    	
    	let acceptEmail = document.querySelector("#acceptEmail");
    	let emailCodeWrap = document.querySelector("#emailCodeWrap");
    	
    	acceptEmail.onclick = function(){
    		let email = u_id.value;
    		console.log("발송할 이메일 ", email);
    		
    		fetch("${path}/user/checkEmail?u_id="+email)
    		.then(res => res.text()) // text : 발신 코드 번호 반환
    		.then(code => {
    			console.log("발신 코드 : code", code);
    			if(code){
    				emailCode = code;
    				alert("메일 발송 완료\n메일을 확인해 주세요.");
    				emailCodeWrap.style.display = "block";
    			}else{
    				alert("메일 발송 실패\n관리자에게 문의하세요.");
    			}
    		});
    	} // end #acceptEmail button
    	
    	let userCode = document.getElementById("userCode"); // 코드 입력 태그 요소
    	
    	// 인증 완료 버튼 클릭 이벤트
    	document.getElementById("emailAcceptBtn").onclick = function(){
    		if(emailCode != '' && emailCode == userCode.value){
    			boolEmailCode = true;
    			alert("이메일 인증 완료");
    			emailCodeWrap.style.display = "none";
    		}else{
    			boolEmailCode = false;
    			alert("인증 코드를 다시 확인해 주세요.");
    		}
    	};
    	// 이메일 인증 -------------------------------------------------------------------
    	
    	// 비밀번호 확인
    	let boolPassword = false;
    	// 특수 문자 포함 비밀번호(8~16자)
    	var regexPass = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$/;
    	
    	let u_pw = document.querySelector("#u_pw");
    	
    	u_pw.oninput = function(){
    		// 결과를 출력할 result 태그 요소
    		let el = u_pw.parentElement.querySelector(".result");
    		let msg = "특수 문자, 숫자, 영문 포함 8~16자 이내로 작성";
    		boolPassword = checkRegex(el, u_pw.value, regexPass, msg);
    		if(boolPassword) showMessage(el, "사용 가능", true);
    	};
    	
    	// 비밀번호 확인 여부
    	let boolPassCheck = false;
    	
    	let re_pw = document.querySelector("#re_pw");
    	
    	re_pw.oninput = function(){
    		let el = re_pw.parentElement.querySelector(".result");
    		
    		if(boolPassword){ // 비밀번호 입력란 확인 여부
    			boolPassCheck = (re_pw.value == u_pw.value);
    			let msg = boolPassCheck ? "비밀번호가 일치합니다." : "비밀번호가 일치하지 않습니다.";
    			showMessage(el, msg, boolPassCheck);
    		}else{
    			showMessage(el, "비밀번호를 먼저 확인해 주세요.", false);
    		}
    	};
    	
    	// 비밀번호 확인 -----------------------------------------------------------------
    	
    	// 이름 필드 확인 ----------------------------------------------------------------
    	let boolName = false;
    	// 한글 2~6자 이내
    	var regexName = /^[\uac00-\ud7a3]{2,6}$/;
    	
    	let u_name = document.querySelector("#u_name");
    	
    	// u_name 입력 필드에 사용자가 데이터를 입력하면 이벤트 실행
    	u_name.oninput = function(){
    		let el = u_name.parentElement.querySelector(".result");
    		boolName = checkRegex(el, u_name.value, regexName, "2~6자 이내 한글로 작성");
    		if(boolName) showMessage(el, "사용 가능합니다.", true);
    	}; // 이름 체크
    	
    	
    	// 전화번호 체크 및 인증
		let boolPhone = false;	// 전화번호 형식 체크
		let boolSMS = false;	// 전화번호 인증 체크
		
		let smsCode = "";		// 발신 인증 코드 저장
		
		// mobile -표시 없이 숫자만
		var regexMobile = /^[0-9]{2,3}?[0-9]{3,4}?[0-9]{4}$/;
		
		let u_phone = document.querySelector("#u_phone");
		
		u_phone.addEventListener("input", function(){
			let el = u_phone.parentElement.querySelector(".result");
			boolPhone = checkRegex(el, u_phone.value, regexMobile, "전화번호형식으로 작성해주세요");
			if(boolPhone) showMessage(el, "전화번호를 인증해주세요.", true);
		});
		
		// 인증 문자 발송
		document.getElementById("sendSMS").onclick = function(){
			if(!boolPhone){
				alert("전화번호를 먼저 확인해 주세요.");
				u_phone.focus();
				return; // 이벤트 처리 종료
			}
			
			// 인증문자 발송 요청
			fetch("${path}/user/sendSMS",{
				method : "POST",
				headers : {
					"Content-Type" : "application/x-www-form-urlencoded"
				},
				body : "userPhoneNumber="+u_phone.value
			}).then(res => res.json())
			.then(data => {
				console.log(data);	
				if(data.result === '2000'){
					smsCode = data.code;
					alert("문자 발송 완료 인증코드를 확인해 주세요.");
				}
			}).catch(err => {
				console.log(err);	
			});
		}; // 인증 문자 발송
		
		// 사용자가 작성한 인증코드 입력 요소
		let userSMSCode = document.querySelector("#code");
		
		// 문자 인증 코드 확인
		document.querySelector("#acceptCode").onclick= function(){
			console.log(smsCode);
			console.log(userSMSCode.value);
			if(smsCode && userSMSCode.value === smsCode){
				boolSMS = true; // 인증 완료
				alert("인증이 완료되었습니다. 다음 정보를 입력해주세요.");
			}else{
				alert("발송된 인증코드를 다시 확인해주세요.");
				userSMSCode.value = ""; // 기존 코드 제거
				userSMSCode.focus();	 // 코드 입력란에 포커스
			}
		
		}; // 인증 코드 확인 - 인증 버튼 이벤트
		
		// 생년월일 확인
		var regexBirth = /^[0-9]{4}[0-9]{2}[0-9]{2}$/;
		let boolBirth = false;
		
		let u_birth = document.getElementById("u_birth");
		
		u_birth.oninput = function(){
			let el = u_birth.parentElement.querySelector(".result");
			boolBirth = checkRegex(el, u_birth.value, regexBirth, "9자로 생년월일을 작성해 주세요.");
			if(boolBirth) showMessage(el, "사용 가능합니다.", true);
		}; //  생년월일 입력 이벤트
    	
    	
    	// 회원 등록 버튼 클릭 시 입력 데이터 확인 후 데이터 등록 요청
    	document.getElementById("joinBtn").onclick = function(){
    		
    		boolUid = true;
    		boolEmailCode = true;
    		boolPhone = boolSMS = true;
    		
    		if(!boolUid){ // 아이디 검증 미확인
    			alert("아이디를 확인해 주세요.");
    			u_id.focus(); // 아이디 작성란에 포커스
    			return; // event 함수 종료
    		}
    		
    		if(!boolEmailCode){
    			userCode.focus();
    			alert("이메일 인증을 완료해 주세요.");
    			return;
    		}
    		
    		if(!boolPassword){ // 비밀번호 작성 미확인
    			u_pw.focus();
    			alert("비밀번호를 확인해 주세요.");
    			return;
    		}
    		
    		if(!boolPassCheck){ // 비밀번호 확인란 미체크
    			re_pw.focus();
    			alert("비밀번호를 확인해 주세요.");
    			return;
    		}
    		
    		if(!boolName){
    			u_name.focus();
    			alert("이름 작성란을 확인해 주세요.");
    			return;
    		}
    		
    		if(!boolPhone){
    			alert("전화번호를 확인해 주세요.");
    			u_phone.focus();
    			return;
    		}
    		
    		if(!boolSMS){
    			alert("전화번호 인증을 완료해 주세요.");
    			userSMSCode.focus();
    			return;
    		}
    		
    		// 주소 작성 확인
    		let addrInputs = document.querySelectorAll(".addr"); // class 이름이 addr인 모든 요소
    		
    		let addrOk = true;
    		let addrObject = null;
    		let hint = "";
    		
    		for(let i = 0; i < addrInputs.length; i++){
    			console.log(addrInputs[i]);
    			if(addrInputs[i].value == ''){
    				addrOk = false;
    				addrObject = addrInputs[i];
    				hint = addrInputs[i].placeholder;
    				break;
    			}
    		} // .addr 빈값 확인
    		
    		if(!addrOk){
    			alert(hint+"를 확인해 주세요.");
    			addrObject.focus();
    			return;
    		}
    		
    		//return; // 임시로 submit 방지
    		let u_info = document.getElementById("u_info");
    		if(!u_info.checked){
    			u_info.focus();
    			alert("개인 정보 이용에 동의해 주세요.");
    			return;
    		}
    		
    		
    		document.querySelector("#joinForm").submit();
    		
    	}; // 회원 등록 버튼 click event
    	
    	
    </script>
</form>


</body>
</html>