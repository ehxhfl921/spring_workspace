package net.koreate.common.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import net.koreate.common.dto.FileDTO;
import net.koreate.common.util.FileUtil;

@Controller
public class FileController {
	
	@Autowired
	private String uploadFileSystem;
	@Autowired
	private String uploadPath;
	@Autowired
	private ServletContext application;

	/**
	 * 실제 파일 저장 경로를 저장할 필드
	 */
	private String saveDir;
	
	/**
	 * FileController 객체 생성 후
	 * 의존성 주입이 완료되어 객체 사용 준비가 완료되면
	 * 서버 구동 시 최초에 한 번 실행되는 메서드
	 */
	@PostConstruct
	public void initPath() {
		System.out.println("PostConstruct initPath() START");
		System.out.println("uploadFileSystem : " + uploadFileSystem);
		System.out.println("uploadPath : " + uploadPath);
		// 실제 저장 경로 - 디렉토리 체크 및 생성
		saveDir = uploadFileSystem;
		saveDir = application.getRealPath(uploadPath);
		System.out.println(saveDir);
		File file = new File(saveDir);
		if(!file.exists()) {
			file.mkdirs();
			System.out.println("업로드 디렉토리 생성");
		}
		System.out.println("PostConstruct initPath() END");
	}
	
	@PostMapping("uploadUtil")
	public String uploadUtil(MultipartFile file, Model model) throws Exception{
		
		// saveDir 위치에 file 업로드 후 실제 업로드된 파일 이름 반환
		String savedName = FileUtil.uploadFile(saveDir, file);
		
		model.addAttribute("savedName", savedName);
		
		return "uploadResult";
	}
	
	@PostMapping("upload")
	public String upload(MultipartFile file, Model model) throws IOException {
		
		// 파일 이름
		System.out.println("file name : " + file.getOriginalFilename());
		// 파일 크기
		System.out.println("file size : " + file.getSize());
		// 파일 유형(MIME type)
		System.out.println("file type : " + file.getContentType());
		// 파일 데이터 존재 여부
		System.out.println("file empty : " + file.isEmpty());
		// 전송된 실제 파일 데이터 - 예외처리 throws
		byte[] bytes = file.getBytes();
		System.out.println("file bytes length : " + bytes.length);
		
		if(!file.isEmpty()) {
			// 선택한 파일이 존재할 때
			String saveDir = "C:\\Temp\\upload";
			File f = new File(saveDir);
			if(!f.exists()) {
				// saveDir 위치에 디렉토리가 존재하지 않을 때
				f.mkdirs(); // 경로상에 존재하지 않는 모든 디렉토리 생성
			}
			/*
			// 파일 저장 (원본 파일 이름으로)
			File savedFile = new File(f, file.getOriginalFilename());
			// C:\\Temp\\upload\\원본파일이름.확장자
			FileOutputStream fos = new FileOutputStream(savedFile);
			fos.write(bytes); // bytes 배열에 저장된 데이터 출력
			fos.flush();
			fos.close();
			*/
			
			String uploadFileName = uploadFile(file);
			
			System.out.println("saved file name : " + uploadFileName);
			
			model.addAttribute("savedName", uploadFileName);
			
		} // end isEmpty()
		
		// /WEB-INF/views/uploadResult.jsp
		return "uploadResult";
	}
	
	/**
	 * 업로드된 파일 이름이 폴더내에서 중복되지 않도록 변경
	 * 실제 디렉토리에 업로드된 파일 이름 반환
	 */
	private String uploadFile(MultipartFile file) throws IOException{
		String savedName = "";
		
		// 32개의 랜덤한 문자 _ 4개의 (-) 특수문자 조합으로 36개의 무작위 문자 반환
		UUID uid = UUID.randomUUID();
		String origin = file.getOriginalFilename();
		String random = uid.toString().replace("-", "");	// - 특수 문자 제거, 32개 무작위 문자
		System.out.println(random);
		
		savedName = random + "_" + origin;
		
		// 파일 업로드
		File f = new File("C:\\Temp\\upload", savedName);
		
		// copy(업로드할 파일 데이터, 업로드 파일 위치 밎 이름);
		FileCopyUtils.copy(file.getBytes(), f);
		
		return savedName;
	}
	
	@PostMapping("uploadMultiple")
	public String uploadMultiple(MultipartFile[] files, Model model) throws IOException{
		
		// 업로드된 파일 이름 목록
		List<String> saves = new ArrayList<>();
		
		for(MultipartFile f : files) {
			String savedName = uploadFile(f); // 파일 데이터 저장 후 저장된 파일 이름 반환
			// 실제 저장된 파일 이름 목록을 List에 저장
			saves.add(savedName);
		}
		System.out.println(saves.size());
		model.addAttribute("saves", saves);
		return "uploadResult";
	}
	
	@PostMapping("uploadWithParam")
	public String uploadWithParam(
			String author,
			String content,
			MultipartFile profile,
			List<MultipartFile> files,
			Model model
			) throws Exception{
	
		List<String> saves = new ArrayList<>();
		// 프로필 이미지 업로드 후 업로드된 파일 이름 저장
		saves.add(uploadFile(profile));
		
		for(MultipartFile f : files) {
			saves.add(uploadFile(f));
		}
		
		model.addAttribute("saves", saves);
		model.addAttribute("author", author);
		model.addAttribute("content", content);
		
		return "uploadResult";
	}
	
	@PostMapping("uploadDTO")
	public String uploadDTO(FileDTO dto, Model model) throws Exception{
		System.out.println(dto);
		List<String> saves = new ArrayList<>();
		String profile = uploadFile(dto.getProfile());
		saves.add(profile);
		
		for(MultipartFile f : dto.getFiles()) {
			saves.add(uploadFile(f));
		}
		
		model.addAttribute("saves", saves);
		model.addAttribute("author", dto.getAuthor());
		model.addAttribute("content", dto.getContent());
		return "uploadResult";
	}
}
