package net.koreate.di.controller.home;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import net.koreate.di.dao.TestDAO;
import net.koreate.di.service.TestService;

@Controller
@RequiredArgsConstructor
public class TestController {
	
	private final TestService ts;
	private final TestDAO td;
	
	
	/*
	public TestController(TestService ts, TestDAO td) {
		this.ts = ts;
		this.td = td;
	}
	*/
	
	/**
	 *  @PostConstruct
	 *  스프링에 의해서 객체가 생성된 뒤 객체가 동작할 수 있도록 
	 *  의존성 주입이 완료되면 서버 구동 시 최초에 한 번 실행되는 method
	 */
	@PostConstruct
	public void init() {
		System.out.println("Test PostConstruct");
		ts.testService("TestController ");
		// td = new TestDAOImpl(); // final field 변경 불가 (재할당 불가)
		td.select("TestController ");
	}
	
}
