package net.koreate.di.controller.second;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import net.koreate.di.dao.TestDAO;
import net.koreate.di.service.TestService;

@Controller
public class SecondController {

	@Autowired(required = false)
	private TestService ts;
	
	@Autowired
	private TestDAO td; // root-context
	
	@GetMapping("main")
	public String secondMain() {
		System.out.println("SecondController main 호출");

		if(ts != null) {
			ts.testService("SecondController ");
		}else {
			System.out.println("SecondController ts is Null");
		}

		if(td != null) {
			td.select("SecondController td");
		}else {
			System.out.println("SecondController TestDAO is Null");
		}
		
		return "home";
	}
	
}














