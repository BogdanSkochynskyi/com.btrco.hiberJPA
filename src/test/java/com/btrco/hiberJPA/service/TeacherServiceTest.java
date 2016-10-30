package com.btrco.hiberJPA.service;

import com.btrco.hiberJPA.dao.ITeacherDao;
import com.btrco.hiberJPA.entity.Subject;
import com.btrco.hiberJPA.entity.Teacher;
import com.btrco.hiberJPA.exceptions.EntityExistsException;
import com.btrco.hiberJPA.exceptions.EntityNotFoundException;
import com.btrco.hiberJPA.exceptions.InvalidNumberException;
import com.btrco.hiberJPA.exceptions.RowsAmountException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.btrco.hiberJPA.service.implementation.TeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class TeacherServiceTest {

	@Autowired
	private static ITeacherService service;

	@Mock
	@Autowired
	private ITeacherDao teacherDao;
	private Teacher testTeacher;
	private Subject testSubject;

	@Before
	public void mockInitialization() throws RowsAmountException, EntityNotFoundException, EntityExistsException, InvalidNumberException {
		this.testSubject = new Subject("Test subject", "subject definition");
		this.testTeacher = new Teacher("Teacher name", 5, this.testSubject);
		MockitoAnnotations.initMocks(this);
//		service = new TeacherServiceImpl();
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
