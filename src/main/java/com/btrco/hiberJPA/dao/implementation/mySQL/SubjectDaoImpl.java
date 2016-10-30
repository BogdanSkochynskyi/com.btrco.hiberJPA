package com.btrco.hiberJPA.dao.implementation.mySQL;

import com.btrco.hiberJPA.dao.ISubjectDao;
import com.btrco.hiberJPA.entity.Subject;
import com.btrco.hiberJPA.exceptions.EntityExistsException;
import com.btrco.hiberJPA.exceptions.EntityNotFoundException;
import com.btrco.hiberJPA.exceptions.InvalidIdException;
import org.apache.log4j.Logger;
import com.btrco.hiberJPA.utils.HibernateUtils;
import com.btrco.hiberJPA.utils.Utils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component("subjectDao")
public class SubjectDaoImpl implements ISubjectDao {

	private static final Logger LOG = Logger.getLogger(SubjectDaoImpl.class.getName());
	private static final String GET_GUMANITARIUM_SUBJECTS	= "SELECT s FROM Subject s WHERE s.name IN ('history', 'literature', 'phylosophy')";
	private static final String GET_ALL_SUBJECTS = "SELECT s FROM Subject s";

	private EntityManager entityManager;

	public SubjectDaoImpl() {
	}

	@Transactional
	@Override
	public Subject create(Subject subject) throws EntityExistsException {
		LOG.trace("User try to create subject");

		if (entityManager.find(Subject.class, subject.getId()) != null) {
			throw new EntityExistsException(subject.toString());
		}

//		EntityTransaction transaction = entityManager.getTransaction();
		try {
//			transaction.begin();
			LOG.trace("Transaction began");
			entityManager.persist(subject);
			LOG.trace("Subject persisted");
//			transaction.commit();
			LOG.trace("Transaction commited");
		} catch (Exception e) {
			LOG.error("Operation failed");
//			transaction.rollback();
			LOG.trace("Transaction rolled back");
			return null;
		}
		return subject;
	}

	@Transactional
	@Override
	public boolean delete(Subject subject) throws EntityNotFoundException {
		LOG.trace("User try to delete group");

		if (entityManager.find(Subject.class, subject.getId()) == null) {
			throw new EntityNotFoundException(subject.toString());
		}

//		EntityTransaction transaction = entityManager.getTransaction();
		try {
//			transaction.begin();
			LOG.trace("Transaction began");
			subject = entityManager.find(Subject.class, subject.getId());
			LOG.trace("Add subject into managed context");
			entityManager.remove(subject);
			LOG.trace("Group removed");
//			transaction.commit();
			LOG.trace("Transaction commited");
		} catch (Exception e) {
			LOG.error("Operation failed");
//			transaction.rollback();
			LOG.trace("Transaction rolled back");
			return false;
		}
		return true;
	}

	@Transactional
	@Override
	public boolean update(Subject subject) throws EntityNotFoundException {
		LOG.trace("User try to update subject");
//		EntityTransaction transaction = entityManager.getTransaction();

		Subject subjectFromDB = entityManager.find(Subject.class, subject.getId());

		if (subjectFromDB == null) {
			throw new EntityNotFoundException(subject.toString());
		}

		LOG.trace("Subject found correct");
		Utils.copySubject(subject, subjectFromDB);
		LOG.trace("Subject data updated correct");
		try {
//			transaction.begin();
			LOG.trace("Transaction began");
			entityManager.persist(subjectFromDB);
			LOG.trace("Subject added to managed context");
//			transaction.commit();
			LOG.trace("Transaction commited");
		} catch (Exception e) {
			LOG.error("Operation failed");
//			transaction.rollback();
			LOG.trace("Transaction rolled back");
			return false;
		}
		return true;
	}

	@Transactional
	@Override
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

	@Transactional
	@Override
	public List<Subject> getAll(int firstRow, int rowsAmount) {
		LOG.trace("User try to find subjects from " + firstRow + " row and with row amount " + rowsAmount);
		return entityManager.createQuery(GET_ALL_SUBJECTS)
				.setFirstResult(firstRow)
				.setMaxResults(rowsAmount)
				.getResultList();
	}

	@Transactional
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

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
