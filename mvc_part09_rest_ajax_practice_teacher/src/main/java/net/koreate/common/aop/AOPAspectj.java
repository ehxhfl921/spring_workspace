package net.koreate.common.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
/**
 *  pom.xml org.aspectj.aspectjtools 의존성(dependency) 추가
 *  
 *  servlet-context.xml
 *  component-scan @Aspect class package 지정
 *  <aop:aspectj-autoproxy /> 태그 추가
 */
@Aspect // AOP advice가 있는 class임을 명시
@Component
@Slf4j
public class AOPAspectj {

	public AOPAspectj() {
		log.info("AOPAspectj Constructor");
	}
	
	// AOP Advice 기능 정의
	/**
	 * JoinPoint : target 객체에서 호출된 실제 메서드 정보를 저장하는 객체
	 * execution(* net.koreate.*.service.*.*(..))
	 * execution(반환 타입 targetClass.method(Parameters)) 
	 */
	@Before("execution(* net.koreate.msg.service.MessageServiceImpl.*(..))")
	public void startLog(JoinPoint jp) throws RuntimeException{
		log.info("--------------------------------------------");
		log.info("------------- START LOG BEGIN --------------");
		log.info("target : " + jp.getTarget()); 					// 대상 객체
		// 실행(호출) 메서드 이름
		log.info("method name : " + jp.getSignature().getName());
		// 호출된 메서드의 매개 변수 인자 값
		Object[] args = jp.getArgs();
		log.info("parameters : " + Arrays.toString(args));
		log.info("-------------- START LOG END ---------------");
		log.info("--------------------------------------------");
	}
	
	/**
	 *  target method가 작업 수행을 정상적으로 처리하고 난 후 결과 값을 반환(return)한 후에 실행
	 */
	@AfterReturning(
			pointcut = "execution(!void net.koreate.*.service.*.*(..))",
			returning = "returnValue"
	)
	public void successLog(JoinPoint jp, Object returnValue) {
		log.info("------------------------------------------");
		log.info("------- START AfterReturning BEGIN -------");
		log.info("target : " + jp.getTarget());
		log.info("name : {}", jp.getSignature().getName());
		log.info("return value : {}", returnValue);
		log.info("-------- START AfterReturning END --------");
		log.info("------------------------------------------");
	}
	
	/**
	 *  target method에서 예외가 발생하면 실행되는 Advice
	 */
	@AfterThrowing(
			value = "execution(* net.koreate.*.service.*.*(..))",
			throwing="ex"
	)
	public void endThrowing(JoinPoint jp, Exception ex) {
		log.info("-----------------------------------------");
		log.info("-------- START endThrowing BEGIN --------");
		log.info("target : " + jp.getTarget());
		log.info("name : {}", jp.getSignature().getName());
		log.info("error : {}", ex.getMessage());
		log.info("--------- START endThrowing END ---------");
		log.info("-----------------------------------------");
	}
	
	@After("execution(* net.koreate.*.service.*.*(..))")
	public void endLog() {
		log.info("------------- END LOG AFTER -------------");
		log.info("-----------------------------------------");
	}
	
	/**
	 *  proceed : 진행하다, 계속하다
	 *  ProceedingJoinPoint
	 */
	@Around("execution(* net.koreate.*.controller.*.*(..))")
	public Object controllerLog(ProceedingJoinPoint pjp) throws Throwable{
		log.info("-----------------------------------------------------");
		log.info("---------------- AROUND BEFORE START ----------------");
		log.info("target : {}", pjp.getTarget());
		log.info("name : {}", pjp.getSignature().getName());
		log.info("parameters : {}", Arrays.toString(pjp.getArgs()));
		log.info("---------------- AROUND BEFORE END -----------------");
		
		// Before
		Object obj = pjp.proceed(); // target 객체의 실체 메서드 호출
		// 반환된 결과는 void 타입일 경우 null
		// After
		log.info("---------------- AROUND AFTER START ----------------");
		log.info("return value : {} ", obj);
		log.info("----------------- AROUND AFTER END -----------------");
		log.info("-----------------------------------------------------");
		return obj; // target 메서드에서 반환된 결과를 외부에서 호출한 위치에 반환
		
	}
	
}
