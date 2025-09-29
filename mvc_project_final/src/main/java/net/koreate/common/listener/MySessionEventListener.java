package net.koreate.common.listener;

import java.util.Hashtable;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import lombok.extern.slf4j.Slf4j;
import net.koreate.user.vo.UserVO;

/**
 *  Listener : 특정 이벤트가 발생했을 때 실행되는 동작(코드)을 정의하는 객체 또는 함수
 *  HttpSessionListener : HttpSession 객체 생성, 제거(무효화)될 때 제거 또는 생성된 session event 감지
 *  HttpSessionAttributeListener : 등록된 Session 객체들의 속성 정보(추가, 수정, 삭제 등) 변경 시 이벤트 감지
 */
@Slf4j
public class MySessionEventListener implements HttpSessionListener, HttpSessionAttributeListener{

	// session 에 userInfo 속성이 추가된 HttpSession 객체 저장
	private static Hashtable<String, HttpSession> sessionRepository = new Hashtable<>();
	
	/**
	 * 사용자가 브라우저를 통해서 최초에 연결 요청을 전달하여 Session 객체가 생성되거나
	 * 기존의 session 객체를 무효화(invalidate)하거나 timeout으로 제거되었을 때
	 * 새롭게 Session 객체가 생성되면 생성된 Session 정보와 함께 호출되는 method
	 */
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		log.info("생성된 SESSION {}", session.getId());
	}

	/**
	 * Server에서 관리 중인 Session 객체가 timeout 또는 invalidate()로
	 * 제거되었을 때 호출되는 method
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		log.info("제거된 SESSION {}", session.getId());
		// 서버에서 제거된 session 정보가 repository에 등록되어 있으면 제거
		sessionRepository.remove(session.getId());
	}

	/**
	 * 등록된 session 객체에 새로운 속성이 추가되면 호출되는 메서드
	 */
	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
		log.info("Added 호출");
		HttpSession session = event.getSession();
		log.info("SESSION ID : {}", session.getId());
		log.info("SESSION ADD name : {}, value : {}", event.getName(), event.getValue());
		
		if(event.getName().equals("userInfo")) {
			
			// 새로운 로그인으로 session 추가된 사용자 정보
			UserVO newUser = (UserVO)event.getValue();
			log.info("=================== newUser : {}", newUser);
			
			
			/*
			
			// 1. 기존 사용자 로그인 해제 - 새로운 사용자 로그인 정보 활용
			// 기존에 로그인 정보가 추가된 session 정보 확인
			for(HttpSession ses : sessionRepository.values()) {
				// 기존에 등록된 로그인된 사용자 정보
				UserVO user = (UserVO)ses.getAttribute("userInfo");
				
				if(user != null && user.getU_no() == newUser.getU_no()) {
					// newUser 는 중복 로그인 사용자
					// 기존 사용자의 로그인 정보 userInfo 제거
					// ses.removeAttribute("userInfo");
					ses.setAttribute("expire", "새로운 PC에서 로그인하여 로그아웃됩니다.");
					break;
				} // end if
			} // end for
			
			 */
			
			
			// 2. 기존 로그인된 사용자 정보가 있을 시 새로운 로그인 차단
			for(HttpSession ses : sessionRepository.values()) {
				UserVO user = (UserVO)ses.getAttribute("userInfo");
				if(user != null && user.getU_id().equals(newUser.getU_id())) {
					session.setAttribute("expire", "이미 로그인 중인 사용자입니다. 중복 로그인을 허용하지 않습니다.");
					return; // 새로운 사용자의 정보를 추가하지 않고 종료
				}
			} // 기존 유저 체크
			
			
			// userInfo 속성을 가진(로그인 된 사용자 정보를 가진) session 정보 저장
			sessionRepository.put(session.getId(), session);
		} // check userInfo if
	} // end attributeAdded()

	/**
	 * 등록된 session 객체에 기존 속성이 제거되면 호출되는 메서드
	 */
	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
		log.info("Removed 호출");
		HttpSession session = event.getSession();
		log.info("SESSION ID : {}", session.getId());
		log.info("SESSION REMOVE name : {}, value : {}", event.getName(), event.getValue());
	
		if(event.getName().equals("userInfo")) {
			// usrInfo가 제거된 session 정보는 repository에서 제거
			sessionRepository.remove(session.getId());
		}
	}

	/**
	 * 등록된 session 객체에 기존 속성의 값이 변경되면 호출되는 메서드
	 * 중복 키값으로 속성값이 추가되었을 때 호출
	 */
	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
		log.info("Replaced 호출");
		HttpSession session = event.getSession();
		log.info("SESSION ID : {}", session.getId());
		log.info("SESSION REPLACE name : {}, value : {}", event.getName(), event.getValue());
	}

	
	
}
