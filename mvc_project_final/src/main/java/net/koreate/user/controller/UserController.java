package net.koreate.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import net.koreate.user.service.UserService;
import net.koreate.user.vo.UserVO;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Controller
@RequestMapping("/user/")
@RequiredArgsConstructor
public class UserController {
	
	private final DefaultMessageService messageService;
	
	private final UserService us;
	
	@GetMapping("/login")
	public String login() {
		/*
		String message = (String)session.getAttribute("message");
		if(message != null) {
			model.addAttribute("message", message);
			session.removeAttribute("message");
		}
		*/
		return "user/login";
	}
	
	@PostMapping("/login")
	public String loginPost(String u_id, String u_pw, HttpSession session) throws Exception{
		us.login(u_id, u_pw, session); // 로그인 요청 처리
		return "redirect:/"; // main 페이지로 이동
	}
	
	@GetMapping("join")
	public void join() {}

	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.removeAttribute("userInfo");
		return "redirect:/user/login";
	}
	
	@GetMapping("uidCheck")
	@ResponseBody
	public boolean uidCheck(String u_id) throws Exception{
		UserVO user = us.getUserById(u_id);
		System.out.println(user);
		return user == null ? true : false;
	}
	
	// 전화번호 인증 문자 메세지 전송
	@PostMapping("sendSMS")
	@ResponseBody
	public Map<String,String> sendSMS(
			String userPhoneNumber
			) throws Exception{
		
		// code 생성
		String code = "";
		for(int i=0; i<5; i++) {
			code += (int)(Math.random()*10);
		}
		
		Message message = new Message();
		
        message.setFrom("01094867166");
        
        message.setTo(userPhoneNumber);
        
        message.setText("CGG에서 전송한 메세지["+code+"]코드를 확인후 입력!");

        SingleMessageSentResponse response = this.messageService.sendOne(
        		new SingleMessageSendingRequest(message)
		);
        System.out.println(response);
	
        Map<String,String> map = new HashMap<>();
        map.put("code", code);
        map.put("result", response.getStatusCode());
        return map;
	}
	
	@PostMapping("joinPost")
	public String joinPost(
				UserVO vo,
				MultipartFile profileImage,
				RedirectAttributes rttr
						  )throws Exception{
		/**
		 * RedirectAttributes
		 * Redirect로 이동된 페이지에 속성 값 또는 파라미터를 전달하는 객체
		 * addAttribute(key, value); 		// get 방식의 parameter 추가
		 * addFlashAttribute(key, value);	// 리다이렉트로 이동된 페이지에서만 사용 가능한 속성값 추가
		 */
		
		String message = us.userJoin(vo, profileImage);
		// session.setAttribute("message", message);
		rttr.addFlashAttribute("message", message);
		return "redirect:/user/login";
	}
	
	
	
}


























