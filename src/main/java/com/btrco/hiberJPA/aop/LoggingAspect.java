package com.btrco.hiberJPA.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

@Aspect
public class LoggingAspect {

	private static final Logger LOG = Logger.getLogger("HiberJPA project LOG");
	private String methodName;
	private String arguments;
	private String className;

	public LoggingAspect() {
		LOG.error("LOGGING ASPECT CREATED");
	}

	@Before("execution(public * com.btrco.hiberJPA.dao.implementation.mySQL..*(..))")
	public void loggingBeforeMethodInvocation(JoinPoint joinPoint) {
		setData(joinPoint);
		LOG.trace("The method " + this.methodName
				+ " with arguments " + arguments
				+ " was called in " + this.className);
	}

	@After("execution(public * com.btrco.hiberJPA.dao.implementation.mySQL..*(..))")
	public void loggingAfterMethodInvoction(JoinPoint joinPoint) {
		setData(joinPoint);
		LOG.trace("The method " + this.methodName
				+ " with arguments " + arguments
				+ " in " + this.className + " was finished");
	}

	@AfterThrowing(value = "execution(public * com.btrco.hiberJPA.dao.implementation.mySQL..*(..))", throwing = "e")
	public void loggingErrorThrowing(JoinPoint joinPoint, Exception e) {
		setData(joinPoint);
		LOG.error("The method " + this.methodName
				+ " with arguments " + arguments
				+ " in " + this.className
				+ " was throwing an exception " + e.getMessage(), e);
	}

	@AfterReturning(value = "execution(public * com.btrco.hiberJPA.dao.implementation.mySQL..*(..))", returning = "result")
	public void loggingReturnedValues(JoinPoint joinPoint, Object result) {
		setData(joinPoint);
		LOG.trace("The method " + this.methodName
				+ " with arguments " + arguments
				+ " in " + this.className
				+ " was returned an Object: " + result);
	}

	private void setData(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		this.methodName = signature.getName();
		this.arguments = Arrays.toString(joinPoint.getArgs());
		this.className = joinPoint.getSourceLocation().getWithinType().getName();
	}


}
