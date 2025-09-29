package net.koreate.comment.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import net.koreate.comment.vo.CommentVO;
import net.koreate.common.util.Criteria;

public interface CommentDAO {

	/**
	 * @param bno - 검색할 댓글의 게시글 번호
	 * @return	  - 해당 게시글에 등록된 모든 댓글 목록
	 * @throws Exception - 발생된 예외는 호출한 곳에서 처리할 수 있도록 throws
	 */
	@Select("SELECT * FROM test_comment WHERE bno = #{bno} ORDER BY cno DESC")
	List<CommentVO> commentList(int bno) throws Exception;
	
	/**
	 * 댓글 삽입
	 * @param vo - 삽입될 댓글 정보(게시글 번호, 내용, 작성자)
	 * @return 	 - 삽입된 행의 개수
	 */
	@Insert("INSERT INTO test_comment(bno, content, author) VALUES(#{bno}, #{content}, #{author})")
	int insert(CommentVO vo) throws Exception;
	
	/**
	 * 댓글 수정
	 * @param vo - 수정할 댓글 정보(내용, 작성자, 수정할 댓글 번호)
	 * @return 	 - 수정(UPDATE)된 행의 개수
	 */
	@Update("UPDATE test_comment SET "
			+ "content = #{content}, author = #{author}, updatedate = CURRENT_TIMESTAMP "
			+ "WHERE cno = #{cno}")
	int update(CommentVO vo) throws Exception;
	
	/**
	 * 댓글 삭제
	 * @param cno - 삭제할 댓글 번호
	 * @return	  - 삭제된 행의 개수
	 */
	@Delete("DELETE FROM test_comment WHERE cno = #{cno}")
	int delete(int cno) throws Exception;
	
	/**
	 * 해당 게시글에 등록된 전체 댓글 개수
	 * @param bno - 검색할 게시글 번호
	 * @return 	  - 등록된 전체 게시글 개수
	 */
	@Select("SELECT count(*) FROM test_comment WHERE bno = #{bno}")
	int totalCount(int bno) throws Exception;
	
	/**
	 * 페이징 처리된 범위 내의 댓글 목록
	 * @param bno - 검색할 게시글 번호
	 * @param cri - 검색 범위
	 * @return	  - 지정된 게시글의 페이징 처리된 범위 내의 댓글 목록
	 */
	@Select("SELECT * FROM test_comment WHERE bno = #{bno} ORDER BY cno DESC "
			+ "OFFSET #{cri.startRow} ROWS FETCH NEXT #{cri.perPageNum} ROWS ONLY")
	// List<CommentVO> listPage(Map<String, Object> map) throws Exception;
	List<CommentVO> listPage(@Param("bno") int bno, @Param("cri") Criteria cri) throws Exception;
	// List<CommentVO> listPage(int bno, Criteria cri) throws Exception;
}
