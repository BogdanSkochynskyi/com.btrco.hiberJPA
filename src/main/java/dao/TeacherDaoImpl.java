package dao;

import entity.Subject;
import entity.Teacher;
import utils.Utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class TeacherDaoImpl implements CrudDao<Teacher> {

	private EntityManagerFactory factory;

	public TeacherDaoImpl(EntityManagerFactory factory) {
		this.factory = factory;
	}

	public Teacher create(Teacher teacher) {
		EntityManager manager = this.factory.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		try{
			transaction.begin();
			manager.persist(teacher);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			return null;
		} finally {
			manager.close();
		}
		return teacher;
	}

	public boolean delete(Teacher teacher) {
		EntityManager manager = factory.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		try {
			transaction.begin();
			manager.remove(teacher);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			return false;
		} finally {
			manager.close();
		}
		return true;
	}

	public boolean update(Teacher teacher) {
		EntityManager manager = factory.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();

		Teacher teacherFromDB = manager.find(Teacher.class, teacher.getId());
		Utils.copyTeacher(teacher, teacherFromDB);

		try {
			transaction.begin();
			manager.persist(teacher);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			return false;
		} finally {
			manager.close();
		}
		return true;
	}

	public Teacher findById(Object id) {
		EntityManager manager = factory.createEntityManager();
		try {
			Teacher teacherFromDB = manager.find(Teacher.class, id);
			return teacherFromDB;
		} finally {
			manager.close();
		}
	}
}
