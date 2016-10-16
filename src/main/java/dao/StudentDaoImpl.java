package dao;

import dao.impl.mysql.IStudentDao;
import entity.Group;
import entity.Student;
import org.apache.log4j.Logger;
import utils.HibernateUtils;
import utils.Utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class StudentDaoImpl implements IStudentDao {

	private static final Logger LOG = Logger.getLogger(StudentDaoImpl.class.getName());
	private static final String GET_GROUP_OF_STUDENTS	= "SELECT s FROM Student s WHERE s.group LIKE :group";
	private static final String GROUP = "group";
	private static final String GET_ALL_STUDENTS = "SELECT s FROM Student s";

	private EntityManager entityManager;

	public StudentDaoImpl() {
		this.entityManager = HibernateUtils.getEntityManager();
	}

	public StudentDaoImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Student create(Student student) {
		LOG.info("User try to create student");
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();
			LOG.info("Transaction began");
			entityManager.persist(student);
			LOG.info("Student persisted");
			transaction.commit();
			LOG.info("Transaction commited");
		} catch (Exception e) {
			LOG.error("Operation failed");
			transaction.rollback();
			LOG.info("Transaction rolled back");
			return null;
		}
		return student;
	}

	public boolean delete(Student student) {
		LOG.info("User try to delete student");
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();
			LOG.info("Transaction began");
			student = entityManager.find(Student.class, student.getId());
			LOG.info("Add student into managed context");
			entityManager.remove(student);
			LOG.info("Student removed");
			transaction.commit();
		} catch (Exception e) {
			LOG.error("Operation failed");
			transaction.rollback();
			LOG.info("Transaction rolled back");
			return false;
		}
		return true;
	}

	public boolean update(Student student) {
		LOG.info("User try to update student");
		EntityTransaction transaction = entityManager.getTransaction();

		Student studentFromDB = entityManager.find(Student.class, student.getId());
		LOG.info("Student found correct");
		Utils.copyStudent(student, studentFromDB);
		LOG.info("Group data updated correct");
		try {
			transaction.begin();
			LOG.info("Transaction began");
			entityManager.persist(student);
			LOG.info("Student added to managed context");
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

	public Student findById(Object id) {
		LOG.info("User try to find student by id");
		try {
			Student studentFromDB = entityManager.find(Student.class, id);
			if (studentFromDB != null)
			{
				LOG.info("Student " + studentFromDB + " found correct");
			} else {
				LOG.info("Student with id " + id + " not found");
			}
			return studentFromDB;
		} catch (Exception e) {
			LOG.error("Operation failed");
			return null;
		}
	}

	@Override
	public List<Student> getAll(int firstRow, int rowsAmount) {
		LOG.info("User try to find students from " + firstRow + " row and with row amount " + rowsAmount);
		return entityManager.createQuery(GET_ALL_STUDENTS)
				.setFirstResult(firstRow)
				.setMaxResults(rowsAmount)
				.getResultList();
	}

	public List<Student> getListOfStudentsInGroup(Group group) {
		LOG.info("User try to get list of students by group");
		List<Student> students = entityManager.createQuery(GET_GROUP_OF_STUDENTS)
				.setParameter(GROUP, group).getResultList();
		if (students != null) {
			LOG.info("Students found correctly");
			return students;
		}
		LOG.info("Students not found");
		return null;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
