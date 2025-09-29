<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Comment</title>
	
	<style>
		#comments li{
			list-style: none;
			padding: 10px;
			border: 1px solid #ccc;
			height:120px;
			margin: 5px 0;
		}
		
		#modDiv{
			border: 1px solid #ccc;
			padding: 10px;
			display:none;
		}
	</style>
</head>
<body>
	<!-- 댓글 수정 화면 start -->
	<div id="modDiv" style="display:none;">
		<h2>댓글 수정</h2>
		<div id="modCno"><!-- 수정할 게시글 번호 --></div>
		<div>
			댓글 작성자 - <input type="text" id="modAuth">
		</div>
		<div>
			댓글 내용 - <input type="text" id="modContent">
		</div>
		<div>
			<input type="button" id="modBtn" value="MODIFY">
			<input type="button" id="delBtn" value="DELETE">
		</div>
	</div>
	<!-- 댓글 수정 화면 end -->

	<h2>AJAX - REST COMMENT</h2>
	<h3>1번 게시글 댓글 정보</h3>
	<hr>
	<h3>댓글 등록</h3>
	<div>
		<div>
			comment author : <input type="text" id="cAuth">
		</div>
		<div>
			comment content : <input type="text" id="cText">
		</div>
		<button id="addBtn">ADD COMMENT</button>
	</div>
	<hr>
	<div>
		<!-- 댓글 목록 출력 -->
		<ul id="comments"></ul>
	</div>
	
	
	<script>
		var bno = 1;		// 이 요청 페이지에서 처리할 게시글 번호 지정
		
		var page = 1;		// 목록을 불러올 페이지 번호
		var perPageNum = 5;	// 한 번에 읽어올 행 개수
		
		// 페이지 최초 출력 시 1page 댓글 목록 요청
		listPage(page);
		
		// 매개 변수로 받은 페이지 번호로 
		// 페이징 처리된 댓글 목록 요청
		function listPage(page){
			
			let modDiv = document.querySelector("#modDiv");
			modDiv.style.display = "none";
			// 문서 본문의 자식들 중 앞으로 수정 태그 이동
			document.body.prepend(modDiv);
			
			// comment/bno/page/perPageNum
			// comment/1/2/5
			let url = "comment/"+bno+"/"+page+"/"+perPageNum;
			
			fetch(url).then(res => res.json())
			.then(data => {
				console.log(data);
				// data에 저장된 댓글 목록 출력
				printPage(data);
			})
			.catch(err => {
				console.log(err.message);
			});
		}
		
		// 페이지 출력에 필요한 데이터를 매개 변수로 받아서
		// comments : 댓글 목록 리스트 화면에 출력
		function printPage(data){
			let list = data.list;
			let pm = data.pm;
			
			console.log("list", list);
			console.log("PageMaker", pm);
			
			let comments = document.querySelector("#comments");
			console.log(comments);
			
			let html = '';
			
			for(let i = 0; i < list.length; i++){
				let cno = list[i].cno;
				let auth = list[i].author;
				let content = list[i].content;
				let regdate = list[i].regdate;
				let reg = new Date(regdate); 
				let regStr = reg.toLocaleString();
				
				html += `
					<li>
						\${cno} - \${auth} : \${regStr} 
						- <button data-cno='\${cno}' data-author='\${auth}' data-content='\${content}'>
							MODIFY
						  </button>
						<br><hr>
						\${content}
					</li>
				`;
			} // end list for each
			
			if(page === 1){
				comments.innerHTML = html;
			}else{
				// Adjacent : 인접한, 가까이 있는
				/*
					insertAdjacentElement() : 노드(요소)를 삽입(복제 생성 X, 기존 위치에서 이동)
					insertAdjacentHTML() 	: HTML이 포함된 문자열을 삽입(innerHTML과 동일)
					insertAdjacentText()	: 텍스트만 삽입(textContent와 동일)
					
					대상 요소.insertAdjacent("position", element or 문자열)
					대상 요소를 기준으로 position 위치에 element 또는 문자열을 삽입
					
					position
					
					- beforebegin
					  : target 요소 바로 앞에 삽입(before()와 동일하게 삽입)
					
					- afterbegin
					  : target 요소의 첫 번째 자식으로 삽입(prepend()와 동일하게 삽입)
					  
					- beforeend
					  : target 요소의 마지막 자식으로 삽입(append()와 동일하게 삽입)
					
					- afterend
					  : target 요소의 바로 뒤에 삽입(after()와 동일하게 삽입)
					  
				*/
				comments.insertAdjacentHTML("beforeend", html);
			} // end 1page check
			
			// 더보기 버튼 활성화 여부
			if(page < pm.maxPage){
				let btnStr = `
					<input type='button' value='더보기'
					onclick="nextPage(this)" style='width:100%;text-align:center;padding:10px;'>
				`;
				comments.insertAdjacentHTML("beforeend", btnStr);
			}
			
		} // end printPage
		
		// 더보기 버튼 클릭 시 다음 페이지 목록 요청 처리
		function nextPage(btn){
			console.log("nextPage : ", btn);
			// click된 button 요소는 화면에서 제거
			btn.remove();
			// 페이지 수 1 증가
			page++;
			// 1 증가된 페이지로 다음 페이지 목록 요청
			listPage(page);
		}
		
		// 전체 댓글 요청 처리
		// getCommentList();
		
		// 댓글 목록 요청 및 화면 출력
		function getCommentList(){
			
			let modDiv = document.querySelector("#modDiv");
			modDiv.style.display = "none";
			// 문서 본문의 자식들 중 앞으로 수정 태그 이동
			document.body.prepend(modDiv);
			
			let url = "comment/list/" + bno; // comment/list?bno=1
			console.log("url : ", url);
			
			// fetch GET 요청
			fetch(url).then(res => res.json())
			.then(data => {
				// json으로 변환된 data
				console.log(data);
				printList(data);
			});
			
		}; // 전체 댓글 목록 요청 실행 함수
		
		// server에서 전달 받은 댓글 목록을 #comments ul 태그에 출력
		function printList(list){
			let comments = document.querySelector("#comments");
			console.log(comments);
			
			let html = '';
			
			for(let i = 0; i < list.length; i++){
				console.log("comment : ", list[i]);
				console.log("================================================");
				let cno = list[i].cno;
				let auth = list[i].author;
				let content = list[i].content;
				let regdate = list[i].regdate;
				// millisecond 정보로 시간 정보를 저장하는 Date 객체 생성
				let reg = new Date(regdate); 
				let regStr = reg.toLocaleString();
				console.log("cno : ", cno);
				console.log("author : ", auth);
				console.log("content : ", content);
				console.log("regdate : ", regdate);
				console.log("date text : ", regStr);
				console.log("================================================");
				
				html += `
					<li>
						\${cno} - \${auth} : \${regStr} 
						- <button data-cno='\${cno}' data-author='\${auth}' data-content='\${content}'>
							MODIFY
						  </button>
						<br><hr>
						\${content}
					</li>
				`;
			} // end list for each
			
			comments.innerHTML = html;
			
		} // end printList
		
		// 댓글 추가
		document.getElementById("addBtn").onclick = function(){
			let auth = document.getElementById("cAuth").value;
			let text = document.querySelector("#cText").value;
			console.log("auth : ", auth);	// 입력된 작성자 이름
			console.log("text : ", text);	// 입력된 댓글 내용
			console.log("bno : ", bno);		// 전역 변수 게시글 번호
			
			// GET  POST  PUT  PATCH  DELETE
			fetch("comment", {
				method : "POST",
				headers : { 'Content-Type' : 'application/x-www-form-urlencoded' },
				body : "bno=" + bno + "&author=" + auth + "&content=" + text
			}).then(res =>{
				if(!res.ok){
					// 응답 결과가 200번대 : 정상 처리가 아닐 경우
					
					// 응답 메세지 정보로 예외 발생
					throw new Error('응답 상태 코드 : ' + res.status);
				}
				return res.text(); // 댓글 등록 결과를 문자열 데이터로 전달
			})
			.then(data =>{
				// 정상 처리된 결과 data
				// console.log(data);
				alert(data);
				document.querySelector("#cAuth").value = "";
				document.querySelector("#cText").value = "";
				getCommentList(); // 댓글 목록 새롭게 작성
			})
			.catch(error =>{
				// 요청 처리 오류 메세지 error
				alert(error.message);
			});
			
		}; // end 댓글 삽입 addBtn event
		
		// 댓글 목록에서 modify button이 클릭되면 수정 창 출력
		document.querySelector("#comments").onclick = function(e){
			// e == click event 정보를 저장하는 Event 객체
			console.log(e.target);
			console.log(e.target.tagName);
			
			if(e.target.tagName == "BUTTON"){
				
				let cno = e.target.dataset.cno;			// 댓글 번호(문자열)
				let auth = e.target.dataset.author;		// 댓글 작성자
				let content = e.target.dataset.content  // 댓글 내용
				
				console.log(cno, auth, content);
				
				document.querySelector("#modCno").textContent = cno;
				document.querySelector("#modAuth").value = auth;
				document.querySelector("#modContent").value = content;
				
				let modDiv = document.querySelector("#modDiv");
				modDiv.style.display = (modDiv.style.display == "none") ? "block" : "none";

				e.target.parentElement.after(modDiv);
			} // click event target BUTTON
			
		}; // end #comments click event
		
		// 댓글 수정 Button click event
		document.querySelector("#modBtn").onclick = ()=>{
			// alert("click!");
			let cno = document.querySelector("#modCno").textContent;
			let auth = document.querySelector("#modAuth").value;
			let content = document.querySelector("#modContent").value;
			console.log("수정 댓글 번호 ", cno);
			console.log("수정된 작성자 ", auth);
			console.log("수정된 댓글 내용 ", content);
			
			// PATCH - 댓글 수정 요청
			// JSON 형식의 데이터 전송
			let paramObj = {author : auth, content : content, cno : cno};
			console.log(paramObj);
			let param = JSON.stringify(paramObj);
			console.log(param);
			
			fetch("comment/update", {
				method : "PATCH",
				headers : {"Content-Type" : "application/json"},
				body : param
			})
			.then(res => res.text())
			.then(result => {
				alert(result);
				// 댓글 목록 갱신
				getCommentList();
			})
			.catch(err => {
				console.log(err.message);
			});
			
		}; // end 댓글 수정 이벤트 처리
		
		// 댓글 삭제 이벤트 처리
		document.querySelector("#delBtn").onclick = () => {
			let cno = document.querySelector("#modCno").textContent;
			
			// DELETE - comment/cno
			fetch("comment/"+cno, {method : "DELETE"})
			.then(res => res.text())
			.then(result => {
				alert(result);
				getCommentList();
			})
			.catch(err => {
				console.log(err.message);
			});
		};
		
	</script>
</body>
</html>
