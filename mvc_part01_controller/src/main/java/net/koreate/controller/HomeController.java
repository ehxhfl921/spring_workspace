package net.koreate.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *	component-scan으로 Spring Bean으로 등록되는 annotation
 *  @Controller annotation이 부여된 class는
 *  		    내부의 mapping method를 통해서 요청 처리에 호출되는 method가 등록됨
 *  <annotation-driven/> 
 */
@Controller
public class HomeController {
	
	public HomeController() {
		System.out.println("HomeController 객체 생성");
	}
	
	/**
	 *  GET 방식의 "main.home" 요청 처리
	 */
	@RequestMapping(value = "main.home", method = RequestMethod.GET)
	public String home(HttpSession session) {
		session.setAttribute("test", "HomeController home()");
		// viewResolver
		// "/WEB-INF/views"+home+".jsp"
		// "/WEB-INF/views/home.jsp"
		// forward 방식으로 web 문서 출력
		return "home";
	}
	
}
