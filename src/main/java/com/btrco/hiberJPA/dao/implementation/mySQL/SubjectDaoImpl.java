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

	@PersistenceContext
	private EntityManager entityManager;

	public SubjectDaoImpl() {
	}

	@Transactional
	@Override
	public Subject create(Subject subject) throws EntityExistsException {
		if (entityManager.find(Subject.class, subject.getId()) != null) {
			throw new EntityExistsException(subject.toString());
		}

		try {
			entityManager.persist(subject);
		} catch (Exception e) {
			return null;
		}

		return subject;
	}

	@Transactional
	@Override
	public boolean delete(Subject subject) throws EntityNotFoundException {
		if (entityManager.find(Subject.class, subject.getId()) == null) {
			throw new EntityNotFoundException(subject.toString());
		}

		try {
			subject = entityManager.find(Subject.class, subject.getId());
			entityManager.remove(subject);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	@Transactional
	@Override
	public boolean update(Subject subject) throws EntityNotFoundException {
		Subject subjectFromDB = entityManager.find(Subject.class, subject.getId());

		if (subjectFromDB == null) {
			throw new EntityNotFoundException(subject.toString());
		}

		Utils.copySubject(subject, subjectFromDB);

		try {
			entityManager.persist(subjectFromDB);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	@Transactional
	@Override
	public Subject findById(Object id) throws InvalidIdException, EntityNotFoundException {
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
		return entityManager.createQuery(GET_ALL_SUBJECTS)
				.setFirstResult(firstRow)
				.setMaxResults(rowsAmount)
				.getResultList();
	}

	@Transactional
	@Override
	public List<Subject> getGumanitariumSubjects() throws EntityNotFoundException {
		List<Subject> gumanitariumSubjects = entityManager.createQuery(GET_GUMANITARIUM_SUBJECTS).getResultList();

		if (gumanitariumSubjects != null) {
			return gumanitariumSubjects;
		} else {
			throw new EntityNotFoundException("Gumanitarium subjects ");
		}
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
