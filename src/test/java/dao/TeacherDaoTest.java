package dao;

import dao.impl.mysql.ISubjectDao;
import dao.impl.mysql.ITeacherDao;
import entity.Subject;
import entity.Teacher;
import org.junit.*;
import utils.HibernateUtils;
import utils.TestUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class TeacherDaoTest {

	private static EntityManager entityManager;
	private static final int THREE_YEARS = 3;
	private static ITeacherDao teacherCrudDao;
	private static ISubjectDao subjectCrudDao;
	private Teacher teacher;


	@BeforeClass
	public static void initializeConections() {
		entityManager = HibernateUtils.getEntityManager();

		teacherCrudDao = new TeacherDaoImpl(entityManager);
		subjectCrudDao = new SubjectDaoImpl(entityManager);

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
		Subject subject = subjectCrudDao.findById(subjectCrudDao.getAll(0,1).get(0).getId());
		this.teacher = new Teacher("Teacher name", 5, subject);
		teacherCrudDao.create(this.teacher);
	}

	@After
	public void removeDataFromDB() {
		if (teacherCrudDao.findById(this.teacher.getId()) != null) {
			teacherCrudDao.delete(this.teacher);
		}
	}

	@Test
	public void testCreateStudent() {
		Subject group = subjectCrudDao.findById(subjectCrudDao.getAll(0,2).get(1).getId());

		Teacher actualStudent = teacherCrudDao.create(new Teacher("New teacher", 7, group));

		Assert.assertNotNull(actualStudent);
	}

	@Test
	public void testDeleteStudent() {
		Teacher actualTeacher = teacherCrudDao.findById(this.teacher.getId());
		boolean actualResult = teacherCrudDao.delete(actualTeacher);

		Assert.assertTrue(actualResult);
	}

	@Test
	public void testUpdateStudent() {
		Teacher expectedResult = teacherCrudDao.findById(this.teacher.getId());
		expectedResult.setName("Updated teacher name");

		teacherCrudDao.update(expectedResult);

		Teacher actualResult = teacherCrudDao.findById(expectedResult.getId());
		Assert.assertEquals(expectedResult, actualResult);
	}

	@Test
	public void testFindById() {
		Teacher actualGroup = teacherCrudDao.findById(this.teacher.getId());

		Assert.assertEquals(this.teacher, actualGroup);
	}

	@Test
	public void testGetAll() {
		int firstRow = 0;
		int rowAmount = 20;
		List<Teacher> students = teacherCrudDao.getAll(firstRow, rowAmount);
		Assert.assertTrue(students.size() == rowAmount);
	}

	@Test
	public void testGetMostExperienceTeacher() {
		Teacher teacher = teacherCrudDao.getMostExperienceTeacher();
		List<Teacher> allTeachers = teacherCrudDao.getAll(0,10);
		System.out.println(teacher);
		assertTrue(allTeachers.stream().allMatch((teacher1 -> TestUtils.checkExperienceMinMax(teacher, teacher1, true))));
	}

	@Test
	public void testGetLessExperienceTeacher() {
		Teacher teacher = teacherCrudDao.getLessExperienceTeacher();
		List<Teacher> allTeachers = teacherCrudDao.getAll(0,10);
		System.out.println(teacher);
		assertTrue(allTeachers.stream().allMatch((teacher1 -> TestUtils.checkExperienceMinMax(teacher, teacher1, false))));
	}

	@Test
	public void testGetTeachersWithMoreThanThreeYearsExperience() {

		List<Teacher> teachers = teacherCrudDao.getTeachersWithMoreThanThreeYearsExperience(THREE_YEARS);

		assertTrue(teachers.stream().allMatch((el) -> el.getExperience() > THREE_YEARS));

	}

}
