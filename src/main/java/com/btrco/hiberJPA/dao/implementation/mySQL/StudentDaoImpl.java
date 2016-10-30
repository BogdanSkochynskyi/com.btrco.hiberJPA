package com.btrco.hiberJPA.dao.implementation.mySQL;

import com.btrco.hiberJPA.dao.IStudentDao;
import com.btrco.hiberJPA.entity.Group;
import com.btrco.hiberJPA.entity.Student;
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


@Component("studentDao")
public class StudentDaoImpl implements IStudentDao {

	private static final Logger LOG = Logger.getLogger(StudentDaoImpl.class.getName());
	private static final String GET_GROUP_OF_STUDENTS	= "SELECT s FROM Student s WHERE s.group LIKE :group";
	private static final String GROUP = "group";
	private static final String GET_ALL_STUDENTS = "SELECT s FROM Student s";

	private EntityManager entityManager;

	public StudentDaoImpl() {

	}

	@Transactional
	@Override
	public Student create(Student student) throws EntityExistsException {
		LOG.trace("User try to create student");

		if (entityManager.find(Student.class, student.getId()) != null) {
			throw new EntityExistsException(student.toString());
		}

//		EntityTransaction transaction = entityManager.getTransaction();
		try {
//			transaction.begin();
			LOG.trace("Transaction began");
			entityManager.persist(student);
			LOG.trace("Student persisted");
//			transaction.commit();
			LOG.trace("Transaction commited");
		} catch (Exception e) {
			LOG.error("Operation failed");
//			transaction.rollback();
			LOG.trace("Transaction rolled back");
			return null;
		}
		return student;
	}

	@Transactional
	@Override
	public boolean delete(Student student) throws EntityNotFoundException {
		LOG.trace("User try to delete student");

		if (entityManager.find(Student.class, student.getId()) == null) {
			throw new EntityNotFoundException(student.toString());
		}

//		EntityTransaction transaction = entityManager.getTransaction();
		try {
//			transaction.begin();
			LOG.trace("Transaction began");
			student = entityManager.find(Student.class, student.getId());
			LOG.trace("Add student into managed context");
			entityManager.remove(student);
			LOG.trace("Student removed");
//			transaction.commit();
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
	public boolean update(Student student) throws EntityNotFoundException {
		LOG.trace("User try to update student");
//		EntityTransaction transaction = entityManager.getTransaction();

		Student studentFromDB = entityManager.find(Student.class, student.getId());

		if (studentFromDB == null) {
			throw new EntityNotFoundException(student.toString());
		}

		LOG.trace("Student found correct");
		Utils.copyStudent(student, studentFromDB);
		LOG.trace("Group data updated correct");
		try {
//			transaction.begin();
			LOG.trace("Transaction began");
			entityManager.persist(student);
			LOG.trace("Student added to managed context");
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
	public Student findById(Object id) throws InvalidIdException, EntityNotFoundException {
		LOG.trace("User try to find student by id");

		if (!(id instanceof Integer) || ((Integer)id < 0)) {
			throw new InvalidIdException(id.toString());
		}

		Student studentFromDB = entityManager.find(Student.class, id);

		if (studentFromDB == null) {
			throw new EntityNotFoundException((Integer)id);
		}
		return studentFromDB;
	}

	@Transactional
	@Override
	public List<Student> getAll(int firstRow, int rowsAmount) {
		LOG.trace("User try to find students from " + firstRow + " row and with row amount " + rowsAmount);
		return entityManager.createQuery(GET_ALL_STUDENTS)
				.setFirstResult(firstRow)
				.setMaxResults(rowsAmount)
				.getResultList();
	}

	@Transactional
	@Override
	public List<Student> getListOfStudentsInGroup(Group group) throws EntityNotFoundException {
		LOG.trace("User try to get list of students by group");

		if (entityManager.find(Group.class, group.getId()) == null) {
			throw new EntityNotFoundException(group.toString());
		}

		List<Student> students = entityManager.createQuery(GET_GROUP_OF_STUDENTS)
				.setParameter(GROUP, group).getResultList();
		if (students != null) {
			LOG.trace("Students found correctly");
			return students;
		} else {
			throw new EntityNotFoundException("Students with group " + group.toString());
		}
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
