package com.btrco.hiberJPA.listeners;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class InitSpringContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("app-context.xml");
		servletContextEvent.getServletContext().setAttribute("spring-context", applicationContext);
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {

	}
}
