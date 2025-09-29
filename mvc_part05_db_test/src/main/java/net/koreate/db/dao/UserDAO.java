package net.koreate.db.dao;

import java.util.List;

import net.koreate.db.vo.UserVO;

public interface UserDAO {

	/**
	 * @param user - 등록할 사용자 정보를 저장하고 있는 UserVO 객체
	 * @return int - 테이블에 회원 정보 삽입 후 삽입된 행 개수 반환
	 */
	int insertUser(UserVO user);

	/**
	 * @param userid - 검색할 사용자 아이디 문자열
	 * @return UserVO : 검색된 한 명의 회원 정보를 UserVO 타입으로 저장하여 반환
	 */
	UserVO readUser(String userid);
	
	/**
	 * @param userid - 검색할 사용자 아이디
	 * @param userpw - 검색할 사용자 비밀번호
	 * @return - 아이디와 비밀번호가 일치하는 한 명의 사용자 정보 반환 : null은 정보가 없음
	 */
	UserVO readUserWithPass(String userid, String userpw);
	
	/**
	 * @return test_user table에 등록된 모든 사용자 목록을 List type으로 저장하여 반환
	 */
	List<UserVO> readUserList();
	
	/**
	 *  현재 등록된 회원들 중 가장 높은(큰) 회원 번호 반환
	 */
	int readMax();
	
}

























