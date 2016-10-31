package com.btrco.hiberJPA.service;

import com.btrco.hiberJPA.dao.ISubjectDao;
import com.btrco.hiberJPA.entity.Subject;
import com.btrco.hiberJPA.exceptions.EntityExistsException;
import com.btrco.hiberJPA.exceptions.EntityNotFoundException;
import com.btrco.hiberJPA.exceptions.RowsAmountException;
import com.btrco.hiberJPA.service.implementation.SubjectServiceImpl;
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

import static org.mockito.Mockito.*;

public class SubjectServiceTest {

	private ISubjectService subjectService;

	@Mock
	private ISubjectDao subjectDao;
	private Subject testSubject;

	@Before
	public void mockInitialization() throws RowsAmountException, EntityNotFoundException, EntityExistsException {
		this.testSubject = new Subject("Test subject", "subject definition");
		MockitoAnnotations.initMocks(this);
		when(this.subjectDao.getAll(0, 20)).thenReturn(getSubjectsList(20));
		when(this.subjectDao.create(this.testSubject)).thenReturn(this.testSubject);
		when(this.subjectDao.update(this.testSubject)).thenReturn(true);
		when(this.subjectDao.getGumanitariumSubjects()).thenReturn(new ArrayList<Subject>());
		subjectService = new SubjectServiceImpl(this.subjectDao);
	}

	@After
	public void resetMocks() {
		reset(subjectDao);
	}

	@Test
	public void testGetListOfSubjectWithStandartRowLimit() throws RowsAmountException, EntityNotFoundException {
		Assert.assertNotNull(subjectService.getListOfSubjects());
	}

	@Test
	public void testGetListOfSubject() throws RowsAmountException, EntityNotFoundException {
		Assert.assertTrue(subjectService.getListOfSubjects(0, 20).size() == 20);
	}

	@Test
	public void testAddSubject() throws EntityExistsException {
		Assert.assertEquals(subjectService.addSubject(this.testSubject), this.testSubject);
	}

	@Test
	public void testUpdateSubject() throws EntityNotFoundException {
		this.testSubject.setName("Updated name");
		Assert.assertTrue(subjectService.updateSubject(this.testSubject));
	}

	@Test
	public void testGetGumanitariumSubjects() throws EntityNotFoundException {
		Assert.assertNotNull(subjectService.getGumanitariumSubjects());
	}

	private List<Subject> getSubjectsList(int capacity) {
		List<Subject> subjects = new ArrayList<>();
		for (int i = 0; i < capacity; i++) {
			subjects.add(new Subject("Subject name", "Description " + i));
		}
		return subjects;
	}

}
