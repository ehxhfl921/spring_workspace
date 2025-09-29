<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>message.jsp</title>
<style>
#messageList {
	margin-left: 0;
}

#messageList li {
	list-style: none;
	border: 1px solid black;
	padding: 5px;
	height: 80px;
	margin-bottom: 5px;
}

#messageList li:hover {
	cursor: pointer;
}
</style>
</head>
<body>
	<div class="message-box">
		<h3>메세지를 보내 보세요!</h3>
		<input type="text" id="targetid" placeholder="수신자아이디" /> <br /> 
		<input type="text" id="sender" placeholder="발신자아이디" /> <br />
		<textarea id="message" placeholder="전달할 메세지"></textarea>
		<br />
		<button id="add">SEND MESSAGE</button>
	</div>
	<ul id="messageList"></ul>

	<script>
	
		getMessageList();

		// 메시지 목록 조회 및 출력
		function getMessageList(){
			fetch("messages/list")
			.then(res => {
				return res.json();
			}).then(result => {
				console.log(result);
				
				let html = "";
				
				for(let msg of result){
					html += `<li onclick='readMessage(\${msg.mno}, "\${msg.targetid}", this);'>`;	
					html += getItem(msg);	
					html += `</li>`;	
				}
				
				document.querySelector("#messageList").innerHTML = html;
				
			}).catch(error => {
				console.log(error.message);
			});
		} // end getMessageList()
		
		
		// 목록에서 click 된 메시지 읽기 처리
		function readMessage(mno, userid, element){
			// alert("readMessage : " + mno);
			console.log(mno, userid, element);
			
			fetch(`messages/read/\${mno}/\${userid}`, {
				method : "PATCH"
			}).then(res => {
				// MessageVO 객체 정보를 저장하는 JSON 문자열을 javascript 객체로 반환
				return res.json();
			}).then(result => {
				//opendate가 update된 메세지 정보
				console.log(result);
				alert("수신 확인");
				// click li 태그 요소의 내부 메세지 내용을 수정된 메세지 내용으로 갱신
				element.innerHTML = getItem(result);
				
			}).catch(err => {
				console.log(err.message);
			});
		}
		
		
		
		// 1개의 message 정보를 받아 화면에 출력 li(lis item)을 문자열로 반환
		function getItem(msg){
			// 메세지에 수신 확인 시간(opendate)이 null이 아니면 시간 정보를 text로 변경 null이면 미확인
			let opendate = msg.opendate ? getDate(msg.opendate) : "미확인";
			let senddate = getDate(msg.senddate);
			
			let content = `
				\${msg.mno} | 수신자 : \${msg.targetid} | 발신자 : \${msg.sender} <br>
				\${msg.message} <br> 수신 확인 : \${opendate} | 발신 시간 : \${senddate}
			`;
			return content;
		}
		
		// 메시지 추가 이벤트 처리
		document.querySelector("#add").onclick = function(){
			
			let targetid = document.querySelector("#targetid").value;
			let sender = document.querySelector("#sender").value;
			let message = document.querySelector("#message").value;
			
			// POST - /messages/add => 메세지 삽입 요청 처리
			// data 전송 유형 - application/json
			// {name:value, name:value, name:value ...}
			//			   - applicarion/x-www-form-urlencoded
			// name=value&name=value&...
			
			let data = {
					targetid : targetid,
					sender : sender,
					message : message
			};
			
			fetch("messages/add", {
				method : "POST",
				headers : {
					"Content-Type" : "application/json"
				},
				body : JSON.stringify(data)
			}).then(res => {
				return res.text();
			}).then(result => {
				alert(result);
				document.querySelector("#targetid").value = "";
				document.querySelector("#sender").value = "";
				document.querySelector("#message").value = "";
				document.querySelector("#targetid").focus();
				getMessageList();
			}).catch(err => {
				alert(err.message);
			});
			
			// javascript Object의 각 필드 값으로 QueryString과 동일 문자열 생성
			/*
			let params = new URLSearchParams(data);
			console.log(params.toString());
			
			fetch("messages/add", {
				method : "POST",
				headers : {
					"Content-Type" : "applicarion/x-www-form-urlencoded"
				},
				body : params.toString()
			})
			*/
			
		};
		
		
		
		// openddate 및 senddate 밀리세컨 시간 정보를 문자열 날짜로 변환하여 반환
		function getDate(date){
			let dateTime = new Date(date);
			let dateStr = dateTime.getFullYear()+"년 ";
			dateStr += (dateTime.getMonth()+1) +"월 ";
			dateStr += dateTime.getDate()+"일 ";
			dateStr += dateTime.getHours()+"시 ";
			dateStr += dateTime.getMinutes()+"분 ";
			dateStr += dateTime.getSeconds()+"초 ";
			return dateStr;
		}
	</script>
</body>
</html>













