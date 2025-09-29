package net.koreate.common.controller;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class EmailController {

	private final JavaMailSender mailSender;

	@GetMapping("/checkEmail")
	@ResponseBody
	public String sendMail(
			@RequestParam("u_id") String email
			)throws Exception{
		System.out.println(email);
		String code = "";
		for(int i=0; i<5; i++) {
			code += (int)(Math.random()*10);
		}
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper
			= new MimeMessageHelper(message,"UTF-8");
		helper.setFrom("chlrlrms1@gmail.com");
		helper.setTo(email);
		helper.setSubject("이메일 인증 코드 확인");
		helper.setText("다음 인증 코드를 입력해주세요.<h3>["+code+"]</h3>",true);
		mailSender.send(message);
		System.out.println("발신 완료");		
		return code;
	}
	
}
