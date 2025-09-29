<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<main class="flex-grow-1">
  <div class="container my-5">
    <div class="row d-flex justify-content-center">
      <div class="col-md-8">
        <div class="card shadow">
          <div class="card-header bg-dark text-white text-center">
            <h3 class="mb-0">REGISTER BOARD</h3>
          </div>
          <div class="card-body">
          	<!-- POST : board/register -->
            <form id="registerForm" action="register" method="POST">
              <!-- 로그인된 현재 글 작성자 회원 번호 -->
              <input type="hidden" name="u_no" value="${userInfo.u_no}"/>

              <!-- 제목 -->
              <div class="mb-3">
                <label for="title" class="form-label">제목</label>
                <input type="text" id="title" name="title" class="form-control" required/>
              </div>

              <!-- 작성자 -->
              <div class="mb-3">
                <label class="form-label">작성자</label>
                <input type="text" class="form-control" value="${userInfo.u_name}" readonly/>
              </div>

              <!-- 내용 -->
              <div class="mb-3">
                <label for="content" class="form-label">내용</label>
                <textarea id="content" name="content" class="form-control" rows="10"></textarea>
              </div>

              <!-- 파일 업로드 (드래그 앤 드롭 영역) -->
              <div class="mb-3">
                <label class="form-label">파일 업로드</label>
                <div class="fileDrop border border-2 rounded p-4 text-center text-muted bg-light">
                  FILE DROP HERE
                </div>
              </div>

              <!-- 업로드된 파일 리스트 -->
              <div class="mb-3">
                <ul class="uploadList list-group">
                	
                </ul>
              </div>

              <!-- 버튼 -->
              <div class="text-center">
                <button type="button" id="saveBtn" class="btn btn-primary px-4">등록</button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</main>

<script>
	// File Drag&Drop 이벤트 처리
	let fileDrop = document.querySelector(".fileDrop");
	fileDrop.addEventListener("dragenter", function(e){
		e.preventDefault();
	});

	fileDrop.addEventListener("dragover", function(e){
		e.preventDefault();
	});

	// 파일 드랍 이벤트 시 드랍된 파일 업로드
	fileDrop.addEventListener("drop", function(e){
		e.preventDefault();
		let files = e.dataTransfer.files;
		console.log(files);
		
		const maxSize = 10485760; // 10mb
		
		let fileSize = 0;
		
		let formData = new FormData();
	
		for(let i = 0; i < files.length; i++){
			
			fileSize += files[i].size; // 드래그 앤 드랍한 파일 크기를 누적해서 저장
			if(fileSize > maxSize){
				alert("파일의 크기가 너무 큽니다. 10MB 이하만 업로드 가능합니다.");
				return false; // 드래그 앤 드랍 이벤트 종료
			}
			
			formData.append("files", files[i]);
		} // end files[] for
		
		// server 파일 업로드
		
		fetch("${path}/uploadfile", {
			method : "POST",
			body : formData
		}).then(res => res.json())
		.then(result => {
			console.log(result);
			let uploadList = document.querySelector(".uploadList");
			
			for(let i = 0; i < result.length; i++){
				console.log(result[i]);
				// 출력 이미지, 파일 링크, 원본 파일 이름
				let imgSrc, getLink, fileName;
				if(result[i].indexOf("s_") >= 0){
					// /2025/09/23/s_99ea0a7920c24381a2420450e02e8144_덧니가 보고 싶어.jpg
					imgSrc = `${path}/displayFile?fileName=\${result[i]}`;
					// 썸네일 이미지가 아닌 업로드한 원본 이미지 이름(s_ 제거)
					let replace = result[i].replace("s_", "");
					getLink = `${path}/displayFile?fileName=\${replace}`;
					
				}else{
					// /2025/09/23/9b47525d867c43faad3066a5994c7885_도서데이터.txt
					imgSrc = "${path}/resources/img/file.png";
					getLink = `${path}/displayFile?fileName=\${result[i]}`;
				}
				
				// 뒤에서부터 검색한 뒤 _ 다음부터 끝까지
				fileName = result[i].substring(result[i].lastIndexOf("_")+1);
				
				// li 태그 생성
				const  li = document.createElement("li");
				li.className = "list-group-item d-flex align-items-center justify-content-between";
				
				uploadList.appendChild(li);
				
				li.innerHTML = `
					<div class="d-flex align-items-center gap-2">
	        			<img src="\${imgSrc}" class="img-thumbnail" style="width:50px;height:50px;object-fit:cover;">
						<a href="\${getLink}" class="text-decoration-none">\${fileName}</a>
	        		</div>
	       			<a href="\${result[i]}" class="btn btn-sm btn-outline-danger delBtn">삭제</a>
				`;
				
			} // end for upload-list
			
		})
		.catch(err => alert(err));
		
	}); // end drop file upload
	
	// 단일 파일 제거
	document.querySelector(".uploadList").addEventListener("click", function(e){
		console.log(e.target); // .uploadList 내부에서 실제 클릭된 이벤트 대상 태그 요소
		
		if(!e.target.classList.contains("delBtn")){
			// click 이벤트가 발생한 태그 요소의 class 목록 중에 delBtn이 존재하는지 확인
			return; // 이벤트 처리 종료
		}
		
		e.preventDefault(); // 기본 이벤트 제거
		
		// target 파일 제거 요청
		fetch("${path}/deleteFile",{
			method : "DELETE",
			headers : {"Content-Type" : "application/json"},
			body : e.target.getAttribute("href")
		})
		.then(res=>res.text())
		.then(result =>{
			alert(result);
			if(result == "DELETED"){
				// e.target 요소의 부모 요소 중 가장 가까운 li 태그 선택 제거
				e.target.closest("li").remove();
			}
		});
		
	}); // end delete file


	let registerForm = document.getElementById("registerForm");

	document.getElementById("saveBtn").onclick = function(){
		// uploadList 내부에 모든 삭제 버튼
		const uploadList = document.querySelectorAll(".uploadList .delBtn");
		
		for(let i = 0; i < uploadList.length; i++){
			// 업로드된 파일 개수 만큼 submit 이벤트 시 전달될 입력 태그 생성
			let input = document.createElement("input");
			input.type = "hidden";
			input.name = "files";
			input.value = uploadList[i].getAttribute("href");
			registerForm.appendChild(input); // 폼 태그 내부에 입력 태그 추가
		}
		
		// registerForm submit 이벤트 실행
		registerForm.submit();
	};
	
</script>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>












