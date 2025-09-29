package net.koreate.msg.dao;

import org.apache.ibatis.annotations.Update;

import net.koreate.msg.vo.UserVO;

/**
 * tbl_user table 작업 수행 class
 */
public interface UserMapper {

	/**
	 * @param vo - upoint : 추가할 포인트 점수 & userid : 수정할 사용자 userid
	 * 전달된 포인트 점수 만큼 upoint 점수 증가
	 */
	@Update("UPDATE tbl_user SET upoint = upoint + #{upoint} WHERE userid = #{userid}")
	void updatePoint(UserVO vo) throws Exception;

}
