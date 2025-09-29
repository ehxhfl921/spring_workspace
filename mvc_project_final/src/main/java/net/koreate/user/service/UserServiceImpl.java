package net.koreate.user.service;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.koreate.common.utils.FileUtils;
import net.koreate.user.dao.UserDAO;
import net.koreate.user.vo.UserVO;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

	private final UserDAO dao;
	
	private final String uploadPath;
	
	private final ServletContext servletContext;
	
	// 완성된 실제 저장 폴더 경로
	private String realPath;
	
	@PostConstruct
	public void initPath() {
		realPath = servletContext.getRealPath(
			File.separator+uploadPath
		);
		File file = new File(realPath);
		if(!file.exists()) {
			file.mkdirs();
			log.info("경로 생성 완료");
		}
		log.info("user service 초기화 완료");
	}
	
	@Override
	public String userJoin(UserVO vo, MultipartFile profileImage) throws Exception {
		// 선택된 프로필 이미지가 없을 경우 빈 문자열 데이터 등록
		vo.setU_profile("");
		
		if(profileImage != null && !profileImage.isEmpty()) {
			String u_profile
				= FileUtils.uploadFile(
						realPath, 
						profileImage
				  );
			log.info(u_profile);
			vo.setU_profile(u_profile);
		}
		log.info("service : {}" , vo);
		int result = dao.memberJoin(vo);
		
		return result == 1 ? "SUCCESS" : "FAIELD";
	}

	@Override
	public UserVO login(String u_id, String u_pw, HttpSession session) throws Exception {
		// 회원 로그인
		// id와 비밀번호가 일치하는 사용자 정보 검색
		UserVO user = dao.login(u_id, u_pw);
		session.setAttribute("userInfo", user);
		return user;
	}
	
	@Override
	public UserVO getUserById(String u_id) throws Exception {
		// 등록된 한명의 회원 정보
		return dao.getUserById(u_id);
	}

	@Override
	public void updateVisteDate(String u_id) throws Exception {
		// TODO 로그인 성공 시 방문 시간 수정
	}

	@Override
	public void deleteYN(UserVO vo) throws Exception {
		// TODO 회원 탈퇴 요청 시 회원 정보 수정
	}

}









