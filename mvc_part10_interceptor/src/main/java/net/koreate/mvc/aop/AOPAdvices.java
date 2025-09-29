package net.koreate.mvc.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect		// AOP Advice 등록
@Component	// Spring Bean 등록 - component-scan
@Slf4j		// Logger log 추가
public class AOPAdvices {

	
	@Around("execution(* net.koreate.mvc.controller.HomeController.*(..))")
	public Object controllerLog(ProceedingJoinPoint pjp) throws Throwable{
		log.info("=================== START ===================");
		log.info("target : {}" , pjp.getTarget());
		log.info("method : {}", pjp.getSignature().getName());
		log.info("arguments : {}", Arrays.toString(pjp.getArgs()));
		Object o = pjp.proceed(); // 실체 메서드 호출
		if(o != null) {
			log.info("return value : {}", o);
		}
		log.info("==================== END ====================");
		return o;
	}
	
}
