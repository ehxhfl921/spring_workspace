package net.koreate.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import net.koreate.board.vo.BoardVO;
import net.koreate.common.util.Criteria;

public interface BoardDAO {

	/**
	 * @param board - 게시글 등록에 필요한 정보
	 * @return - 테이블에 삽입된 행의 개수
	 */
	@Insert("INSERT INTO test_board(title, content, writer) "
			+ "VALUES(#{title}, #{content}, #{writer})")
	int create(BoardVO board) throws Exception;
	
	/**
	 * @param bno - 조회할 게시글 번호
	 * @return - PK로 조회된 1개의 게시글 정보 반환
	 */
	@Select("SELECT * FROM test_board WHERE bno = #{bno}")
	BoardVO read(int bno) throws Exception;
	
	/**
	 * @param board - 수정할 게시글 정보
	 * @return - 수정된 행의 개수
	 */
	@Update("UPDATE test_board SET title = #{title}, content = #{content}, writer = #{writer} WHERE bno = #{bno}")
	int update(BoardVO board) throws Exception;
	
	/**
	 * @param bno - 삭제할 게시글 번호
	 * @return - 처리 후 삭제된 행의 개수 반환
	 */
	@Delete("DELETE FROM test_board WHERE bno = #{bno}")
	int delete(int bno) throws Exception;
	
	/**
	 * @param bno - 조회수 증가할 게시글 번호
	 * 일치하는 게시글 번호를 가진 행의 viewCnt(조회수) 컬럼의 데이터를 1 증가 
	 */
	@Update("UPDATE test_board SET viewcnt = viewcnt + 1 WHERE bno = #{bno}")
	void updateCnt(int bno) throws Exception;
	
	/**
	 * @return - 등록된 전체 게시글(행) 개수
	 */
	@Select("SELECT count(*) FROM test_board")
	int totalCount() throws Exception;
	
	/**
	 * @param cri - 검색 기준
	 * @return - 검색 기준에 따라 조회된 게시글 목록
	 */
	@Select("SELECT * FROM test_board ORDER BY bno DESC "
			+ "OFFSET #{startRow} ROWS FETCH NEXT #{perPageNum} ROWS ONLY")
	List<BoardVO> listCriteria(Criteria cri) throws Exception;
	
}
