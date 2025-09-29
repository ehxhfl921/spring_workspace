package net.koreate.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import net.koreate.user.vo.UserVO;

public interface UserDAO {

	/**
	 * 회원가입
	 */
	@Insert("INSERT INTO validation_user("
	+"u_id,u_pw,u_profile,u_phone,u_name,u_birth,u_addr_post,u_addr,u_addr_detail) "
	+"VALUES(#{u_id},#{u_pw},#{u_profile},#{u_phone},#{u_name},#{u_birth},#{u_addr_post},#{u_addr},#{u_addr_detail})")
	int memberJoin(UserVO vo) throws Exception;
	
	@Select("SELECT * FROM validation_user WHERE u_id = #{u_id} AND u_pw = #{u_pw}")
	UserVO login(@Param("u_id") String u_id, @Param("u_pw")String u_pw) throws Exception;

	/**
	 * id로 회원 정보 검색
	 */
	@Select("SELECT * FROM validation_user WHERE u_id = #{u_id}")
	UserVO getUserById(String u_id) throws Exception;
	
	/**
	 * 로그인 시 최종 방문 시간을 현재시간으로 수정
	 */
	void updateVistDate(String u_id)throws Exception;

	/**
	 * 등록된 사용자 리스트
	 */
	List<UserVO> getMemberList()throws Exception;

	/**
	 *	활성화 여부 수정
	 */
	void deleteYN(UserVO vo) throws Exception;

}









