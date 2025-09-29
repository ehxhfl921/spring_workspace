package net.koreate.board.service;

import java.util.List;

import net.koreate.board.vo.BoardVO;
import net.koreate.common.utils.PageMaker;
import net.koreate.common.utils.SearchCriteria;

public interface BoardService {
	
	/**
	 * 게시글(원본글-질문글) 작성
	 */
	String register(BoardVO vo)throws Exception;
	
	/**
	 * 게시글 상세보기
	 */
	BoardVO read(int bno) throws Exception;
	
	/**
	 * 조회수 증가
	 */
	void updateCnt(int bno) throws Exception;
	
	/**
	 * 게시글 수정
	 */ 
	String modify(BoardVO vo)throws Exception;
	
	/**
	 *  게시글 삭제
	 */
	String remove(int bno)throws Exception;
	
	/**
	 * 검색 게시글 목록
	 */
	List<BoardVO> listReply(SearchCriteria scri) throws Exception;
	
	/**
	 * 게시글 페이징 블럭 정보
	 */
	PageMaker getPageMaker(SearchCriteria scri) throws Exception;

	/**
	 * 답변글 등록
	 */
	String replyRegister(BoardVO board) throws Exception;
	
}












