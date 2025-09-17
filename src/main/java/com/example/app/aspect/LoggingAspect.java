package com.example.app.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

	public static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
	
	@Around("execution(* com.example.app.controller.BlogController.*(..)) || execution(* com.example.app.service.BlogService.*(..))")
	public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
		//get method signature
		String methodName = joinPoint.getSignature().toShortString();
		logger.info("LOGGING METHOD ASPECT -- Entering Method: {}", methodName);
		
		//Mark start time
		long startTime = System.nanoTime();
		
		try {
			//proceed with method invocation
			Object result = joinPoint.proceed();
			long endTime = System.nanoTime();
			long duration = (endTime - startTime) / 1_000_000;
			
			logger.info("METHOD EXECUTION TIME -- Exiting method : {}. Execution Time: {} ms", methodName, duration);
			return result;
		} catch (Throwable throwable) {
			logger.error("Exception in method: {}. Error: {}", methodName, throwable.getMessage());
			throw throwable;
		}
		
	}
}
