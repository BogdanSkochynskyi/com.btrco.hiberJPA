package dao;

import dao.service.ISubjectDao;
import entity.Subject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.HibernateUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class SubjectDaoTest {

	private static EntityManager entityManager;
	private final ISubjectDao subjecctCrudDao = new SubjectDaoImpl();

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
	public void testGetGumanitariumSubjects() {
		List<Subject> gumanitariumSubjects = subjecctCrudDao.getGumanitariumSubjects();

		assertTrue(gumanitariumSubjects.stream().allMatch(this::checkIsItGumanitariumSubject));
	}

	boolean checkIsItGumanitariumSubject(Subject subject){
		return subject.getName().equals("history") ||
				subject.getName().equals("literature") ||
				subject.getName().equals("phylosophy");

	}

}
