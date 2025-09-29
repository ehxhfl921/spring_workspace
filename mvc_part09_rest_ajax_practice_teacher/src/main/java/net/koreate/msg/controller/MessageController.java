package net.koreate.msg.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.koreate.msg.service.MessageService;
import net.koreate.msg.vo.MessageVO;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
// lombok 라이브러리에서 해당 클래스에 로그 클래스를 생성하는 어노테이션
@Slf4j
public class MessageController {

	private final MessageService ms;
	
	@GetMapping("/list")
	public ResponseEntity<List<MessageVO>> readList() {
		try {
			List<MessageVO> list = ms.list();
			return ResponseEntity.ok(list);
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@PostMapping(value="/add", produces="text/plain;charset=utf-8")
	public ResponseEntity<String> addMessage(@RequestBody MessageVO vo) {
		// System.out.println("addMessage vo : " + vo);
		log.info("add message" + vo);
		ResponseEntity<String> entity = null;
		
		try {
			ms.addMessage(vo);
			log.info("addMessage 오류 없이 작업 수행 완료");
			entity = new ResponseEntity<>("SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
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
		
		ResponseEntity<Object> entity = null;
		
		try {
			MessageVO message = ms.readMessage(userid, mno);
			entity = new ResponseEntity<>(message, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
}











