package net.koreate.comment.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.koreate.comment.dao.CommentDAO;
import net.koreate.comment.vo.CommentVO;
import net.koreate.common.util.Criteria;
import net.koreate.common.util.PageMaker;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

	private final CommentDAO dao;
	
	@Override
	public List<CommentVO> commentList(int bno) throws Exception {
		return dao.commentList(bno);
	}

	@Override
	public String addComment(CommentVO vo) throws Exception {
		int result = dao.insert(vo);
		return result == 0 ? "요청 처리 실패" : "요청 처리 성공";
	}

	@Override
	public String updateComment(CommentVO vo) throws Exception {
		int result = dao.update(vo);
		return result == 1 ? "댓글 수정 완료" : "댓글 수정 실패";
	}

	@Override
	public String deleteComment(int cno) throws Exception {
		return dao.delete(cno) == 1 ? "삭제 성공" : "삭제 실패";
	}

	@Override
	public Map<String, Object> commentListPage(int bno, Criteria cri) throws Exception {
		Map<String, Object> map = new HashMap<>();
		List<CommentVO> list = dao.listPage(bno, cri);
		/*
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("bno", bno);
		paramMap.put("cri", cri);
		List<CommentVO> list = dao.listPage(paramMap);
		*/
		map.put("list", list);
		
		PageMaker pm = new PageMaker();
		pm.setCri(cri);
		
		int totalCount = dao.totalCount(bno);
		pm.setTotalCount(totalCount);
		
		map.put("pm", pm);
		
		return map;
	}

}
