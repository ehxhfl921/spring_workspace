<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>uploadAJAX</title>
<style>

	.fileDrop{
		width: 100%;
		height: 300px;
		background-color: #ccc;
		border: 1px solid black;
		cursor:pointer;
	}

</style>
</head>
<body>
	<h2>File Drag & Drop (AJAX fetch API)</h2>
	<!-- file drag & drop 시 파일 업로드할 태그 -->
	<div class="fileDrop">
		<h2>업로드할 파일을 드래그 또는 드랍하거나 여기를 클릭해서 파일을 선택하세요.</h2>
	</div>
	<input type="file" id="files" style="display:none;">
	<!-- upload된 파일 목록 출력 -->
	<ul id="uploadList"></ul>
	
	
	<script>
		let fileDrop = document.querySelector(".fileDrop");
		
		// browser가 인식하는 드래그 이벤트
		// dragenter 드래그 상태로 브라우저에 접근
		// dragover 드래그 상태로 브라우저에서 머무는 동안
		// drop 드래그를 브라우저에서 놓을 시
		
		fileDrop.addEventListener("dragenter", function(e){
			e.preventDefault();
		});
		
		fileDrop.addEventListener("dragover", function(e){
			e.preventDefault();
		});
		
		fileDrop.addEventListener("drop", function(e){
			e.preventDefault();
			
			// drop event에서 발생한 파일 목록
			let files = e.dataTransfer.files;
			console.log(files);
			
			// drop 된 파일 목록으로 서버에 업로드 요청
			fileUpload(files);
			
		});
	
		/*
			매개 변수로 전달된 파일 목록을 서버에 전송 후 결과 출력
		*/
		function fileUpload(files){
			// server 에 전송할 데이터를 저장하는 객체
			let formData = new FormData();
			
			let size = 0;
			
			for(let i = 0; i < files.length; i++){
				const file = files[i];
				console.log(file);
				if(!file.type.startsWith("image/")){
					// 이미지 파일이 아닌 file
					console.log(file.name);
					alert(file.name + "파일은 이미지가 아니므로 제외됩니다.");
					// break;
					continue;
				}
				formData.append("files", file);
				size++;
			} // end append for
			
			if(size > 0){
				
				fetch("uploadFiles", {
					method : "POST",
					body : formData
				})
				.then(res => res.json())
				.then(result =>{
					
					console.log(result);
					
					let html = "";
					
					for(let i = 0; i < result.length; i++){
						// 파일 이름 기준 _ 다음 인덱스 번호
						const index = result[i].indexOf("_") + 1;
						const origin = result[i].substring(index);
						console.log("upload file : ", result[i]);
						console.log("origin file : ", origin);
						
						html += `
							<li>
								<a href="requestFile?fileName=\${result[i]}">\${origin}</a>
								<span onclick="deleteFile('\${result[i]}', this);">[X]</span>
							</li>
						`;
					} // end result for
					
					// document.querySelector("#uploadList").innerHTML = html;
					document.querySelector("#uploadList").insertAdjacentHTML("beforeend", html);
					
				}).catch(err => {
					console.log(err);
					alert("파일 업로드 중 오류가 발생했습니다.");
				});
			} // end size check
		} // end function fileUpload()
		
		// input#files
		let fileInput = document.querySelector("#files");
		
		// fileDrop 박스 태그 요소
		fileDrop.onclick = function(){
			// alert("click");
			// click(); - click event 강제 발생
			fileInput.click();
		};
		
		fileInput.onchange = function(){
			console.log(this.files);
			fileUpload(this.files);
		};
		
		// 서버에 업로드된 파일 삭제 요청 처리
		function deleteFile(fileName, element){
			console.log("delete file : " + fileName);
			console.log("delete span : " + element);
			
			// 서버에 삭제 요청
			fetch("deleteFile", {
				method : "DELETE",
				headers : {"Content-Type" : "application/json"},
				body : fileName
			})
			.then(res => res.text())
			.then(result => {
				alert(result);
				if(result == "DELETED"){
					element.parentElement.remove();	// li 태그 제거
				}
			})
			.catch(error => {
				console.log(error);
			});
			
			
		} // end function deleteFile
	</script>
</body>
</html>