package dao;

import dao.impl.mysql.ISubjectDao;
import entity.Subject;
import org.apache.log4j.Logger;
import utils.HibernateUtils;
import utils.Utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class SubjectDaoImpl implements ISubjectDao {

	private static final Logger LOG = Logger.getLogger(SubjectDaoImpl.class.getName());
	private static final String GET_GUMANITARIUM_SUBJECTS	= "SELECT s FROM Subject s WHERE s.name IN ('history', 'literature', 'phylosophy')";
	private static final String GET_ALL_SUBJECTS = "SELECT s FROM Subject s";
	private EntityManager entityManager;

	public SubjectDaoImpl() {
		this.entityManager = HibernateUtils.getEntityManager();
	}

	public SubjectDaoImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Subject create(Subject subject) {
		LOG.info("User try to create subject");
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();
			LOG.info("Transaction began");
			entityManager.persist(subject);
			LOG.info("Subject persisted");
			transaction.commit();
			LOG.info("Transaction commited");
		} catch (Exception e) {
			LOG.error("Operation failed");
			transaction.rollback();
			LOG.info("Transaction rolled back");
			return null;
		}
		return subject;
	}

	public boolean delete(Subject subject) {
		LOG.info("User try to delete group");
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();
			LOG.info("Transaction began");
			subject = entityManager.find(Subject.class, subject.getId());
			LOG.info("Add subject into managed context");
			entityManager.remove(subject);
			LOG.info("Group removed");
			transaction.commit();
			LOG.info("Transaction commited");
		} catch (Exception e) {
			LOG.error("Operation failed");
			transaction.rollback();
			LOG.info("Transaction rolled back");
			return false;
		}
		return true;
	}

	public boolean update(Subject subject) {
		LOG.info("User try to update subject");
		EntityTransaction transaction = entityManager.getTransaction();

		Subject subjectFromDB = entityManager.find(Subject.class, subject.getId());
		LOG.info("Subject found correct");
		Utils.copySubject(subject, subjectFromDB);
		LOG.info("Subject data updated correct");
		try {
			transaction.begin();
			LOG.info("Transaction began");
			entityManager.persist(subjectFromDB);
			LOG.info("Subject added to managed context");
			transaction.commit();
			LOG.info("Transaction commited");
		} catch (Exception e) {
			LOG.error("Operation failed");
			transaction.rollback();
			LOG.info("Transaction rolled back");
			return false;
		}
		return true;
	}

	public Subject findById(Object id) {
		LOG.info("User try to find subject by id");
		try {
			Subject subjectFromDB = entityManager.find(Subject.class, id);
			if (subjectFromDB != null)
			{
				LOG.info("Group " + subjectFromDB + " found correct");
			} else {
				LOG.info("Group with id " + id + " not found");
			}
			return subjectFromDB;
		} catch (Exception e) {
			LOG.error("Operation failed");
			return null;
		}
	}

	@Override
	public List<Subject> getAll(int firstRow, int rowsAmount) {
		LOG.info("User try to find subjects from " + firstRow + " row and with row amount " + rowsAmount);
		return entityManager.createQuery(GET_ALL_SUBJECTS)
				.setFirstResult(firstRow)
				.setMaxResults(rowsAmount)
				.getResultList();
	}

	@Override
	public List<Subject> getGumanitariumSubjects() {
		LOG.info("User try to find only gumanitarium subjects");
		List<Subject> gumanitariumSubjects = entityManager.createQuery(GET_GUMANITARIUM_SUBJECTS).getResultList();
		if (gumanitariumSubjects != null) {
			LOG.info("Gumanitarium subjects are found correctly");
			return gumanitariumSubjects;
		}
		LOG.info("Gumanitarium subjects are not found");
		return null;

	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
