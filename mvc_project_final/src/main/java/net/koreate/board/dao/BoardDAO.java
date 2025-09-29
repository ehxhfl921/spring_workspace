package net.koreate.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;

import net.koreate.board.provider.BoardQueryProvider;
import net.koreate.board.vo.BoardVO;
import net.koreate.common.utils.SearchCriteria;

public interface BoardDAO {
	
	/**
	 * 게시글 등록
	 */
	@Insert("INSERT INTO re_tbl_board(title, content, u_no) VALUES(#{title}, #{content}, #{u_no})")
	// key 생성 사용, 
	@Options(useGeneratedKeys=true, keyProperty="bno", keyColumn="bno")
	int regist(BoardVO vo)throws Exception;
	
	/**
	 * 게시글 등록후 질문글의 origin column 정보를 등록된 게시글 번호로 변경
	 */
	int updateOrigin(int bno) throws Exception;
	
	/**
	 * 검색결과내에서 페이징 처리된 게시글 목록
	 */
	@SelectProvider(type=BoardQueryProvider.class, method="searchSelectSQL")
	List<BoardVO> listReply(SearchCriteria cri)throws Exception;
	
	/**
	 * 검색결과내의 전체 게시글 개수
	 */
	// @Select("SELECT count(*) FROM re_tbl_board")
	@SelectProvider(type=BoardQueryProvider.class, method="searchListCount")
	int listCount(SearchCriteria cri)throws Exception;
	
	/**
	 * bno가 일치하는 게시글 정보
	 */
	@Select("SELECT R.*, U.u_name AS writer FROM "
			+ "re_tbl_board R JOIN validation_user U ON R.u_no = U.u_no "
			+ "WHERE R.bno = #{bno}")
	BoardVO read(int bno)throws Exception;
	
	/**
	 * 조회수 증가
	 */
	@Update("UPDATE re_tbl_board SET viewcnt = viewcnt + 1 WHERE bno = #{bno}")
	void updateCnt(int bno) throws Exception;
	
	/**
	 * 게시글 수정 - title, content, updatedate
	 */
	int modify(BoardVO vo) throws Exception;
	
	/**
	 * 게시글 삭제
	 * 행 정보 제거가 아닌 column 수정
	 * showboard = 'n' update
	 */
	
	int remove(int bno) throws Exception;

	/**
	 * 답변글 등록
	 */
	int replyRegister(BoardVO board) throws Exception;

}













