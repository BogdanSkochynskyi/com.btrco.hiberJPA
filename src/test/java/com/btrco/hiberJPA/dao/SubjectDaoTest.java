package com.btrco.hiberJPA.dao;

import com.btrco.hiberJPA.dao.implementation.mySQL.SubjectDaoImpl;
import com.btrco.hiberJPA.entity.Subject;
import com.btrco.hiberJPA.exceptions.EntityExistsException;
import com.btrco.hiberJPA.exceptions.EntityNotFoundException;
import com.btrco.hiberJPA.exceptions.InvalidIdException;
import com.btrco.hiberJPA.exceptions.RowsAmountException;
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
public class SubjectDaoTest {

	private static final String TEST_SUBJECT_NAME = "Subject name";
	private static final String TEST_SUBJECT_DESCRIPTION = "Subject description";

	private static TestUtils utils;

	@Autowired
	private static ISubjectDao subjectCrudDao;
	private Subject subject;

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
	public void initDataInDB() throws EntityExistsException {
		this.subject = new Subject(TEST_SUBJECT_NAME, TEST_SUBJECT_DESCRIPTION);
		subjectCrudDao.create(this.subject);
	}

	@After
	public void removeDataFromDB() {
		try {
			subjectCrudDao.delete(this.subject);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCreateSubject() throws EntityExistsException {
		Subject actualSubject = subjectCrudDao.create(new Subject("New Subject", "New subject description"));

		Assert.assertNotNull(actualSubject);
	}

	@Test
	public void testDeleteSubject() throws InvalidIdException, EntityNotFoundException {
		Subject actualSubject = subjectCrudDao.findById(this.subject.getId());
		boolean actualResult = subjectCrudDao.delete(actualSubject);

		Assert.assertTrue(actualResult);
	}

	@Test
	public void testUpdateSubject() throws InvalidIdException, EntityNotFoundException {
		Subject expectedResult = subjectCrudDao.findById(this.subject.getId());
		expectedResult.setName("Updated subject");

		subjectCrudDao.update(expectedResult);

		Subject actualResult = subjectCrudDao.findById(expectedResult.getId());
		Assert.assertEquals(expectedResult, actualResult);
	}

	@Test
	public void testFindById() throws InvalidIdException, EntityNotFoundException {
		Subject actualGroup = subjectCrudDao.findById(this.subject.getId());

		Assert.assertEquals(this.subject, actualGroup);
	}

	@Test
	public void testGetAll() throws RowsAmountException, EntityNotFoundException {
		int firstRow = 0;
		int rowAmount = 20;
		List<Subject> subjects = subjectCrudDao.getAll(firstRow, rowAmount);
		Assert.assertTrue(subjects.size() == rowAmount);
	}

	@Test
	public void testGetGumanitariumSubjects() throws EntityNotFoundException {
		List<Subject> gumanitariumSubjects = subjectCrudDao.getGumanitariumSubjects();

		assertTrue(gumanitariumSubjects.stream().allMatch(TestUtils::checkIsItGumanitariumSubject));
	}


}
