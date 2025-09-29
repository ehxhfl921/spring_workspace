package net.koreate.comment.service;

import java.util.List;
import java.util.Map;

import net.koreate.comment.vo.CommentVO;
import net.koreate.common.util.Criteria;

public interface CommentService {

	/**
	 *  매개 변수로 받은 게시글 번호(bno)로 해당 게시글에 등록된 모든 댓글 리스트 반환
	 */
	List<CommentVO> commentList(int bno) throws Exception;
	
	/**
	 *  댓글 삽입 요청 처리 후 처리된 결과를 문자열로 반환
	 */
	String addComment(CommentVO vo) throws Exception;
	
	/**
	 *  댓글 수정 처리 후 처리된 결과를 문자열로 반환
	 */
	String updateComment(CommentVO vo) throws Exception;

	/**
	 *  댓글 번호를 매개 변수로 받아 댓글 삭제 후 결과를 문자열로 반환
	 */
	String deleteComment(int cno) throws Exception;
	
	/**
	 *  검색할 게시글 번호, 검색 범위 기준 정보를 이용하여 검색된 댓글 목록 리스트와 
	 *  페이징 블럭 출력에 필요한 PageMaker 객체를 Map에 저장하여 반환
	 */
	Map<String, Object> commentListPage(int bno, Criteria cri) throws Exception;
}
