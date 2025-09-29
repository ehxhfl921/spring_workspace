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
			.then(res => res.json())
			.then(data => {
				getItem(data)
			});
		} // end getMessageList()
		
		// 1개의 message 정보를 받아 화면에 출력 li(lis item)을 문자열로 반환
		function getItem(message){
			let messageList = document.querySelector("#messageList");
			console.log(message);
			console.log(message[0].senddate);
			
			let html = '';
			
			for(let i = 0; i < message.length; i++){
				let mno = message[i].mno;
				let targetid = message[i].targetid;
				let sender = message[i].sender;
				let text = message[i].message;
				let senddate = getDate(message[i].senddate);
				let opendate = message[i].opendate;
				let isOpened = (opendate == null) ? "미확인" : getDate(opendate);
				
				html += `
					<li data-mno=\${mno} data-userid=\${targetid}>
						\${mno} | 수신자 : \${targetid} | 발신자 : \${sender} <br>
						\${text} <br>
						수신 확인 : \${isOpened} | 발신 시간 : \${senddate} 
					</li>
						
				`;
			}
			
			messageList.innerHTML = html;
			
		}
		
		// 메시지 추가 이벤트 처리
		document.querySelector("#add").onclick = function(){
			let targetid = document.querySelector("#targetid").value;
			let sender = document.querySelector("#sender").value;
			let message = document.querySelector("#message").value;
			
			fetch("messages/add", {
				method : "POST",
				headers : { 'Content-Type' : 'application/x-www-form-urlencoded' },
				body : "targetid="+targetid+"&sender="+sender+"&message="+message
			})
			.then(res => res.text())
			.then(data => {
				alert(data);
				document.querySelector("#targetid").value = "";
				document.querySelector("#sender").value = "";
				document.querySelector("#message").value = "";
				getMessageList();
			})
			.catch(e => {
				alert(e.message);
			});
			
		}; // end 메세지 추가
		
		// 목록에서 click 된 메시지 읽기 처리
		let list = document.querySelector("#messageList");
		
		list.onclick = function readMessage(e){
			
			let mno = e.target.dataset.mno;
			let userid = e.target.dataset.userid;
			
			fetch("messages/read/"+mno+"/"+userid, {method : "PATCH"})
			.then(res => res.text())
			.then(result => {
				getMessageList();
			})
			.catch(err => {
				console.log(err.message);
			})
		}
		
		 
		
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













