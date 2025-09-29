package net.koreate.board.backup;

import java.util.List;

import net.koreate.board.vo.BoardVO;
import net.koreate.common.util.Criteria;

public interface BoardDAO {

	/**
	 * @param board - 게시글 등록에 필요한 정보
	 * @return - 테이블에 삽입된 행의 개수
	 */
	int create(BoardVO board) throws Exception;
	
	/**
	 * @param bno - 조회할 게시글 번호
	 * @return - PK로 조회된 1개의 게시글 정보 반환
	 */
	BoardVO read(int bno) throws Exception;
	
	/**
	 * @param board - 수정할 게시글 정보
	 * @return - 수정된 행의 개수
	 */
	int update(BoardVO board) throws Exception;
	
	/**
	 * @param bno - 삭제할 게시글 번호
	 * @return - 처리 후 삭제된 행의 개수 반환
	 */
	int delete(int bno) throws Exception;
	
	/**
	 * @param bno - 조회수 증가할 게시글 번호
	 * 일치하는 게시글 번호를 가진 행의 viewCnt(조회수) 컬럼의 데이터를 1 증가 
	 */
	void updateCnt(int bno) throws Exception;
	
	/**
	 * @return - 등록된 전체 게시글(행) 개수
	 */
	int totalCount() throws Exception;
	
	/**
	 * @param cri - 검색 기준
	 * @return - 검색 기준에 따라 조회된 게시글 목록
	 */
	List<BoardVO> listCriteria(Criteria cri) throws Exception;
	
}
