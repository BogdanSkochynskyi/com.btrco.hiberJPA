package service;

import dao.ISubjectDao;
import entity.Subject;
import exceptions.EntityExistsException;
import exceptions.EntityNotFoundException;
import exceptions.RowsAmountException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.implementation.SubjectServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class SubjectServiceTest {

	private static ISubjectService service;

	@Mock
	private ISubjectDao subjectDao;
	private Subject testSubject;

	@Before
	public void mockInitialization() throws RowsAmountException, EntityNotFoundException, EntityExistsException {
		this.testSubject = new Subject("Test subject", "subject definition");
		MockitoAnnotations.initMocks(this);
		service = new SubjectServiceImpl(this.subjectDao);
		when(this.subjectDao.getAll(0, 20)).thenReturn(getSubjectsList(20));
		when(this.subjectDao.create(this.testSubject)).thenReturn(this.testSubject);
		when(this.subjectDao.update(this.testSubject)).thenReturn(true);
		when(this.subjectDao.getGumanitariumSubjects()).thenReturn(new ArrayList<Subject>());
	}

	@Test
	public void testGetListOfSubjectWithStandartRowLimit() throws RowsAmountException, EntityNotFoundException {
		Assert.assertNotNull(service.getListOfSubjects());
	}

	@Test
	public void testGetListOfSubject() throws RowsAmountException, EntityNotFoundException {
		Assert.assertTrue(service.getListOfSubjects(0, 20).size() == 20);
	}

	@Test
	public void testAddSubject() throws EntityExistsException {
		Assert.assertEquals(service.addSubject(this.testSubject), this.testSubject);
	}

	@Test
	public void testUpdateSubject() throws EntityNotFoundException {
		this.testSubject.setName("Updated name");
		Assert.assertTrue(service.updateSubject(this.testSubject));
	}

	@Test
	public void testGetGumanitariumSubjects() throws EntityNotFoundException {
		Assert.assertNotNull(service.getGumanitariumSubjects());
	}

	private List<Subject> getSubjectsList(int capacity) {
		List<Subject> subjects = new ArrayList<>();
		for (int i = 0; i < capacity; i++) {
			subjects.add(new Subject("Subject name", "Description " + i));
		}
		return subjects;
	}

}
