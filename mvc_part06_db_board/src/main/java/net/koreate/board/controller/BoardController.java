package net.koreate.board.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import net.koreate.board.service.BoardService;
import net.koreate.board.vo.BoardVO;
import net.koreate.common.util.Criteria;
import net.koreate.common.util.PageMaker;

@Controller
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService service;

	/**
	 *  게시글 작성 페이지 요청
	 *  GET : board/register
	 */
	@GetMapping("board/register")
	public void register() throws Exception{
		System.out.println("게시글 작성 페이지 요청");
		// "/WEB-INF/views/" + "board/register" + ".jsp"
		//  /WEB-INF/views/board/register.jsp
	}
	
	
	/**
	 * 	게시글 등록 요청 처리
	 * 	POST : board/register
	 */
	@PostMapping("board/register")
	public String registerPost(BoardVO board, HttpSession session) throws Exception {
		System.out.println("param data : " + board);
		String result = service.regist(board);
		session.setAttribute("msg", result);
		return "redirect:/";
	}
	
	/**
	 * 	게시글 상세 보기 요청 시 조회수 증가
	 * 	GET : board/readPage
	 */
	@GetMapping("board/readPage")
	public String readPage(int bno, Model model) throws Exception{
		// 조회수 증가
		service.updateCnt(bno);
		
		// 상세 보기 게시글 정보
		BoardVO board = service.read(bno);
		model.addAttribute("boardVO", board);
		
		return "board/read";
	}
	
	/**
	 * 	게시글 수정 페이지 요청
	 * 	GET : board/modify
	 */
	@GetMapping("board/modify")
	public void modify(int bno, Model model) throws Exception{
		BoardVO board = service.read(bno);
		model.addAttribute(board); // boardVO
	}
	
	/**
	 * 	게시글 수정 요청 처리 -> 수정된 게시글 상세 보기 페이지 이동
	 * 	POST : board/modify
	 */
	@PostMapping("board/modify")
	public String modify(BoardVO board, HttpSession session) throws Exception{
		String result = service.modify(board);
		session.setAttribute("result", result);
		return "redirect:/board/readPage?bno=" + board.getBno();
	}
	
	/**
	 *  게시글 삭제 요청 처리 -> 게시글 삭제 후 목록 페이지 이동
	 *  GET : board/remove
	 */
	@GetMapping("board/remove")
	public String remove(int bno, HttpSession session) throws Exception{
		String result = service.remove(bno);
		session.setAttribute("result", result);
		return "redirect:/board/listPage";
	}
	
	/**
	 *  페이징 처리된 게시글 목록 페이지 요청
	 *  GET : board/listPage
	 *  parameter : page, perPageNum
	 */
	@GetMapping("board/listPage")
	public String listPage(Criteria cri, Model model, HttpSession session) throws Exception{
		System.out.println("게시글 목록 listPage 요청");
		String result = (String)session.getAttribute("result");
		if(result != null) {
			model.addAttribute("result", result);
			session.removeAttribute("result");
		}
		System.out.println("listPage : " + cri);
		System.out.println("StartRow : " + cri.getStartRow());
		// 조회된 게시글 목록
		List<BoardVO> list = service.listCriteria(cri);
		model.addAttribute("list", list);
		PageMaker pm = service.getPageMaker(cri);
		model.addAttribute("pm", pm);
		return "board/listPage";
	}
	
	
}
