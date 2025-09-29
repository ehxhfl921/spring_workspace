package net.koreate.common.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogoutInterceptor implements HandlerInterceptor {@Override
	
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// expire message 제거
		request.getSession().removeAttribute("expire");
	
	
		// 로그아웃 처리 완료 후 - 사용자 브라우저에 등록된 쿠키 정보가 존재하면 제거
		
		Cookie cookie = WebUtils.getCookie(request, "rememberme");
		
		if(cookie != null) {
			cookie.setMaxAge(0);
			cookie.setPath("/");
			response.addCookie(cookie);
			log.info("쿠키 존재 - 브라우저에 쿠키 제거 응답");
		}else {
			
		}
		

	}

	
}
