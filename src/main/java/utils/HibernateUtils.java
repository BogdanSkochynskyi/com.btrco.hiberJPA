package utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtils {

	private static final String PERSISTENT_UNIT_NAME = "university";

	private static final EntityManagerFactory entityManagerFactory;

	static {
			entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENT_UNIT_NAME);
	}

	public static EntityManager getEntityManager() {
		return entityManagerFactory.createEntityManager();
	}

}
