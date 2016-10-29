package com.btrco.hiberJPA.dao;

import com.btrco.hiberJPA.dao.implementation.mySQL.GroupDaoImpl;
import com.btrco.hiberJPA.dao.implementation.mySQL.StudentDaoImpl;
import com.btrco.hiberJPA.entity.Group;
import com.btrco.hiberJPA.entity.Student;
import com.btrco.hiberJPA.exceptions.EntityExistsException;
import com.btrco.hiberJPA.exceptions.EntityNotFoundException;
import com.btrco.hiberJPA.exceptions.InvalidIdException;
import com.btrco.hiberJPA.exceptions.RowsAmountException;
import org.junit.*;
import com.btrco.hiberJPA.utils.HibernateUtils;
import com.btrco.hiberJPA.utils.TestUtils;

import javax.persistence.EntityManager;
import java.util.List;

public class StudentDaoTest {
	private static EntityManager entityManager;
	private static IStudentDao studentCrudDao;
	private static IGroupDao groupCrudDao;
	private Student student;

	@BeforeClass
	public static void initializeConections() {
		entityManager = HibernateUtils.getEntityManager();

		studentCrudDao = new StudentDaoImpl(entityManager);
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
	public void initDataInDB() throws RowsAmountException, EntityNotFoundException, InvalidIdException, EntityExistsException {
		Group group = groupCrudDao.findById(groupCrudDao.getAll(0,1).get(0).getId());
		this.student = new Student("Student name", group);
		studentCrudDao.create(this.student);
	}

	@After
	public void removeDataFromDB() {
		try {
			studentCrudDao.delete(this.student);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCreateStudent() throws RowsAmountException, EntityNotFoundException, InvalidIdException, EntityExistsException {
		Group group = groupCrudDao.findById(groupCrudDao.getAll(0,2).get(1).getId());

		Student actualStudent = studentCrudDao.create(new Student("Bogdan", group));

		Assert.assertNotNull(actualStudent);
	}

	@Test
	public void testDeleteStudent() throws InvalidIdException, EntityNotFoundException {
		Student actualStudent = studentCrudDao.findById(this.student.getId());
		boolean actualResult = studentCrudDao.delete(actualStudent);

		Assert.assertTrue(actualResult);
	}

	@Test
	public void testUpdateStudent() throws InvalidIdException, EntityNotFoundException {
		Student expectedResult = studentCrudDao.findById(this.student.getId());
		expectedResult.setName("Dima");

		studentCrudDao.update(expectedResult);

		Student actualResult = studentCrudDao.findById(expectedResult.getId());
		Assert.assertEquals(expectedResult, actualResult);
	}

	@Test
	public void testFindById() throws InvalidIdException, EntityNotFoundException {
		Student actualGroup = studentCrudDao.findById(this.student.getId());

		Assert.assertEquals(this.student, actualGroup);
	}

	@Test
	public void testGetAll() throws RowsAmountException, EntityNotFoundException {
		int firstRow = 0;
		int rowAmount = 20;
		List<Student> students = studentCrudDao.getAll(firstRow, rowAmount);
		Assert.assertTrue(students.size() == rowAmount);
	}

	@Test
	public void testGetListOfStudentsInGroup() throws RowsAmountException, EntityNotFoundException, InvalidIdException {
		Group group = groupCrudDao.findById(groupCrudDao.getAll(0,1).get(0).getId());

		List<Student> studentList = studentCrudDao.getListOfStudentsInGroup(group);
		Assert.assertNotNull(studentList);

	}
	//Если вытянуть группу из таблицы, а потом вызвать getStudents, то студенты вытянутся из БД. До этого их в группе не было.
	//Для этого хибернейт юзает  PersistentBag класс, который наследуетсчяя от List, поэтому нельзя использовать в таких случаях например ArrayList
	//Pattern proxy or adapter
}
