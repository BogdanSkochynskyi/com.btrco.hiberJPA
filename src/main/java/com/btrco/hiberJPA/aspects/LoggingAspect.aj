package com.btrco.hiberJPA.aspects;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component("loggingAspect")
public class LoggingAspect {

	private static final Logger LOG = Logger.getLogger(LoggingAspect.class.getName());
	public LoggingAspect() {
		LOG.error("LOGGING ASPECT CREATED");
	}


	@Before("execution(public * com.btrco.hiberJPA.dao.implementation.mySQL..*(..))")
	public void loggingBeforeMethodInvocation(JoinPoint joinPoint) {
		System.out.println("jksfhlkrhfwkfhehfle");
		LOG.error("The method " + joinPoint.getSignature().getName() +
				" in " + joinPoint.getSourceLocation().getFileName() + " class is called");
	}

}
