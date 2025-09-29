package net.koreate.comment.controller;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import net.koreate.comment.service.CommentService;
import net.koreate.comment.vo.CommentVO;
import net.koreate.common.util.Criteria;

/**
 *  댓글 관련된 요청은 공통적으로 comment 접두어로 시작되도록 지정
 */
@RequestMapping("/comment")
@RestController
@RequiredArgsConstructor
public class CommentController {

	private final CommentService cs;
	
	/**
	 *  ResponseEntity
	 *  HTTP 응답 전체를 표현하는 객체
	 *  단순히 데이터를 반환하지 않고 
	 *  HTTP 상태 코드(status code)
	 *  HTTP 헤더(Header)
	 *  응답 본문(Body)를 모두 포함해서 client에 전달할 수 있도록
	 *  DaspatcherServlet의 응답을 제어하는 객체
	 */
	
	// POST : "/comment"
	@PostMapping("")
	public ResponseEntity<String> addComment(CommentVO vo) {
		System.out.println("addComment : " + vo);
		ResponseEntity<String> entity = null;
		// 응답 헤더 정보를 저장하는 객체
		HttpHeaders headers = new HttpHeaders();
		// Server에서 Client로 전송되는 데이터 유형을 저장하는 객체
		MediaType mediaType = new MediaType("text", "plain", Charset.forName("utf-8"));
		headers.setContentType(mediaType);
		
		String message = null;
		try {
			message = cs.addComment(vo);
			// new ResponseEntity<String>("필수 매개 변수는 응답 코드");
			entity = new ResponseEntity<String>(message, headers, HttpStatus.OK); // 200 코드로 응답
			
		} catch (Exception e) {
			message = "잘못된 데이터로 인한 요청 처리 실패";
			entity = new ResponseEntity<String>(message, headers, HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	// 게시글에 등록된 모든 댓글 list 요청
	// comment/list/bno -> bno는 변화되는 요청 게시글 번호
	@GetMapping("/list/{bno}") // path variable에서 변화하는 데이터는 중괄호로 묶어서 표현
	public List<CommentVO> commentList(
				@PathVariable(name="bno") int bno
			) throws Exception{
		System.out.println("path variable bno : " + bno);
		List<CommentVO> list = cs.commentList(bno);
		return list;
	}
	
	
	// PATCH - comment/update
	// Mapping method에서 지정할 수 있는 속성
	// produces 컨트롤 메서드가 반환하는 데이터를 어떤 유형(Content-Type)으로 전달할지 알려주는 속성
	// consumes 컨트롤러 메서드가 처리할 요청의 Content-Type을 지정
	@PatchMapping(
			value="/update", 
			produces="text/plain;charset=utf-8",
			consumes="application/json"
			)
	public ResponseEntity<String> update(@RequestBody CommentVO vo){
		System.out.println(vo);
		
		try {
			String result = cs.updateComment(vo);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	} // end update()
	
	// DELETE - /comment/cno
	@DeleteMapping(value="/{cno}", produces="text/plain;charset=utf-8")
	public String delete(@PathVariable int cno) throws Exception{
		System.out.println("cno : "+cno);
		return cs.deleteComment(cno);
	}
	
	// comment/bno/page/perPageNum
	// comment/1/2/5
	@GetMapping("/{bno}/{page}/{perPageNum}")
	// Map : 페이징 처리된 댓글 목록 - List<CommentVO> list
	// 	   : 페이징 블럭 정보 - PageMaker pm
	// data : {list : List<CommentVO>, pm : PageMaker}
	public Map<String, Object> listPage(
				@PathVariable(name="bno") int bno,
				Criteria cri
			) throws Exception{
		System.out.println("bno : " + bno);
		System.out.println(cri);
		return cs.commentListPage(bno, cri);
	}
	
} // end CommentController
