package net.koreate.common.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import net.koreate.common.service.NewsService;
import net.koreate.common.vo.News;

@Controller
@RequiredArgsConstructor
public class HomeController {
	
	private final NewsService ns;
	
	private final WeatherService ws;
	
	@GetMapping("/")
	public String home(Model model) {
		List<News> jsoupNews = ns.getNewsList();
		model.addAttribute("jsoupNews", jsoupNews);
		model.addAttribute("weather", ws.getWeaterInfo());
		return "home";
	}
}













