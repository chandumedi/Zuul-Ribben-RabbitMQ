package com.stackroute.keepnote.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/* Annotate this class with @Aspect and @Component */
@Aspect
@Component
public class LoggingAspect {
	/*
	 * Write loggers for each of the methods of Category controller, any particular method
	 * will have all the four aspectJ annotation
	 * (@Before, @After, @AfterReturning, @AfterThrowing).
	 */
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Before(value = "execution(* com.stackroute.keepnote.controller.*.*(..))")
	public void beforeExecutingMethod(JoinPoint jp) {
		logger.info("Method execution started with method name " + jp.getSignature().getName());
	}

	@After(value = "execution(* com.stackroute.keepnote.controller.*.*(..))")
	public void afterExecutingMethod(JoinPoint jp) {
		logger.info("Method execution ended with method name " + jp.getSignature().getName());
	}

	@AfterReturning(value = "execution(* com.stackroute.keepnote.controller.*.*(..))", returning = "result")
	public void afterReturningExecutingMethod(JoinPoint jp, Object result) {
		logger.info("Method execution returned  with result "+ result );
	}

	@AfterThrowing(value = "execution(* com.stackroute.keepnote.controller.*.*(..))", throwing = "ex")
	public void afterThrowingExecutingMethod(Exception ex) {
		logger.info("Method execution startted with method name "+ ex.getMessage());
	}
}
