package com.btrco.hiberJPA.dao;

import com.btrco.hiberJPA.dao.implementation.mySQL.SubjectDaoImpl;
import com.btrco.hiberJPA.dao.implementation.mySQL.TeacherDaoImpl;
import com.btrco.hiberJPA.entity.Subject;
import com.btrco.hiberJPA.entity.Teacher;
import com.btrco.hiberJPA.exceptions.*;
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
import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:app-context.xml")
@Transactional
public class TeacherDaoTest {

	private static final int THREE_YEARS = 3;

	private static TestUtils utils;

	@Autowired
	private ITeacherDao teacherCrudDao;

	@Autowired
	private ISubjectDao subjectCrudDao;
	private Teacher teacher;


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
	public void initDataInDB() throws RowsAmountException, EntityNotFoundException, InvalidIdException, EntityExistsException {
		Subject subject = subjectCrudDao.findById(subjectCrudDao.getAll(0,1).get(0).getId());
		this.teacher = new Teacher("Teacher name", 5, subject);
		teacherCrudDao.create(this.teacher);
	}

	@After
	public void removeDataFromDB() {
		try {
			teacherCrudDao.delete(this.teacher);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCreateStudent() throws RowsAmountException, EntityNotFoundException, EntityExistsException, InvalidIdException {
		Subject group = subjectCrudDao.findById(subjectCrudDao.getAll(0,2).get(1).getId());

		Teacher actualStudent = teacherCrudDao.create(new Teacher("New teacher", 7, group));

		Assert.assertNotNull(actualStudent);
	}

	@Test
	public void testDeleteStudent() throws InvalidIdException, EntityNotFoundException {
		Teacher actualTeacher = teacherCrudDao.findById(this.teacher.getId());
		boolean actualResult = teacherCrudDao.delete(actualTeacher);

		Assert.assertTrue(actualResult);
	}

	@Test
	public void testUpdateStudent() throws InvalidIdException, EntityNotFoundException {
		Teacher expectedResult = teacherCrudDao.findById(this.teacher.getId());
		expectedResult.setName("Updated teacher name");

		teacherCrudDao.update(expectedResult);

		Teacher actualResult = teacherCrudDao.findById(expectedResult.getId());
		Assert.assertEquals(expectedResult, actualResult);
	}

	@Test
	public void testFindById() throws InvalidIdException, EntityNotFoundException {
		Teacher actualGroup = teacherCrudDao.findById(this.teacher.getId());

		Assert.assertEquals(this.teacher, actualGroup);
	}

	@Test
	public void testGetAll() throws RowsAmountException, EntityNotFoundException {
		int firstRow = 0;
		int rowAmount = 10;
		List<Teacher> students = teacherCrudDao.getAll(firstRow, rowAmount);
		Assert.assertTrue(students.size() == rowAmount);
	}

	@Test
	public void testGetMostExperienceTeacher() throws EntityNotFoundException, RowsAmountException {
		Teacher teacher = teacherCrudDao.getMostExperienceTeacher();
		List<Teacher> allTeachers = teacherCrudDao.getAll(0,10);
		assertTrue(allTeachers.stream().allMatch((teacher1 -> TestUtils.checkExperienceMinMax(teacher, teacher1, true))));
	}

	@Test
	public void testGetLessExperienceTeacher() throws EntityNotFoundException, RowsAmountException {
		Teacher teacher = teacherCrudDao.getLessExperienceTeacher();
		List<Teacher> allTeachers = teacherCrudDao.getAll(0,10);
		assertTrue(allTeachers.stream().allMatch((teacher1 -> TestUtils.checkExperienceMinMax(teacher, teacher1, false))));
	}

	@Test
	public void testGetTeachersWithMoreThanThreeYearsExperience() throws InvalidNumberException, EntityNotFoundException {

		List<Teacher> teachers = teacherCrudDao.getTeachersWithMoreThanYearsExperience(THREE_YEARS);

		assertTrue(teachers.stream().allMatch((el) -> el.getExperience() > THREE_YEARS));

	}

}
