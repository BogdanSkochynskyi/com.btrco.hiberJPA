package dao;

import entity.Student;
import utils.Utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class StudentDaoImpl implements CrudDao<Student> {

	private EntityManagerFactory factory;

	public StudentDaoImpl(EntityManagerFactory factory) {
		this.factory = factory;
	}

	public Student create(Student student) {
		EntityManager manager = factory.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		try {
			transaction.begin();
			manager.persist(student);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			return null;
		} finally {
			manager.close();
		}
		return student;
	}

	public boolean delete(Student student) {
		EntityManager manager = factory.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		try {
			transaction.begin();
			manager.remove(student);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			return false;
		} finally {
			manager.close();
		}
		return true;
	}

	public boolean update(Student student) {
		EntityManager manager = factory.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();

		Student studentFromDB = manager.find(Student.class, student.getId());
		Utils.copyStudent(student, studentFromDB);

		try {
			transaction.begin();
			manager.persist(student);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			return false;
		} finally {
			manager.close();
		}
		return true;
	}

	public Student findById(Object id) {
		EntityManager manager = factory.createEntityManager();
		try {
			Student studentFromDB = manager.find(Student.class, id);
			return studentFromDB;
		} finally {
			manager.close();
		}
	}
}
