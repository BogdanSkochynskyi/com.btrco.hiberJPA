package service;

import dao.ITeacherDao;
import entity.Subject;
import entity.Teacher;
import exceptions.EntityExistsException;
import exceptions.EntityNotFoundException;
import exceptions.InvalidNumberException;
import exceptions.RowsAmountException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.implementation.TeacherServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class TeacherServiceTest {

	private static ITeacherService service;

	@Mock
	private ITeacherDao teacherDao;
	private Teacher testTeacher;
	private Subject testSubject;

	@Before
	public void mockInitialization() throws RowsAmountException, EntityNotFoundException, EntityExistsException, InvalidNumberException {
		this.testSubject = new Subject("Test subject", "subject definition");
		this.testTeacher = new Teacher("Teacher name", 5, this.testSubject);
		MockitoAnnotations.initMocks(this);
		service = new TeacherServiceImpl(this.teacherDao);
		when(teacherDao.getAll(0, 20)).thenReturn(getTeachersList(20));
		when(teacherDao.create(this.testTeacher)).thenReturn(this.testTeacher);
		when(teacherDao.update(this.testTeacher)).thenReturn(true);
		when(teacherDao.getLessExperienceTeacher()).thenReturn(this.testTeacher);
		when(teacherDao.getMostExperienceTeacher()).thenReturn(this.testTeacher);
		when(teacherDao.getTeachersWithMoreThanYearsExperience(3)).thenReturn(new ArrayList<Teacher>());
	}

	@Test
	public void testGetListOfTeachersWithStandartRowLimit() throws RowsAmountException, EntityNotFoundException {
		Assert.assertNotNull(service.getListOfTeachers());
	}

	@Test
	public void testGetListOfTeachers() throws RowsAmountException, EntityNotFoundException {
		Assert.assertTrue(service.getListOfTeachers(0, 20).size() == 20);
	}

	@Test
	public void testAddTeacher() throws EntityExistsException {
		Assert.assertEquals(service.addTeacher(this.testTeacher), this.testTeacher);
	}

	@Test
	public void testUpdateTeacher() throws EntityNotFoundException {
		this.testTeacher.setName("Updated name");
		Assert.assertTrue(service.updateTeacher(this.testTeacher));
	}

	@Test
	public void testGetMostExperiencedTeacher() throws EntityNotFoundException {
		Assert.assertNotNull(service.getMostExperiencedTeacher());
	}

	@Test
	public void testGetLessExperiencedTeacher() throws EntityNotFoundException {
		Assert.assertNotNull(service.getLessExperiencedTeacher());
	}

	@Test
	public void testgetTeachersThatHaveExperienceMoreThanYears() throws InvalidNumberException, EntityNotFoundException {
		Assert.assertNotNull(service.getTeachersThatHaveExperienceMoreThanYears(3));
	}

	private List<Teacher> getTeachersList(int capacity) {
		List<Teacher> teachers = new ArrayList<>();
		for (int i = 0; i < capacity; i++) {
			teachers.add(new Teacher("Teacher name", i, this.testSubject));
		}
		return teachers;
	}

}
