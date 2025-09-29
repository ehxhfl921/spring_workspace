package net.koreate.valid;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations = {
			// "file:src/main/webapp/WEB-INF/spring/root-context.xml"
			"classpath:root-context.xml"
		}
)
@Slf4j
public class MailSenderTest {

	@Autowired
	private JavaMailSender mailSender;
	
	@Test
	public void mailTest() {
		log.info("mail sender : {}", mailSender);
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
		try {
			// 발신자, 수신자, 제목, 내용, 참조, 숨은 참조
			helper.setFrom("ssunllo9898@gmail.com", "MASTER");
			helper.setTo("ssunllo@naver.com");
			// helper.setBcc(null); // 숨은 참조
			// helper.setCc(null);  // 참조
			helper.setSubject("제목 : 이메일 테스트");
			// true : HTML 포함, 기본 값은 false
			helper.setText("<h1>HTML 포함 메세지 확인</h1>", true);
			
			// 메일 발송 요청 - 요청 처리 완료 시까지 대기 (blocking)
			mailSender.send(message);
			// 오류 없이 발신
			log.info("mail send 완료!");
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}










