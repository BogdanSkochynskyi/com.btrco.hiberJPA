package dao;

import dao.service.CrudDao;
import entity.Group;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.HibernateUtils;

import javax.persistence.EntityManager;

public class GroupDaoImplTest {

	private static EntityManager entityManager;
	private CrudDao<Group> groupCrudDao = new GroupDaoImpl();

	@BeforeClass
	public static void initializeConections() {
		entityManager = HibernateUtils.getEntityManager();
	}

	@AfterClass
	public static void shutdownConnection() {
		entityManager.clear();
		entityManager.close();
	}

	@Test
	public void testCreateGroup() {
		Group expectedGroup = new Group("ACP14");

		Group actualGroup = groupCrudDao.create(expectedGroup);

		Assert.assertNotNull(actualGroup);
	}

	@Test
	public void testDeleteGroup() {
		Group expectedGroup = new Group("ACP15");

		Group actualGroup = groupCrudDao.findById(1);
		boolean expectedResult = true;
		boolean actualResult = groupCrudDao.delete(actualGroup);

		Assert.assertEquals(expectedResult, actualResult);
	}

	@Test
	public void testUpdateGroup() {
		Group initialGroup = new Group("ACP16");

		Group actualGroup = groupCrudDao.create(initialGroup);
		actualGroup.setName("ACP16_1");

		groupCrudDao.update(actualGroup);

		Group updatedGroup = groupCrudDao.findById(actualGroup.getId());
		System.out.println(updatedGroup);
	}

	@Test
	public void testFindById() {
		Group initialGroup = new Group("ACP16");

		Group expectedGroup = groupCrudDao.create(initialGroup);

		Group actualGroup = groupCrudDao.findById(expectedGroup.getId());

		Assert.assertEquals(expectedGroup, actualGroup);
	}

}
