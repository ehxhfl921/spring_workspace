package net.koreate.common.interceptor;

import java.util.Base64;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.WebUtils;

import lombok.extern.slf4j.Slf4j;
import net.koreate.user.service.UserService;
import net.koreate.user.vo.UserVO;

@Slf4j
public class checkCookieInterceptor implements HandlerInterceptor {
	
	@Autowired
	private UserService us;
	
	@Override
	public boolean preHandle(
			HttpServletRequest request, 
			HttpServletResponse response, Object handler)throws Exception {
		
		HttpSession session = request.getSession();
		if(session.getAttribute("userInfo") != null) {
			log.info("이미 로그인 상태");
			return true; // controller mapping method로 요청 전달
		}
		
		// Cookie 확인
		// request로 전달된 Cookie 목록에서 rememberme라는 이름의 쿠키 정보 검색
		Cookie cookie = WebUtils.getCookie(request, "rememberme");
		if(cookie != null) {
			log.info("rememberme 쿠키 존재 : {} ", cookie.getName());
			// rememberme 쿠키 존재
			String value = cookie.getValue();
			log.info("rememberme 쿠키 값 : {} ", value);
			// Decoding - Base64
			byte[] decode = Base64.getDecoder().decode(value.getBytes());
			String u_id = new String(decode);
			log.info("decoded u_id : {} ", u_id);
			
			UserVO user = us.getUserById(u_id);
			if(user != null) {
				log.info("session userInfo 추가 : {} ", user);
				session.setAttribute("userInfo", user);
			}
		}
		return true;
	}

	
	
}
