package net.koreate.valid;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import net.koreate.common.service.UserService;
import net.koreate.valid.vo.ValidationUserVO;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Controller
@RequiredArgsConstructor
public class HomeController {
	
	private final UserService us;
	private final DefaultMessageService messageService;
	private final JavaMailSender mailSender;
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "home";
	}
	
	@GetMapping("regex")
	public void regex() {}
	
	@GetMapping("user/join")
	public String join() {
		// /WEB-INF/views/user/join.jsp
		return "user/join";
	}
	
	@GetMapping("/user/uidCheck")
	@ResponseBody
	public boolean isCheck(String u_id) {
		boolean isCheck = false;
		System.out.println("u_id : "+ u_id);
		if(u_id != null && !u_id.equals("chlrlrms@gmail.com")) {
			// u_id가 존재하고 chlrlrms@gmail.com이 아니면 사용 가능한 아이디
			isCheck = true;
		}
		// false - 등록된 이메일 아이디
		return isCheck;
	} // uid check
	
	// 메일 발송
	@GetMapping("user/checkEmail")
	@ResponseBody
	public String sendMail(String u_id) throws Exception{
		String code = "";
		for(int i = 0; i < 6; i++) {
			code += (int)(Math.random() * 10);
		}
		System.out.println("발신 code : "+code);
		
		// code 메일 발송
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
		helper.setFrom("ssunllo9898@gmail.com", "CGG MASTER");
		helper.setTo(u_id);	// 사용자가 작성한 아이디(이메일)를 수신자로 지정
		helper.setSubject("CGG 이메일 인증 코드");
		helper.setText("다음 인증 코드를 입력해 주세요.<h3>["+code+"]</h3>", true);
		mailSender.send(message);
		System.out.println("발신 완료");
		return code;
	}
	
	@PostMapping("user/sendSMS")
	@ResponseBody
	public Map<String, String> sendSMS(String userPhoneNumber) throws Exception{
		
		// 코드 생성
		String code = "";
		for(int i = 0; i < 5; i++) {
			code += (int)(Math.random() * 10);
		}
		
		Message message = new Message();
		message.setFrom("01076583382");
		message.setTo(userPhoneNumber);
		message.setText("CGG에서 전송한 메세지입니다. ["+code+"] 코드를 확인 후 입력!");
		SingleMessageSentResponse response = this.messageService.sendOne(
				new SingleMessageSendingRequest(message)
		);
		logger.info("메세지 발송 응답 : {}", response);
		
		Map<String, String> map = new HashMap<>();
		map.put("code", code);							// 문자 인증 코드
		map.put("result", response.getStatusCode());	// 메세지 발신 응답 코드
		
		return map;
	}
	
	@PostMapping("user/joinPost")
	public String joinPost(
				ValidationUserVO vo,
				MultipartFile profileImage
			) throws Exception {
		logger.info("joinPost : {}", vo);
		logger.info("profileImage : {}", profileImage);
	
		String message = us.memberJoin(vo, profileImage);
		
		logger.info("result message : {} ", message);
		
		return "redirect:/"; // home으로 리다이렉트
	}
	
}
