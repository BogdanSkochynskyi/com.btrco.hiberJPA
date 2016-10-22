package ioc;

import dao.implementation.mySQL.GroupDaoImpl;
import dao.IGroupDao;
import service.implementation.GroupServiceImpl;

import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {

	private static final Map<String, Object> context  = new HashMap();

	//while class loading
	static {
		context.put("entityManagerFactory", Persistence.createEntityManagerFactory("university"));
		context.put("groupDao", new GroupDaoImpl());
		context.put("groupService", new GroupServiceImpl((IGroupDao) context.get("groupDao")));
	}

	public static Object get(String beanName) {
		return context.get(beanName);
	}
}
