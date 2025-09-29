package net.koreate.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.koreate.vo.BoardVO;

@Controller
public class HomeController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "home";
	}
	
	@GetMapping("write") // == return "write";
	public void write() {} // /WEB-INF/views/write.jsp
	
	@PostMapping("write")
	public String writePost(
				//String title, String writer, String content,
				HttpServletRequest request,
				BoardVO board
			) throws UnsupportedEncodingException {

		// 요청 파라미터를 한 번이라도 읽고 난 뒤에는 변경되지 않음
		request.setCharacterEncoding("UTF-8");
		
		String title = request.getParameter("title");
		String writer = request.getParameter("writer");
		String content = request.getParameter("content");
		
		System.out.println("title : " + title);
		System.out.println("writer : " + writer);
		System.out.println("content : " + content);
		System.out.println("----------------------------------------------");
		System.out.println(board);
		System.out.println("----------------------------------------------");
		
		return "redirect:/";
	}
}
