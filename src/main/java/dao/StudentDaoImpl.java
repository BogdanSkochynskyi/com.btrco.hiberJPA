package dao;

import dao.service.IStudentDao;
import entity.Group;
import entity.Student;
import utils.HibernateUtils;
import utils.Utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class StudentDaoImpl implements IStudentDao {

	private static final String GET_GROUP_OF_STUDENTS	= "SELECT s FROM Student s WHERE s.group LIKE :group";
	private static final String GROUP = "group";
	private static final String GET_ALL_STUDENTS = "SELECT s FROM Student s";

	private static EntityManager entityManager;

	static {
		entityManager = HibernateUtils.getEntityManager();
	}

	public StudentDaoImpl() {
	}

	public Student create(Student student) {
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();
			entityManager.persist(student); //entityManager.persisit(addrres) должны быть в контектсе два оъекта чтобы их связать
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			return null;
		} finally {
			entityManager.close();
		}
		return student;
	}

	public boolean delete(Student student) {
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();
			entityManager.remove(student);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			return false;
		} finally {
			entityManager.close();
		}
		return true;
	}

	public boolean update(Student student) {
		EntityTransaction transaction = entityManager.getTransaction();

		Student studentFromDB = entityManager.find(Student.class, student.getId());
		Utils.copyStudent(student, studentFromDB);

		try {
			transaction.begin();
			entityManager.persist(student);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			return false;
		} finally {
			entityManager.close();
		}
		return true;
	}

	public Student findById(Object id) {
		try {
			Student studentFromDB = entityManager.find(Student.class, id);
			return studentFromDB;
		} finally {
			entityManager.close();
		}
	}

	@Override
	public List<Student> getAll() {
		return entityManager.createQuery(GET_ALL_STUDENTS).setMaxResults(100).getResultList();
	}

	public List<Student> getListOfStudentsInGroup(Group group) {
		List<Student> students = entityManager.createQuery(GET_GROUP_OF_STUDENTS)
				.setParameter(GROUP, group).getResultList();
		if (students != null) {
			return students;
		}
		return null;
	}

	public static void setEntityManager(EntityManager entityManager) {
		StudentDaoImpl.entityManager = entityManager;
	}
}
