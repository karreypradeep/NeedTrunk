package com.apeironsol.need.util.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LoggingAspect {

	Logger	log	= Logger.getLogger(LoggingAspect.class.getName());

	// @Pointcut("execution(* *..service.Service+.*(..))")
	@Pointcut("within(@org.springframework.stereotype.Service *)")
	public void servicePointcut() {
	}

	// @Pointcut("execution(* *..dao.Dao+.*(..))")
	@Pointcut("within(@org.springframework.stereotype.Repository *)")
	public void daoPointcut() {
	}

	@Before(value = "servicePointcut()")
	public void traceCallExecution(final JoinPoint joinPoint) {
		String klass = joinPoint.getTarget().getClass().getName();
		Logger log = Logger.getLogger(klass);
		String method = joinPoint.getSignature().getName();
		log.info("INVOKED : " + klass + "." + method);
	}

	@AfterThrowing(value = "servicePointcut()", throwing = "e")
	public void logServiceLayerException(final JoinPoint joinPoint, final Throwable e) {
		Logger log = Logger.getLogger(joinPoint.getTarget().getClass().getName());
		log.error("ERROR ON SERVICE LAYER : " + joinPoint.getSignature(), e);

	}
}
