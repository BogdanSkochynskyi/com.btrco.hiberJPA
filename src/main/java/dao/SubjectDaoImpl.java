package dao;

import entity.Student;
import entity.Subject;
import utils.Utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class SubjectDaoImpl implements CrudDao<Subject>{

	private EntityManagerFactory factory;

	public SubjectDaoImpl(EntityManagerFactory factory) {
		this.factory = factory;
	}

	public Subject create(Subject subject) {
		EntityManager manager = factory.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		try {
			transaction.begin();
			manager.persist(subject);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			return null;
		} finally {
			manager.close();
		}
		return subject;
	}

	public boolean delete(Subject subject) {
		EntityManager manager = factory.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		try {
			transaction.begin();
			manager.remove(subject);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			return false;
		} finally {
			manager.close();
		}
		return true;
	}

	public boolean update(Subject subject) {
		EntityManager manager = factory.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();

		Subject subjectFromDB = manager.find(Subject.class, subject.getId());
		Utils.copySubject(subject, subjectFromDB);

		try {
			transaction.begin();
			manager.persist(subject);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			return false;
		} finally {
			manager.close();
		}
		return true;
	}

	public Subject findById(Object id) {
		EntityManager manager = factory.createEntityManager();
		try {
			Subject subjectFromDB = manager.find(Subject.class, id);
			return subjectFromDB;
		} finally {
			manager.close();
		}
	}
}
