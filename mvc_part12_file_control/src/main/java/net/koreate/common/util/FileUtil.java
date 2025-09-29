package net.koreate.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

/**
 *	File System File 요청 처리 class
 *	날짜별 업로드 파일
 *	업로드된 파일 데이터 제공
 *	업로드된 파일 중 필요 없어진 파일 데이터 삭제
 *	업로드된 파일 정보를 제공하기 위한 response header 정보 제공 
 */
public class FileUtil {

	/**
	 * file upload 처리 후 업로드된 파일 이름 반환
	 * 
	 * @param MultipartFile - 업로드할 파일 정보
	 * @param String - 업로드할 File System 경로
	 * @return	실제 업로드된 파일 이름
	 */
	public static String uploadFile(
				String realPath,
				MultipartFile file
			) throws Exception{
		
		String uploadFileName = "";
		
		UUID uid = UUID.randomUUID();
		String randomName = uid.toString().replace("-", "");	// 32개 무작위 문자
		String origin = file.getOriginalFilename();	// 업로드 요청 원본 파일 이름
		uploadFileName = randomName + "_" + origin;
		System.out.println(uploadFileName);
		
		// 업로드 요청 날짜 기준 폴더 생성
		LocalDateTime date = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(
				File.separator+"yyyy"+File.separator+"MM"+File.separator+"dd"
		);
		String datePath = date.format(dtf);
		System.out.println("datePath : "+datePath); 	// \2025\09\09
		File dateDir = new File(realPath + datePath);	// C:\\Temp\\upload\\2025\\09\\09
		if(!dateDir.exists()) {
			dateDir.mkdirs();
		}
		
		// 업로드할 파일 정보
		File f = new File(dateDir, uploadFileName);
		// 매개 변수로 받은 file 위치에 MultipartFile 객체가 저장하고 있는 파일 데이터 전송
		file.transferTo(f);
		
		uploadFileName = datePath + File.separator + uploadFileName;
		
		// 디렉토리 구분자를 URL 경로 구분자로 변경
		uploadFileName = uploadFileName.replace(File.separatorChar, '/');
		
		return uploadFileName;
	} // end upload file
	
	/**
	 *  업로드 root 디렉토리 경로와 업로드된 파일 이름을 매개 변수로받아
	 *  해당 위치의 파일 정보 byte[] 이진 데이터 반환
	 */
	public static byte[] getBytes(String realPath, String fileName) throws Exception{
		File file = new File(realPath, fileName);
		InputStream is = new FileInputStream(file);
		
		byte[] bytes = new byte[is.available()];	// 파일 크기 만큼의 배열 생성
		
		for(int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte)is.read();
		}
		is.close();
		
		return bytes; 
	}
	
	/**
	 * request로 요청 파일 정보를 응답할 때
	 * response header에 추가
	 */
	public static HttpHeaders getHeaders(String fileName) throws Exception{
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		// ==  headers.add("Content-Type", "application/octet-stream");
		
		// 원본 파일 이름 추출
		String origin = fileName.substring(fileName.indexOf("_")+1);
		
		// Content-Disposition : 컨텐츠 배치 또는 조치
		// headers.add("Content-Disposition", attatchment;fileName="파일이름");
		ContentDisposition cd = ContentDisposition
								.attachment()
								// 파일 이름은 origin이며 인코딩이 UTF-8로 되어있음을 명시
								.filename(origin, Charset.forName("UTF-8"))
								.build();
		headers.setContentDisposition(cd);
		
		return headers;
	}

	/**
	 * 파일 저장 폴더와 파일 이름을 매개 변수로 전달 받아 파일 삭제 후 결과 반환
	 * @return true : 삭제 성공, false : 삭제 실패
	 */
	public static boolean deleteFile(String realPath, String fileName) {
		// fileName : /2025/09/10/aec054ffe74947348d7649700b8d8738_멘토링 페이지.png
		boolean isDeleted = false;
		
		// fileName : \\2025\\09\\10\\aec054ffe74947348d7649700b8d8738_멘토링 페이지.png
		fileName = fileName.replace('/', File.separatorChar);

		// 제거할 파일 정보 저장
		File file = new File(realPath, fileName);
		
		System.out.println(file.getAbsolutePath());
		
		// file 객체에 저장된 파일 제거 실행 후 성공 여부를 boolean으로 반환
		isDeleted = file.delete();
		
		return isDeleted;
	}
}
