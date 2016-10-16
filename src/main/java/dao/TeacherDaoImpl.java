package dao;

import dao.service.ITeacherDao;
import entity.Teacher;
import utils.HibernateUtils;
import utils.Utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class TeacherDaoImpl implements ITeacherDao {

	private static final String GET_TEACHERS_THAT_HAVE_EXPERIENCE_MORE_THAN_3_YEARS	= "SELECT t FROM Teacher t WHERE t.experience > 3";
	private static final String GET_MOST_EXPERIENCED_TEACHER	= "SELECT t FROM Teacher t WHERE t.experience = (SELECT MAX(tt.experience) FROM Teacher tt)";
	private static final String GET_LESS_EXPERIENCED_TEACHER	= "SELECT t FROM Teacher t WHERE t.experience = (SELECT MIN(tt.experience) FROM Teacher tt)";
	private static final String GET_ALL_STUDENTS = "SELECT t FROM Teacher t";
	private static EntityManager entityManager;

	static {
		entityManager = HibernateUtils.getEntityManager();
	}

	public TeacherDaoImpl() {
	}

	public Teacher create(Teacher teacher) {
		EntityManager manager = HibernateUtils.getEntityManager();
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
		EntityManager manager = HibernateUtils.getEntityManager();
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
		EntityManager manager = HibernateUtils.getEntityManager();
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
		EntityManager manager = HibernateUtils.getEntityManager();
		try {
			Teacher teacherFromDB = manager.find(Teacher.class, id);
			return teacherFromDB;
		} finally {
			manager.close();
		}
	}

	@Override
	public List<Teacher> getAll() {
		return entityManager.createQuery(GET_ALL_STUDENTS).setMaxResults(100).getResultList();
	}

	public Teacher getMostExperienceTeacher() {
		List<Teacher> teachers = entityManager.createQuery(GET_MOST_EXPERIENCED_TEACHER).getResultList();
		if (teachers.size() != 0) {
			return teachers.get(0);
		}
		return null;
	}

	public Teacher getLessExperienceTeacher() {
		List<Teacher> teachers = entityManager.createQuery(GET_LESS_EXPERIENCED_TEACHER).getResultList();
		if (teachers.size() != 0) {
			return teachers.get(0);
		}
		return null;
	}

	public List<Teacher> getTeachersWithMoreThanThreeYearsExperience() {

		List<Teacher> teachers = entityManager.createQuery(GET_TEACHERS_THAT_HAVE_EXPERIENCE_MORE_THAN_3_YEARS).getResultList();
		if (teachers != null) {
			return teachers;
		}

		return null;
	}

	public static void setEntityManager(EntityManager entityManager) {
		TeacherDaoImpl.entityManager = entityManager;
	}
}
