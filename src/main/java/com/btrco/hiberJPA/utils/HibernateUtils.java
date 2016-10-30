package com.btrco.hiberJPA.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class HibernateUtils {

	private static final String PERSISTENT_UNIT_NAME = "university";

//	private static final EntityManagerFactory entityManagerFactory;

//	static {
//		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/app-context.xml");
//		context.refresh();
//			entityManagerFactory = context.getBean(EntityManagerFactory.class); /*Persistence.createEntityManagerFactory(PERSISTENT_UNIT_NAME);*/
//
//	}

	public static EntityManager getEntityManager() {
		return ApplicationContextFactory.getApplicationContext().getBean(EntityManagerFactory.class).createEntityManager();
	}

}
