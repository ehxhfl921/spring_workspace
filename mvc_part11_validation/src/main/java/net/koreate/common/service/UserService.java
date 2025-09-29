package net.koreate.common.service;

import java.io.File;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.koreate.common.dao.UserDAO;
import net.koreate.common.util.FileUtil;
import net.koreate.valid.vo.ValidationUserVO;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

	private final UserDAO dao;

	private String profilePath;
	
	@PostConstruct
	public void initPath() {
		profilePath = "C:\\Temp\\profile";
		File file = new File(profilePath);
		
		if(!file.exists()) {
			file.mkdirs();
		}
		
		log.info("UserService Post Construct");
	}
	
	public String memberJoin(ValidationUserVO vo, MultipartFile profileImage) throws Exception{
		if(profileImage != null && !profileImage.isEmpty()) {
			String u_profile = FileUtil.uploadFile(profilePath, profileImage);
			vo.setU_profile(u_profile);
		}
		
		log.info("service : {} ", vo);
		int result = dao.memberJoin(vo);
		
		return result == 1 ? "SUCCESS" : "FAILED";
	}
	
}
