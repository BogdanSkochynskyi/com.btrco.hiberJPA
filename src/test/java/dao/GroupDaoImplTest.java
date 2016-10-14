package dao;

import entity.Group;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.Persistence;

public class GroupDaoImplTest {

	private final CrudDao<Group> groupCrudDao = new GroupDaoImpl(Persistence.createEntityManagerFactory("university"));

	@Test
	public void testCreateGroup() {
		Group expectedGroup = new Group("ACP14");

		Group actualGroup = groupCrudDao.create(expectedGroup);

		Assert.assertNotNull(actualGroup);
	}

	@Test
	public void testDeleteGroup() {
		Group expectedGroup = new Group("ACP15");

		Group actualGroup = groupCrudDao.create(expectedGroup);
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
