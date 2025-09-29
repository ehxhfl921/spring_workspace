package net.koreate.valid;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations = {
			"file:src/main/webapp/WEB-INF/spring/root-context.xml"
			// "classpath:root-context.xml"
		}
)
@Slf4j
public class CoolSMSTest {

	@Autowired
	private DefaultMessageService messageService;

	/*
	@Before
    public void init() {
        // 반드시 계정 내 등록된 유효한 API 키, API Secret Key를 입력해주셔야 합니다!
        this.messageService = NurigoApp.INSTANCE.initialize(
        		"NCSCS8MDCQUEL1PO", "X51VINYUZ7SUKMJ8CSHCDJ62LW3M9QUZ", "https://api.coolsms.co.kr");
    }
    */
	
	@Test
	public void sendSMS() {
		log.info("message service : {}", messageService);
		Message message = new Message();
		// 회원 가입 시 등록한 발신 번호 입력
		message.setFrom("01076583382");
		// 010-0000-0000 : 나라에서 발급되지 않는 번호-수신되지 않는 번호
		// message.setTo("01094867166");
		message.setTo("01000000000");
		
		message.setText("[CGG 발신] - 히히");
		
		SingleMessageSentResponse response
			= this.messageService.sendOne(new SingleMessageSendingRequest(message));
		
		log.info("message response : {}", response);
	}
    
}
