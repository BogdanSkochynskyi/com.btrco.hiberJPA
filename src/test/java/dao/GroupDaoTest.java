package dao;

import dao.implementation.mySQL.GroupDaoImpl;
import entity.Group;
import exceptions.EntityExistsException;
import exceptions.EntityNotFoundException;
import exceptions.InvalidIdException;
import exceptions.RowsAmountException;
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
	public void initDataInDB() throws EntityExistsException {
		this.group = new Group("ACP_01");
		this.group = groupCrudDao.create(this.group);
	}

	@After
	public void removeDataFromDB()  {
		try {
			groupCrudDao.delete(this.group);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCreateGroup() throws EntityExistsException {
		Group actualGroup = groupCrudDao.create(new Group("Group 5"));

		Assert.assertNotNull(actualGroup);
	}

	@Test
	public void testDeleteGroup() throws InvalidIdException, EntityNotFoundException {

		Group actualGroup = groupCrudDao.findById(this.group.getId());
		boolean actualResult = groupCrudDao.delete(actualGroup);
		Assert.assertTrue(actualResult);
	}

	@Test
	public void testUpdateGroup() throws InvalidIdException, EntityNotFoundException {
		Group expectedResult = groupCrudDao.findById(this.group.getId());
		expectedResult.setName("ACP_02");

		groupCrudDao.update(expectedResult);

		Group actualResult = groupCrudDao.findById(expectedResult.getId());
		Assert.assertEquals(expectedResult, actualResult);
	}

	@Test
	public void testFindById() throws InvalidIdException, EntityNotFoundException {
		Group actualGroup = groupCrudDao.findById(this.group.getId());

		Assert.assertEquals(this.group, actualGroup);
	}

	@Test
	public void testGetAll() throws RowsAmountException, EntityNotFoundException {
		int firstRow = 0;
		int rowAmount = 20;
		List<Group> groups = groupCrudDao.getAll(firstRow, rowAmount);
		Assert.assertTrue(groups.size() == rowAmount);
	}

}
