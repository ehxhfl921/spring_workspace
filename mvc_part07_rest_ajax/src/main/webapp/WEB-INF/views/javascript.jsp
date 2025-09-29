<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JavaScript AJAX</title>
</head>
<body>
	<h1>JavaScript AJAX</h1>
	<input type="button" id="continue_btn" value="AJAX">
	<div id="box1"></div>
	<hr>
	<div id="box2"></div>
	
	<script>
		/*
			AJAX(Asynchronous Javascript And Xml)
			비동기 자바스크립트와 XML
			비동기 방식으로 브라우저의 요청 변경 없이 Javascript와 서버 간의 통신을 하기 위해
			XMLHttpRequest 객체를 사용하는 것
			
			JavaScript로 요청을 보내고 JavaScript로 결과를 받아 부분 화면 정보 또는
			데이터를 활용할 수 있도록 통신 방법을 지원하는 객체
		*/
		
		// XmlHttpRequest 객체를 저장하기 위한 변수 선언
		var httpRequest;
		document.getElementById("continue_btn").onclick = function(){
			// id가 continue_btn인 태그 요소에 click event가 발생하면 실행될 callback 함수
			httpRequest = new  XMLHttpRequest();
			if(!httpRequest){
				alert("AJAX 통신 불가");
				return false; // event 종료
			} // check httpRequest
			
			// httpRequest 객체가 요청 처리에 따른 현재 진행 상태를 저장하는 속성
			/*
				0		UNSET - request가 초기화되지 않음
				1		OPEND - 서버와 연결 준비 완료 (open() 호출됨)
				2		HEADERS RECEIVED - 요청이 서버에 도착, 헤더 정보 수신 완료
				3		LOADING - 응답 본문 수신 중
				4		DONE	- 모든 데이터 수신 완료(처리 준비 완료)
			*/
			console.log("test 1", httpRequest.readyState);
			
			// 상태가 변경될 때 호출될 함수로 setContents 함수 지정
			httpRequest.onreadystatechange = setContents;
			
			// 서버와 연결
			httpRequest.open("GET", "sample2?name=최기근&price=10000");
			
			console.log("test 2", httpRequest.readyState);
			
			// open된 정보로 서버에 요청 전달
			httpRequest.send();
			
			console.log("send 호출 완료");
			
		}; // end onclick
		
		// 진행 상태가 변경될 때마다 호출되어 처리할 함수
		function setContents(){
			console.log("test contents : ", httpRequest.readyState);
			// 응답 완료 상태 코드
			console.log("test status : ", httpRequest.status);
			// 응답이 완료 되었을 때
			if(httpRequest.readyState === 4){
				// 응답 상태 코드 확인
				if(httpRequest.status === 200){
					
					let str = httpRequest.responseText;
					console.log("응답 결과 : ", str);
					let obj = {name : 'choi', price : 38};
					console.log("js obj : ", obj);
					
					// box1 태그 요소 내부에 HTML 형식의 컨텐츠 추가
					document.getElementById("box1").innerHTML = str;
					
					// JSON 형식으로 작성된 문자열을 JavaScript Object(객체)로 변환
					let sample = JSON.parse(str);
					console.log("response sample ", sample);
					
					let html = "<table border='1'>";
					html += "<tr><th>이름</th><th>가격</th></tr>";
					html += "<tr><td>"+sample.name+"</td><td>"+sample.price+"</td></tr>"
					html += "</table>";
					console.log("html : ", html);
					
					document.getElementById("box2").innerHTML += html;
					
				}else{
					alert("요청 처리를 완료하지 못했습니다.");
				}
			}
		}
	</script>
</body>
</html>














