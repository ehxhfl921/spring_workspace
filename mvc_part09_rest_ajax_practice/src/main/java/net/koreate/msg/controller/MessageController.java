package net.koreate.msg.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.koreate.msg.service.MessageService;
import net.koreate.msg.vo.MessageVO;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
@Slf4j
public class MessageController {

	private final MessageService ms;
	
	@GetMapping("/list")
	public ResponseEntity<List<MessageVO>> readList() {
		ResponseEntity<List<MessageVO>> entity = null;
		List<MessageVO> list = null;
		try {
			list = ms.list();
			entity = new ResponseEntity<List<MessageVO>>(list, HttpStatus.OK);
		} catch (Exception e) {
			entity = new ResponseEntity<List<MessageVO>>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

	@PostMapping(value="/add", produces="text/plain;charset=utf-8")
	public ResponseEntity<String> addMessage(MessageVO vo) {
		ResponseEntity<String> entity = null;
		String message = null;
		
		
		try {
			ms.addMessage(vo);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
			log.info("메세지 추가 완료");
		} catch (Exception e) {
			message = "요청 처리 실패";
			entity = new ResponseEntity<String>(message, HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	/**
		 type : "PATCH",
		 url : "messages/read/"+mno+"/"+userid,
		 {mno} 읽은 메세지 번호
		 {userid} 수신자 아이디
	 */
	@PatchMapping("/read/{mno}/{userid}")
	public ResponseEntity<Object> readMessage(
				@PathVariable(name="mno") int mno,
				@PathVariable(name="userid") String userid
			){
		
		try {
			MessageVO result = ms.readMessage(userid, mno);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}











