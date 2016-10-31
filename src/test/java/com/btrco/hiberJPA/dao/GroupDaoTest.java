package com.btrco.hiberJPA.dao;

import com.btrco.hiberJPA.entity.Group;
import com.btrco.hiberJPA.exceptions.EntityExistsException;
import com.btrco.hiberJPA.exceptions.EntityNotFoundException;
import com.btrco.hiberJPA.exceptions.InvalidIdException;
import com.btrco.hiberJPA.exceptions.RowsAmountException;
import com.btrco.hiberJPA.utils.ApplicationContextFactory;
import org.junit.*;
import com.btrco.hiberJPA.utils.HibernateUtils;
import com.btrco.hiberJPA.utils.TestUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

//TODO: rewrite tests without spring??? REWRITE tests with using hamcrest library

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:app-context.xml")
@Transactional
public class GroupDaoTest {

	private static TestUtils utils;

	@Autowired
	private CrudDao<Group> groupCrudDao;
	private Group group;

	@BeforeClass
	public static void initializeConections() {
		ApplicationContext context = ApplicationContextFactory.getApplicationContext();
		utils = context.getBean("testUtils", TestUtils.class);

		utils.addDataIntoDB();
	}

	@AfterClass
	public static void shutdownConnection() {
		utils.trancateTables();
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
