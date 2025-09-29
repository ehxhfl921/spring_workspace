package net.koreate.mvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.koreate.mvc.vo.TestVO;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		logger.info(" call!");
		return "home";
	}
	
	@GetMapping("test1")
	public String test1() {
		logger.info(" call!");
		return "home";
	}
	
	@PostMapping("test2")
	public String test2(@ModelAttribute("test") TestVO vo, Model model) {
		logger.info(" call!");
		logger.info("TestVO : {}", vo);
		model.addAttribute("result", "test2 job");
		logger.info(" END!");
		return "home";
	}
	
	@GetMapping("test3")
	public void test3() {
		logger.info("Call!");
	}
	
	@GetMapping("test4")
	public void test4() {
		logger.info("Call!");
	}
}
