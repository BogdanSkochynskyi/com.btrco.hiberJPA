package dao;

import dao.service.ISubjectDao;
import entity.Subject;
import utils.HibernateUtils;
import utils.Utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class SubjectDaoImpl implements ISubjectDao {

	private static final String GET_GUMANITARIUM_SUBJECTS	= "SELECT s FROM Subject s WHERE s.name IN ('history', 'literature', 'phylosophy')";
	private static EntityManager entityManager;
	private static final String GET_ALL_SUBJECTS = "SELECT s FROM Subject s";

	static {
		entityManager = HibernateUtils.getEntityManager();
	}

	public SubjectDaoImpl() {
	}

	public Subject create(Subject subject) {
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();
			entityManager.persist(subject);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			return null;
		} finally {
			entityManager.close();
		}
		return subject;
	}

	public boolean delete(Subject subject) {
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();
			entityManager.remove(subject);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			return false;
		} finally {
			entityManager.close();
		}
		return true;
	}

	public boolean update(Subject subject) {
		EntityTransaction transaction = entityManager.getTransaction();

		Subject subjectFromDB = entityManager.find(Subject.class, subject.getId());
		Utils.copySubject(subject, subjectFromDB);

		try {
			transaction.begin();
			entityManager.persist(subject);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			return false;
		} finally {
			entityManager.close();
		}
		return true;
	}

	public Subject findById(Object id) {
		try {
			Subject subjectFromDB = entityManager.find(Subject.class, id);
			return subjectFromDB;
		} finally {
			entityManager.close();
		}
	}

	@Override
	public List<Subject> getAll() {
		return entityManager.createQuery(GET_ALL_SUBJECTS).setMaxResults(100).getResultList();
	}

	@Override
	public List<Subject> getGumanitariumSubjects() {

		List<Subject> gumanitariumSubjects = entityManager.createQuery(GET_GUMANITARIUM_SUBJECTS).getResultList();
		if (gumanitariumSubjects != null) {
			return gumanitariumSubjects;
		}

		return null;

	}

	public static void setEntityManager(EntityManager entityManager) {
		SubjectDaoImpl.entityManager = entityManager;
	}
}
