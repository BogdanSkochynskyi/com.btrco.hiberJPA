package com.btrco.hiberJPA;

import com.btrco.hiberJPA.dao.IGroupDao;
import com.btrco.hiberJPA.ioc.DependencyInjector;
import com.btrco.hiberJPA.ioc.ServiceLocator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.btrco.hiberJPA.service.IGroupService;

public class GroupServiceTest {

	public static void main(String[] args) {

		IGroupService groupService = (IGroupService) ServiceLocator.get("groupService");

		DependencyInjector.doInjection(groupService);

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("app-context.xml");

		IGroupDao groupDao = (IGroupDao) applicationContext.getBean("groupDao");
	}

}
