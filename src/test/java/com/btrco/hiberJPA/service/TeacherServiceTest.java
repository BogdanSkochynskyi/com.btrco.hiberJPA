package com.btrco.hiberJPA.service;

import com.btrco.hiberJPA.dao.ITeacherDao;
import com.btrco.hiberJPA.entity.Subject;
import com.btrco.hiberJPA.entity.Teacher;
import com.btrco.hiberJPA.exceptions.EntityExistsException;
import com.btrco.hiberJPA.exceptions.EntityNotFoundException;
import com.btrco.hiberJPA.exceptions.InvalidNumberException;
import com.btrco.hiberJPA.exceptions.RowsAmountException;
import com.btrco.hiberJPA.service.implementation.TeacherServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

public class TeacherServiceTest {

	private ITeacherService teacherService;

	@Mock
	private ITeacherDao teacherDao;
	private Teacher testTeacher;
	private Subject testSubject;

	@Before
	public void mockInitialization() throws RowsAmountException, EntityNotFoundException, EntityExistsException, InvalidNumberException {
		this.testSubject = new Subject("Test subject", "subject definition");
		this.testTeacher = new Teacher("Teacher name", 5, this.testSubject);
		MockitoAnnotations.initMocks(this);
		when(teacherDao.getAll(0, 20)).thenReturn(getTeachersList(20));
		when(teacherDao.create(this.testTeacher)).thenReturn(this.testTeacher);
		when(teacherDao.update(this.testTeacher)).thenReturn(true);
		when(teacherDao.getLessExperienceTeacher()).thenReturn(this.testTeacher);
		when(teacherDao.getMostExperienceTeacher()).thenReturn(this.testTeacher);
		when(teacherDao.getTeachersWithMoreThanYearsExperience(3)).thenReturn(new ArrayList<Teacher>());
		this.teacherService = new TeacherServiceImpl(this.teacherDao);
	}

	@After
	public void resetMocks() {
		reset(teacherDao);
	}

	@Test
	public void testGetListOfTeachersWithStandartRowLimit() throws RowsAmountException, EntityNotFoundException {
		Assert.assertNotNull(teacherService.getListOfTeachers());
	}

	@Test
	public void testGetListOfTeachers() throws RowsAmountException, EntityNotFoundException {
		Assert.assertTrue(teacherService.getListOfTeachers(0, 20).size() == 20);
	}

	@Test
	public void testAddTeacher() throws EntityExistsException {
		Assert.assertEquals(teacherService.addTeacher(this.testTeacher), this.testTeacher);
	}

	@Test
	public void testUpdateTeacher() throws EntityNotFoundException {
		this.testTeacher.setName("Updated name");
		Assert.assertTrue(teacherService.updateTeacher(this.testTeacher));
	}

	@Test
	public void testGetMostExperiencedTeacher() throws EntityNotFoundException {
		Assert.assertNotNull(teacherService.getMostExperiencedTeacher());
	}

	@Test
	public void testGetLessExperiencedTeacher() throws EntityNotFoundException {
		Assert.assertNotNull(teacherService.getLessExperiencedTeacher());
	}

	@Test
	public void testgetTeachersThatHaveExperienceMoreThanYears() throws InvalidNumberException, EntityNotFoundException {
		Assert.assertNotNull(teacherService.getTeachersThatHaveExperienceMoreThanYears(3));
	}

	private List<Teacher> getTeachersList(int capacity) {
		List<Teacher> teachers = new ArrayList<>();
		for (int i = 0; i < capacity; i++) {
			teachers.add(new Teacher("Teacher name", i, this.testSubject));
		}
		return teachers;
	}

}
