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

	@PersistenceContext
	private EntityManager entityManager;

	public StudentDaoImpl() {

	}

	@Transactional
	@Override
	public Student create(Student student) throws EntityExistsException {
		if (entityManager.find(Student.class, student.getId()) != null) {
			throw new EntityExistsException(student.toString());
		}

		try {
			entityManager.persist(student);
		} catch (Exception e) {
			return null;
		}

		return student;
	}

	@Transactional
	@Override
	public boolean delete(Student student) throws EntityNotFoundException {
		if (entityManager.find(Student.class, student.getId()) == null) {
			throw new EntityNotFoundException(student.toString());
		}

		try {
			student = entityManager.find(Student.class, student.getId());
			entityManager.remove(student);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	@Transactional
	@Override
	public boolean update(Student student) throws EntityNotFoundException {
		Student studentFromDB = entityManager.find(Student.class, student.getId());

		if (studentFromDB == null) {
			throw new EntityNotFoundException(student.toString());
		}

		Utils.copyStudent(student, studentFromDB);

		try {
			entityManager.persist(student);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	@Transactional
	@Override
	public Student findById(Object id) throws InvalidIdException, EntityNotFoundException {
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
		return entityManager.createQuery(GET_ALL_STUDENTS)
				.setFirstResult(firstRow)
				.setMaxResults(rowsAmount)
				.getResultList();
	}

	@Transactional
	@Override
	public List<Student> getListOfStudentsInGroup(Group group) throws EntityNotFoundException {
		if (entityManager.find(Group.class, group.getId()) == null) {
			throw new EntityNotFoundException(group.toString());
		}

		List<Student> students = entityManager.createQuery(GET_GROUP_OF_STUDENTS)
				.setParameter(GROUP, group).getResultList();

		if (students != null) {
			return students;
		} else {
			throw new EntityNotFoundException("Students with group " + group.toString());
		}
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
