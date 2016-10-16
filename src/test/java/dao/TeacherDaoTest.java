package dao;

import dao.service.ITeacherDao;
import entity.Teacher;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.HibernateUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class TeacherDaoTest {

	private static EntityManager entityManager;
	private static final int THREE_YEARS = 3;
	private final ITeacherDao teacherCrudDao = new TeacherDaoImpl();


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
	public void testGetMostExperienceTeacher() {
		Teacher teacher = teacherCrudDao.getMostExperienceTeacher();
		List<Teacher> allTeachers = teacherCrudDao.getAll();
		System.out.println(teacher);
		assertTrue(allTeachers.stream().allMatch((teacher1 -> checkExperienceMinMax(teacher, teacher1, true))));
	}

	@Test
	public void testGetLessExperienceTeacher() {
		Teacher teacher = teacherCrudDao.getLessExperienceTeacher();
		List<Teacher> allTeachers = teacherCrudDao.getAll();
		System.out.println(teacher);
		assertTrue(allTeachers.stream().allMatch((teacher1 -> checkExperienceMinMax(teacher, teacher1, false))));
	}

	@Test
	public void testGetTeachersWithMoreThanThreeYearsExperience() {

		List<Teacher> teachers = teacherCrudDao.getTeachersWithMoreThanThreeYearsExperience();

		assertTrue(teachers.stream().allMatch((el) -> el.getExperience() > THREE_YEARS));

	}

	boolean checkExperienceMinMax(Teacher checkedTeacher, Teacher teacher, boolean isMax) {
		return isMax ? checkedTeacher.getExperience() >= teacher.getExperience() : checkedTeacher.getExperience() <= teacher.getExperience();
	}

}
