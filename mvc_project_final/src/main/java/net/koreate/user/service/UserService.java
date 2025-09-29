package net.koreate.user.service;

import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import net.koreate.user.vo.UserVO;

public interface UserService {
	
	// 회원가입
	String userJoin(UserVO vo, MultipartFile profileImage) throws Exception;
	
	// 로그인
	UserVO login(String u_id, String u_pw, HttpSession session)throws Exception;
	
	// 아이디 체크
	UserVO getUserById(String u_id)throws Exception;
	
	// 최종 방문 시간 현재시간으로 수정
	void updateVisteDate(String u_id) throws Exception;
	
	// 회원 탈퇴 여부  y,n		
	void deleteYN(UserVO vo)throws Exception;

	
	

}











