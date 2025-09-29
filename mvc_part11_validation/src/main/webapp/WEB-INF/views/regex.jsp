<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="common/header.jsp" %>


<h1> 정규 표현식 확인 </h1>

<script>
	/*
		정규 표현식(정규식)은 문자열에 포함된 문자 조합을 찾기 위해 사용되는 패턴
		javascript의 정규 표현식은 java 정규 표현식과 표현 방법의 차이는 있으나
		기본 패턴 문자는 동일함
		
		java : 문자열로 정규 표현식 표현
		javascript : '/' 특수 문자로 정규 표현식의 시작과 끝을 표현
		
		("문자열").match(/정규 표현식/flag); "문자열"에서 /정규 표현식/에 매칭되는 문자열을 배열로 반환
		(/정규 표현식/).test("문지열") "문자열"이 /정규 표현식/에 매치되면 true, 아니면 false 반환
	*/
	
	const regex = /\d{3}-\d{4}-\d{4}/;  // java : "\\d{3}-\\d{4}-\\d{4}"
	let bool = regex.test("010-1111-1111");
	console.log(bool);			// true
	
	const text = "안녕하세요 제 번호는 010-1111-1111입니다.";
	// test() 문자열 전체를 검사해서 패턴에 만족하는 문자열이 존재하면 true
	bool = regex.test(text); 	// true
	console.log(bool);
	
	let result = text.match(regex);
	console.log(result);
	
	const pattern = /\d{5}-\d{4}-\d{4}/;
	result = text.match(pattern);
	console.log(result); 		// null
	
	var regexEmail =/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;       // 이메일
	const email = "hap0p9y@nate.com";
	console.log(email.match(regexEmail));
	console.log(regexEmail.test(email));
	const wrongEmail = "hap0p9ynatecom";
	console.log(wrongEmail.match(regexEmail));
	console.log(regexEmail.test(wrongEmail));
	
</script>

</body>
</html>