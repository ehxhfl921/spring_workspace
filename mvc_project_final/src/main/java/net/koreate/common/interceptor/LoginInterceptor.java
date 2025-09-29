package net.koreate.common.interceptor;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;
import net.koreate.user.dao.BanIPDAO;
import net.koreate.user.vo.BanIPVO;
import net.koreate.user.vo.UserVO;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
	
	@Autowired
	private BanIPDAO dao;
	
	@Override
	public void postHandle(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler,
			ModelAndView modelAndView) throws Exception {
		
		String method = request.getMethod();
		log.info("전송 방식 : {}", method);
		
		if(method.equalsIgnoreCase("GET")) {
			log.info("get 요청 postHandle 종료");
			return;
		}
		
		// POST - 로그인 요청 처리 완료 후
		// 현재 요청 사요자의 컴퓨터(IP) 주소
		String ip = request.getRemoteAddr();
		
		// 이전 실패 기록 존재 확인
		BanIPVO banVO = dao.getBanIPVO(ip);
		log.info("로그인 실패 기록 : {}", banVO);
		
		HttpSession session = request.getSession();
		UserVO user = (UserVO)session.getAttribute("userInfo");
		
		
		if(user != null) {
			// 로그인 성공
			
			if(banVO != null) {
				// 로그인 실패 기록이 존재하면 기존 기록 제거
				dao.removeBanIP(ip);
			}
			
			String rememberme = request.getParameter("rememberme");
			log.info("rememberme : {}", rememberme);
			if(rememberme != null) {
				// 로그인 정보 저장 체크
				
				String u_id = user.getU_id();
				/*
				 * Encoding - Base64
				 * 0 ~ 64까지 아스키 코드의 값을 조합하여 표준화된 text를 생성하는 class
				 */
				byte[] encodedId = Base64.getEncoder().encode(u_id.getBytes());
				log.info("u_id : {} ", u_id);
				String encodedText = new String(encodedId); 
				log.info("encodedId : {} ", encodedId);
				
				Cookie cookie = new Cookie("rememberme", encodedText);
				cookie.setPath("/");	// 지정 안 하면 contextPath
				cookie.setMaxAge(60 * 60 * 24 * 15);	// 15일
				response.addCookie(cookie);
			}
			
		}else {
			// 로그인 실패
			String message = "";
			// 시도 가능한 횟수
			int count = 5;
			
			if(banVO == null) {
				// 최초 실패
				dao.signInFail(ip); // 실패 기록 등록
				count--;
			}else {
				// 누적(중복) 실패
				int failCount = banVO.getCnt();
				// 5 - 기존 실패 횟수 + 이번 실패(1)
				count = count - (failCount + 1);
				
				// database 시도횟수 update
				dao.updateBanIPCnt(ip);
			}
			
			if(count > 0) {
				// 시도 횟수가 남음
				message = "회원 정보가 일치하지 않습니다. 남은 횟수 : "+count;
			}else {
				// 시도 횟수가 없음
				message = "로그인 실패 5회 - 30분 동안 로그인이 제한됩니다.";
			}
			
			modelAndView.addObject("message", message);
			modelAndView.setViewName("/user/login");
			
		} // check login success
	} // end postHandle

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		if(request.getMethod().equalsIgnoreCase("get")) {
			// 로그인 페이지 요청 시
			return true;
		}
		
		// POST u_id, u_pw 입력 후 로그인 처리 요청 시
		// 요청자 ip 주소
		String ip = request.getRemoteAddr();
		log.info("preHandle IP : {}", ip);
		// 기존 로그인 실패 기록 정보
		BanIPVO banIP = dao.getBanIPVO(ip);
		log.info("preHandle ban : {}", banIP);
		
		// 실패 기록이 존재하고 시도 횟수가 5번 이상이면 로그인 시도 차단
		if(banIP != null && banIP.getCnt() >= 5) {
			int limit = 1000 * 60 * 30;		// milisecond : 30분
			log.info("제한 시간 : {}", limit);
			// Date 객체에 저장된 시간 정보를 milisecond으로 반환
			long bandate = banIP.getBandate().getTime();
			log.info("마지막 시도 시간 : {}", bandate);
			long now = System.currentTimeMillis();
			log.info("요청 현재 시간 : {}", now);
			long saveTime = limit - (now - bandate);
			log.info("남은 시간 : {}", saveTime);
			
			if(saveTime > 0) {
				log.info("제한 시간이 남아 있음");
				SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
				String time = sdf.format(new Date(saveTime));
				log.info("남은 시간 : {}", time);
				
				
				RequestDispatcher rd = request.getRequestDispatcher(
				"/WEB-INF/views/user/login.jsp" ); request.setAttribute("message",
				"로그인을 요청할 수 없습니다. "); request.setAttribute("time", saveTime); // 남은 시간
				rd.forward(request, response);
				  
				return false;
				 
			}else {
				log.info("제한 시간 초과");
				// 30분 제한 시간 초과 시 실패 기록 삭제
				dao.removeBanIP(ip);
			}
		}
		
		
		return true;
	}
	

} 


