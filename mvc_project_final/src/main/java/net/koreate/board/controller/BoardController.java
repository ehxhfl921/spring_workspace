package net.koreate.board.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import net.koreate.board.service.BoardService;
import net.koreate.board.vo.BoardVO;
import net.koreate.common.utils.PageMaker;
import net.koreate.common.utils.SearchCriteria;

@Controller
@RequestMapping("/board/*")
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService bs;
	
	/**
	 * 게시글 목록 페이지 요청
	 * 검색 요청 시 검색 결과 내의 페이징 처리된 게시글 목록 출력
	 * page : 요청 페이지
	 * perPageNum : 보고 싶은 페이지의 게시물 개수
	 * searchType : 검색 컬럼 분류
	 * keyword : 검색 키워드
	 */
	@GetMapping("searchList")
	public String listReply(SearchCriteria cri, Model model)throws Exception{
		List<BoardVO> list = bs.listReply(cri);
		PageMaker pm = bs.getPageMaker(cri);
		model.addAttribute("list", list);
		model.addAttribute("pm", pm);
		return "board/searchList";
	}
	
	/**
	 * 게시글 작성 페이지
	 */
	@GetMapping("register")
	public String register() {
		return "board/register";
	}
	
	@PostMapping("register")
	public String registerPost(BoardVO vo, RedirectAttributes rttr) throws Exception{
		String msg = bs.register(vo);
		rttr.addFlashAttribute("msg", msg);
		return "redirect:/board/searchList";
	}
	
	/**
	 * 상세보기 요청 시 조회 수 증가
	 */
	@GetMapping("readPage")
	public String readPage(int bno, RedirectAttributes rttr)throws Exception{
		// 조회수 증가
		bs.updateCnt(bno);
		rttr.addAttribute("bno", bno);
		return "redirect:/board/read";
	}
	
	/**
	 * 상세보기 페이지 요청
	 */
	@GetMapping("read")
	public String read(int bno, Model model)throws Exception{
		// 조회수 증가 이후 실제 상세보기 ㅠㅔ이지 출력
		BoardVO board = bs.read(bno);
		model.addAttribute("board", board);
		return "board/readPage";
	}
	
	/**
	 * 답변글 작성 페이지 요청
	 */
	@GetMapping("replyRegister")
	public void replyRegister() throws Exception{}
	
	
	/**
	 * 수정 페이지 요청
	 */
	@GetMapping("modifyPage")
	public String modifyPage()throws Exception{
		return "board/modifyPage";
	}
	
}













