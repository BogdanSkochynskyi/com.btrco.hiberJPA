package dao;

import dao.service.IGroupDao;
import dao.service.IStudentDao;
import dao.service.ServiceFactory;
import entity.Group;
import entity.Student;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.HibernateUtils;

import javax.persistence.EntityManager;
import java.util.List;

public class StudentDaoTest {
	private static EntityManager entityManager;
	private static IStudentDao studentCrudDao = ServiceFactory.getStudentServiceInstance();
	private static IGroupDao groupCrudDao = ServiceFactory.getGroupServiceInstance();

	@BeforeClass
	public static void initializeConections() {
		entityManager = HibernateUtils.getEntityManager();
		StudentDaoImpl.setEntityManager(entityManager);
		GroupDaoImpl.setEntityManager(entityManager);
	}

	@AfterClass
	public static void shutdownConnection() {
		entityManager.clear();
		entityManager.close();
	}

	@Test
	public void testGetListOfStudentsInGroup() {
		Group group = groupCrudDao.findById(29);

		List<Student> studentList = studentCrudDao.getListOfStudentsInGroup(group);
		Assert.assertNotNull(studentList);

	}

	@Test
	public void testCreateStudent() {
		Group group = groupCrudDao.findById(3);
		Student expectedStudent = new Student("Bogdan", group);

		Student actualStudent = studentCrudDao.create(expectedStudent);

		Assert.assertNotNull(actualStudent);
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

	//Если вытянуть группу из таблицы, а потом вызвать getStudents, то студенты вытянутся из БД. До этого их в группе не было.
	//Для этого хибернейт юзает  PersistentBag класс, который наследуетсчяя от List, поэтому нельзя использовать в таких случаях например ArrayList
	//Pattern proxy or adapter
}
