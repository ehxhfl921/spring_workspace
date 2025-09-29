package net.koreate.common.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.koreate.common.util.FileUtil;

@RestController
public class AJAXFileController {
	
	@Autowired
	private String uploadFileSystem; 
	
	
	
	// uploadFiles
	@PostMapping("uploadFiles")
	public List<String> uploadFiles(
				List<MultipartFile> files
			) throws Exception{
		List<String> names = new ArrayList<>();
		
		for(MultipartFile f : files) {
			
			String uploadFile = FileUtil.uploadFile(uploadFileSystem, f);
			names.add(uploadFile);
			
		}
		return names;
	}
	
	@GetMapping("requestFile")
	public ResponseEntity<byte[]> requestFile(String fileName) throws Exception{
		byte[] fileData = FileUtil.getBytes(uploadFileSystem, fileName);
		HttpHeaders headers = FileUtil.getHeaders(fileName);
		return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
	}
	
	@DeleteMapping("deleteFile")
	public ResponseEntity<String> deleteFile(
				@RequestBody String fileName
			) throws Exception{
		ResponseEntity<String> entity = null;
		
		// 파일 저장 폴더와 파일 이름을 매개 변수로 전달 받아 파일 삭제 후 결과 반환
		boolean isDeleted = FileUtil.deleteFile(uploadFileSystem, fileName);
		
		if(isDeleted) {
			entity = new ResponseEntity<>("DELETED", HttpStatus.OK);			// 200
		}else {
			entity = new ResponseEntity<>("FAILED", HttpStatus.BAD_REQUEST);	// 400
		}
		
		return entity;
	}
}
