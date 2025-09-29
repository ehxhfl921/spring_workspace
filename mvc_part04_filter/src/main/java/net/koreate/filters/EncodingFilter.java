package net.koreate.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class EncodingFilter implements Filter{

	// request encoding 방식을 저장할 필드 선언
	private String encoding;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException{
		System.out.println("EncodingFilter init() 호출");
		// 초기화 파라미터들 중 param-name이 encoding인 파라미터
		encoding = filterConfig.getInitParameter("encoding");
		System.out.println("init param encoding : " + encoding);
		if(encoding == null) {
			encoding = "UTF-8";
		}
		System.out.println("EncodingFilter init() 종료");
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("EncodingFilter doFilter() 호출");
		// 전처리
		/**
		 *  ServletRequest -> HttpServletRequest의 상위 클래스
		 *  프로토콜(HTTP, FTP 등)에 의존하지 않는 일반적인 요청 처리를 위한 인터페이스
		 */
		// Http Protocol 기능을 사용하기 위해서 타입 변환이 필요함
		HttpServletRequest req = (HttpServletRequest)request;
		String method = req.getMethod();
		// request 전송 방식이 POST 방식인 경우
		if(method.equalsIgnoreCase("POST")) {
			// Servlet으로 요청이 전달되기 전에 parameter encoding 정보 변경
			req.setCharacterEncoding(encoding);
		}
		System.out.println("request encoding : " + req.getCharacterEncoding());
		// FilterChain에 등록된 다른 Filter의 doFilter 호출
		chain.doFilter(request, response);
		// FilterChain에 등록된 모든 Filter의 doFilter method가 전처리를 완료하고 나면
		// Servlet으로 요청 전달 -> Servlet이 요청 처리를 완료하고 나면 doFilter method 호출도 종료
		// 후처리
		String uri = req.getRequestURI();
		System.out.println("encodingFilter request URI : " + uri);
		if(uri.contains("write")) {
			System.out.println("write 포함");
			// forward 방식으로 Servlet이 JSP 문서 출력을 완료한 상태이므로
			// JSP 페이지에 출력에 필요한 medel data 전달 불가
			req.setAttribute("write", "Encoding Filter write");
		}
		System.out.println("EncodingFilter doFilter() 종료");
	}

	@Override
	public void destroy() {
		System.out.println("EncodingFilter destroy() 호출");
	}
}
