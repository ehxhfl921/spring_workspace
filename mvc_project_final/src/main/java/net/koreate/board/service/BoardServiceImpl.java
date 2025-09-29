package net.koreate.board.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import net.koreate.board.dao.AttachmentDAO;
import net.koreate.board.dao.BoardDAO;
import net.koreate.board.vo.BoardVO;
import net.koreate.common.utils.PageMaker;
import net.koreate.common.utils.SearchCriteria;
import net.koreate.common.utils.SearchPageMaker;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

	private final BoardDAO dao;
	private final AttachmentDAO attachDAO;
	
	@Override
	public String register(BoardVO vo) throws Exception {
		// 게시글 등록 기능 구현
		int result = dao.regist(vo);
		
		System.out.println(vo);
		
		// 등록 게시글의 첨부 파일 이름 목록
		List<String> files = vo.getFiles();
		if(files != null && !files.isEmpty()) {
			// 게시글 번호
			for(String file : files) {
				attachDAO.addAttachment(file, vo.getBno());
			}
		}
		return result == 1 ? "게시글 등록 성공" : "게시글 등록 실패";
	}
	
	@Override
	public BoardVO read(int bno) throws Exception {
		// 한개의 게시글 정보 반환 구현
		BoardVO vo = dao.read(bno);
		
		// 게시글에 등록된 첨부파일 목록 검색 및 추가
		List<String> files = attachDAO.getAttach(bno);
		vo.setFiles(files);
		return vo;
	}
	
	@Override
	public void updateCnt(int bno) throws Exception {
		// 조회수 증가 기능 구현
		dao.updateCnt(bno);
	}
	
	@Override
	public String modify(BoardVO vo) throws Exception {
		// TODO 게시글 수정 기능 구현
		return null;
	}
	
	@Override
	public String remove(int bno) throws Exception {
		// TODO 게시글 삭제(삭제요청된 게시글) 기능 구현
		return null;
	}
	
	@Override
	public List<BoardVO> listReply(SearchCriteria scri) throws Exception {
		// 검색된 게시글 목록 기능 구현
		List<BoardVO> list = dao.listReply(scri);
		return list;
	}
	
	@Override
	public PageMaker getPageMaker(SearchCriteria scri) throws Exception {
		// 페이징 블럭 정보 출력 기능 구현
		int totalCount = dao.listCount(scri); // 전체 게시물 개수
		PageMaker pm = new SearchPageMaker(scri, totalCount, 5);
		
		return pm;
	}
	
	@Override
	public String replyRegister(BoardVO board) throws Exception {
		// TODO 질문에 대한 답변글 등록 기능 구현
		return null;
	}
	
}





