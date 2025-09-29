package net.koreate.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	
	// GET "/" 경로로 요청이 들어오면 반환된 문자열로 home.jsp page 응답
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "home";
	}
	
	@GetMapping("responseData")
	public String responseData() {
		return "resData";
	}
	
	@GetMapping("jsAJAX")
	public String javascriptAJAX() {
		return "javascript";
	}
	
	@GetMapping("fetch")
	public void fetch() {}
}
