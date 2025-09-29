package net.koreate.mvc.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

/**
 *	spring mvc 요청 처리 시 TestIntercepter가 어떤 요청을 가로챌 것인지 등록
 *	servlet-context(dispatcherServlet) 설정 
 */
@Slf4j
public class TestInterceptor implements HandlerInterceptor {

	/**
	 *  DispatcherServlet이 Controller의 Mapping method를 호출하기 전에 실행
	 *  return true = mapping method 호출
	 *  return false = mapping method를 호출하지 않고 preHandle에서 요청 처리 완료
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.info("================ START ================");
		// HandlerMethod : Controller의 method 정보를 저장하는 class
		HandlerMethod method = (HandlerMethod)handler;
		log.info("target controller : {} ", method.getBean());
		log.info("target method : {} ", method);
		
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestURI.substring(contextPath.length() +1);
		log.info("요청 경로 : {} ", command);
		
		if(command.equals("test3")) {
			response.sendRedirect("test1");
			log.info(" return false sendRedirect test1");
			return false; // 응답 완료
		}
		
		log.info("================= END =================");
		return true;
	}

	/**
	 *  Controller의 Mapping method가 실행 완료된 후 실행
	 *  (DispatcherServlet으로 결과 - ModelAndView가 반환되고 난 이후 실행)
	 */
	@Override
	public void postHandle(HttpServletRequest request, 
						   HttpServletResponse response, 
						   Object handler,
						   ModelAndView modelAndView) throws Exception {
		log.info("================ START ================");
		log.info("ModelAndView : {} ", modelAndView);
		
		Map<String, Object> models = modelAndView.getModel();
		for(String key : models.keySet()) {
			log.info("key : {} ", key);
			log.info("value : {} ", models.get(key));
			log.info("----------------------------------------");
		}
		
		String viewName = modelAndView.getViewName();
		log.info("view name : {} ", viewName);
		
		// viewName이 test4일 경우
		if(viewName.equals("test4")) {
			// 출력할 view 이름을 home으로 변경
			modelAndView.setViewName("home");
		}
		
		// 모델 정보 중 key가 result인 속성
		Object o = models.get("result");
		if(o == null) {
			// result를 key 값으로 한 속성이 존재하지 않으면 추가
			modelAndView.addObject("result", "postHandle JOB");
		}
		
		// home.jsp page에서 추가한 속성 : home
		String home = (String)request.getAttribute("home");
		log.info("home : {}", home);
		
		log.info("================= END =================");
	}

	/**
	 *  DispatcherServlet이 JSP 또는 응답 데이터를 출력 완료하고 난 후 실행
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		log.info("================ START ================");
		log.info("response content-type : {}", response.getContentType());
		log.info("response status : {}", response.getStatus());
		// home.jsp page에서 추가한 속성 : home
		String home = (String)request.getAttribute("home");
		log.info("home : {}", home);
		log.info("================= END =================");
	}

	
	
}
