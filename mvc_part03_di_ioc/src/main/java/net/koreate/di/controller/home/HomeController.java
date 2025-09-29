package net.koreate.di.controller.home;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.koreate.di.dao.TestDAO;
import net.koreate.di.service.TestService;

/**
 *  의존성 주입 (Dependency Injection, DI)
 *  객체 지향 프로그래밍에서 객체 간의 의존 관계를 외부에서 주입해 주는 패턴
 *    - 의존성(Dependency) : 어떤 객체가 다른 객체를 사용해야만 동작할 때, 그 관계를 의존성이라고 함
 *    - 주입(Injection)	 : 의존성을 직접 new로 생성하는 대신, 외부(컨테이너, 프레임워크, 설정 파일 등)에서 필요한 객체를 넣어(주입) 주는 것
 *  
 *  Spring 에서 의존성 주입이란 Spring Container가 관리하는 객체를 필요한 의존성에 자동으로 연결해 주는 것
 *   
 *  DI Annotation 의존성 주입 어노테이션
 *  
 *  			@Autowired			@Qualifier
 *  범용성		스프링 전용				스프링 전용
 *  연결성		타입에 맞춰서 주입		특정 개체의 이름을 이용해서 주입
 *  								@Autowired와 함께 사용 (독립 사용 x)
 *  
 *  javax.annotation-api
 *  
 *  			@Inject				@Named				@Resource
 *  범용성		자바에서 지원			자바에서 지원			자바에서 지원
 *  연결성		타입에 맞춰서 주입		특정 개체의 이름을		이름으로 검색 후 주입
 *   								이용해서 주입
 *   								@Inject와 함께 사용 
 *   								(독립 사용 x)
 */
@Controller
public class HomeController {
	
	@Autowired
	private TestService tsi; // new TestServiceImpl();
	
	@Inject		 // type으로 검색
	@Named("td") // 동일한 타입이 여러 개 존재할 경우 이름으로 선별
	private TestDAO dao;
	
	@Autowired
	@Qualifier("path")
	private String imgPath;
	
	@Resource(name="path")
	private String uploadPath; // @Resource : name="uploadPath"
	
	@Resource
	private int size;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		System.out.println("HomeController home() 호출");
		if(tsi != null) {
			tsi.testService("HomeController");
		}else {
			System.out.println("HomeController tsi is Null");
		}
		
		if(dao != null) {
			dao.select("HomeController home");
		}else {
			System.out.println("HomeController dao is Null");
		}
		
		System.out.println("img path : " + imgPath);
		System.out.println("upload path : " + uploadPath);
		System.out.println("int size : " + size);
		
		System.out.println("HomeController home() 종료");
		return "home";
	}
	
}
