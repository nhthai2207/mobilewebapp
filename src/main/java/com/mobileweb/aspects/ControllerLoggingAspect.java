package com.mobileweb.aspects;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;


/**
 * Will log every invokation of @RequestMapping annotated methods in @Controller
 * annotated beans.
 */
@Aspect
public class ControllerLoggingAspect {
	static Logger log = Logger.getLogger("AspectController");
	
	@Pointcut("within(@org.springframework.stereotype.Controller *)")
	public void controller() {
	}

	@Pointcut("execution(* *(..))")
	public void methodPointcut() {
	}

	@Pointcut("within(@org.springframework.web.bind.annotation.RequestMapping *)")
	public void requestMapping() {
	}

	@Before("controller() && methodPointcut() && requestMapping()")
	public void aroundControllerMethod(JoinPoint joinPoint) throws Throwable {
		System.out.println("Invoked: " + niceName(joinPoint));
		log.info("Invoked: " + niceName(joinPoint));
	}

	@AfterReturning("controller() && methodPointcut() && requestMapping()")
	public void afterControllerMethod(JoinPoint joinPoint) {
		System.out.println("Finished: " + niceName(joinPoint));
		log.info("Finished: "  + niceName(joinPoint));
	}

	private String niceName(JoinPoint joinPoint) {
		return joinPoint.getTarget().getClass() + "#" + joinPoint.getSignature().getName() + "\n\targs:" + Arrays.toString(joinPoint.getArgs());
	}

}
