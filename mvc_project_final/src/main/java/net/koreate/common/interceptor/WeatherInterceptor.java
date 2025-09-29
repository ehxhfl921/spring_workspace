package net.koreate.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import net.koreate.common.controller.WeatherService;

public class WeatherInterceptor implements HandlerInterceptor {

	@Autowired
	private WeatherService ws;
	
	@Override
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
			
		HttpSession session = request.getSession();
		
		if(session.getAttribute("weather") == null) {
			session.setAttribute("weather", ws.getWeaterInfo());
		}
		
	}

	
}
