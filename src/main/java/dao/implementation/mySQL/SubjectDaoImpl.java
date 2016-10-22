package dao.implementation.mySQL;

import dao.ISubjectDao;
import entity.Group;
import entity.Student;
import entity.Subject;
import exceptions.EntityExistsException;
import exceptions.EntityNotFoundException;
import exceptions.InvalidIdException;
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

	public Subject create(Subject subject) throws EntityExistsException {
		LOG.trace("User try to create subject");

		if (entityManager.find(Subject.class, subject.getId()) != null) {
			throw new EntityExistsException(subject.toString());
		}

		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();
			LOG.trace("Transaction began");
			entityManager.persist(subject);
			LOG.trace("Subject persisted");
			transaction.commit();
			LOG.trace("Transaction commited");
		} catch (Exception e) {
			LOG.error("Operation failed");
			transaction.rollback();
			LOG.trace("Transaction rolled back");
			return null;
		}
		return subject;
	}

	public boolean delete(Subject subject) throws EntityNotFoundException {
		LOG.trace("User try to delete group");

		if (entityManager.find(Subject.class, subject.getId()) == null) {
			throw new EntityNotFoundException(subject.toString());
		}

		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();
			LOG.trace("Transaction began");
			subject = entityManager.find(Subject.class, subject.getId());
			LOG.trace("Add subject into managed context");
			entityManager.remove(subject);
			LOG.trace("Group removed");
			transaction.commit();
			LOG.trace("Transaction commited");
		} catch (Exception e) {
			LOG.error("Operation failed");
			transaction.rollback();
			LOG.trace("Transaction rolled back");
			return false;
		}
		return true;
	}

	public boolean update(Subject subject) throws EntityNotFoundException {
		LOG.trace("User try to update subject");
		EntityTransaction transaction = entityManager.getTransaction();

		Subject subjectFromDB = entityManager.find(Subject.class, subject.getId());

		if (subjectFromDB == null) {
			throw new EntityNotFoundException(subject.toString());
		}

		LOG.trace("Subject found correct");
		Utils.copySubject(subject, subjectFromDB);
		LOG.trace("Subject data updated correct");
		try {
			transaction.begin();
			LOG.trace("Transaction began");
			entityManager.persist(subjectFromDB);
			LOG.trace("Subject added to managed context");
			transaction.commit();
			LOG.trace("Transaction commited");
		} catch (Exception e) {
			LOG.error("Operation failed");
			transaction.rollback();
			LOG.trace("Transaction rolled back");
			return false;
		}
		return true;
	}

	public Subject findById(Object id) throws InvalidIdException, EntityNotFoundException {
		LOG.trace("User try to find subject by id");

		if (!(id instanceof Integer) || ((Integer)id < 0)) {
			throw new InvalidIdException(id.toString());
		}

		Subject subjectFromDB = entityManager.find(Subject.class, id);
		if (subjectFromDB == null) {
			throw new EntityNotFoundException((Integer)id);
		}

		return subjectFromDB;
	}

	@Override
	public List<Subject> getAll(int firstRow, int rowsAmount) {
		LOG.trace("User try to find subjects from " + firstRow + " row and with row amount " + rowsAmount);
		return entityManager.createQuery(GET_ALL_SUBJECTS)
				.setFirstResult(firstRow)
				.setMaxResults(rowsAmount)
				.getResultList();
	}

	@Override
	public List<Subject> getGumanitariumSubjects() throws EntityNotFoundException {
		LOG.trace("User try to find only gumanitarium subjects");
		List<Subject> gumanitariumSubjects = entityManager.createQuery(GET_GUMANITARIUM_SUBJECTS).getResultList();
		if (gumanitariumSubjects != null) {
			LOG.trace("Gumanitarium subjects are found correctly");
			return gumanitariumSubjects;
		} else {
			throw new EntityNotFoundException("Gumanitarium subjects ");
		}


	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
