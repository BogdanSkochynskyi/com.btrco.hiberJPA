package dao;

import dao.impl.mysql.CrudDao;
import entity.Group;
import org.apache.log4j.Logger;
import org.junit.*;
import utils.HibernateUtils;
import utils.TestUtils;

import javax.persistence.EntityManager;
import java.util.List;

public class GroupDaoTest {
	private static EntityManager entityManager;
	private static CrudDao<Group> groupCrudDao;
	private Group group;

	@BeforeClass
	public static void initializeConections() {
		entityManager = HibernateUtils.getEntityManager();
		groupCrudDao = new GroupDaoImpl(entityManager);

		TestUtils.addDataIntoDB();
	}

	@AfterClass
	public static void shutdownConnection() {
		TestUtils.trancateTables();

		entityManager.clear();
		entityManager.close();

	}

	@Before
	public void initDataInDB() {
		this.group = new Group("ACP_01");
		groupCrudDao.create(this.group);
	}

	@After
	public void removeDataFromDB() {
		if (groupCrudDao.findById(this.group.getId()) != null) {
			groupCrudDao.delete(this.group);
		}
	}

	@Test
	public void testCreateGroup() {
		Group actualGroup = groupCrudDao.create(new Group("Group 5"));

		Assert.assertNotNull(actualGroup);
	}

	@Test
	public void testDeleteGroup() {

		Group actualGroup = groupCrudDao.findById(this.group.getId());
		boolean actualResult = groupCrudDao.delete(actualGroup);
		Assert.assertTrue(actualResult);
	}

	@Test
	public void testUpdateGroup() {
		Group expectedResult = groupCrudDao.findById(this.group.getId());
		expectedResult.setName("ACP_02");

		groupCrudDao.update(expectedResult);

		Group actualResult = groupCrudDao.findById(expectedResult.getId());
		Assert.assertEquals(expectedResult, actualResult);
	}

	@Test
	public void testFindById() {
		Group actualGroup = groupCrudDao.findById(this.group.getId());

		Assert.assertEquals(this.group, actualGroup);
	}

	@Test
	public void testGetAll() {
		int firstRow = 0;
		int rowAmount = 20;
		List<Group> groups = groupCrudDao.getAll(firstRow, rowAmount);
		Assert.assertTrue(groups.size() == rowAmount);
	}

}
