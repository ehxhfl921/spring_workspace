<!-- fetch.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JavaScript fetch API</title>
</head>
<body>
	<div style="background-color:#ccc;height:600px;">dummy box</div>
	<div>
		<input type="text" id="name" autofocus placeholder="이름" > <br>
		<input type="number" id="price" placeholder="가격" > <br>
		<button id="get">fetch get</button>
		<button id="post">fetch Post</button>
		<button id="put">fetch put</button>
	</div>
	<hr>
	<div id="result" style="border:1px solid black;padding:10px;"></div>
	<div style="height:500px;"></div>
<script>
	
	// get 
	document.getElementById("get").onclick = function(){
		
		// click event 발생 시점에 각 입력필드에 작성된 value 값 호출 
		const input_name = document.querySelector("#name").value;
		const input_price = document.getElementById("price").value;
		console.log("name : " , input_name);
		console.log("price : " , input_price);
		
		let url = "sample2?name="+input_name+"&price="+input_price;
		
		// fetch API
		// 따로 option을 추가하지 않으면 문자열 경로로 GET 방식으로 요청
		fetch(
			url
		// AJAX 실행 완료 readystate === 4 일시 실행되는 함수
		).then(function(response){
			console.log(response);
			// 응답 결과 text 정보를 JSON(JavaScript Object) 객체로 반환
			// console.log(response.json());
			return response.json();
		}).then(function(data){
			// JSON javascript 객체 타입으로 변환된 데이터
			console.log(data);
			let html = "<div style='border:1px solid red;'>";
			html += "이름 : " + data.name +"<br>";
			html += "가격 : " + data.price +"<br>";
			html += "</div>";
			
			document.getElementById("result").innerHTML += html;
			
		}).catch(function(error){
			console.log("Error : " , error);
		});
		
	}; // end #get onclick
	
	// post fetch
	document.getElementById("post").onclick = function(){
		// click event 발생 시점에 각 입력 필드에 작성된 value 값 호출 
		const input_name = document.querySelector("#name").value;
		const input_price = document.getElementById("price").value;
		console.log("name : " , input_name);
		console.log("price : " , input_price);
		
		fetch("sampleList",{
			method : "POST",
			headers : {'Content-Type' : 'application/x-www-form-urlencoded'},
			body : "name="+input_name+"&price="+input_price
		})
		.then(response => {
			if(!response.ok){ // 응답 상태 코드가 200 ~ 299 OK 이외라면
				throw new Error("HTTP 오류 상태코드 : " + response.status);
			}
			return response.json();
		})
		.then(data => {
			// [SampleVO, SampleVO, SampleVO ...]
			console.log(data);
			
			let result = "";
			
			for(let i = 0; i < data.length; i++){
				console.log(data[i]);
				result += "<div style='border:1px solid gray;'>";
				result += "이름 : " + data[i].name + "<br>";
				result += "가격 : " + data[i].price + "<br>";
				result += "</div>";
			}
			
			document.getElementById("result").innerHTML = result;
			
		})
		.catch(error => {
			console.log("Error : " , error);
		});
		
	}; // end #post onclick
	
	// PUT
	document.getElementById("put").onclick = function(){
		const payload = {
				name : document.getElementById("name").value,
				price : document.getElementById("price").value
		}; // javascript object
		
		console.log(payload);
		
		fetch("sampleList", {
			method : "PUT",
			headers : {
				"Content-Type" : "application/json"
			},
			// JSON.parse("JSON 문자열") : JSON 형식으로 작성된 문자열을 JS Object로 변환
			// JSONstringify({javascript object})
			// 매개 변수로 전달 받은 Javascript 객체를 JSON 형식의 표기로 된 문자열로 변환
			body : JSON.stringify(payload)
		})
		.then(response => response.json())
		.then(data => {
			console.log(data);
		})
		.catch(error => {
			console.log(error);
		});
	}; // end #put onclick
</script>	
</body>
</html>












