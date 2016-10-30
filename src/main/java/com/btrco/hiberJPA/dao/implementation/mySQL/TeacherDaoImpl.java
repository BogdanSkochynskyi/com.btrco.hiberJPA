package com.btrco.hiberJPA.dao.implementation.mySQL;

import com.btrco.hiberJPA.dao.ITeacherDao;
import com.btrco.hiberJPA.entity.Teacher;
import com.btrco.hiberJPA.exceptions.EntityExistsException;
import com.btrco.hiberJPA.exceptions.EntityNotFoundException;
import com.btrco.hiberJPA.exceptions.InvalidIdException;
import com.btrco.hiberJPA.exceptions.InvalidNumberException;
import org.apache.log4j.Logger;
import com.btrco.hiberJPA.utils.HibernateUtils;
import com.btrco.hiberJPA.utils.Utils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component("teacherDao")
public class TeacherDaoImpl implements ITeacherDao {
	private static final Logger LOG = Logger.getLogger(TeacherDaoImpl.class.getName());
	private static final String GET_TEACHERS_THAT_HAVE_EXPERIENCE_MORE_THAN_YEARS = "SELECT t FROM Teacher t WHERE t.experience > :experience";
	private static final String GET_MOST_EXPERIENCED_TEACHER	= "SELECT t FROM Teacher t WHERE t.experience = (SELECT MAX(tt.experience) FROM Teacher tt)";
	private static final String GET_LESS_EXPERIENCED_TEACHER	= "SELECT t FROM Teacher t WHERE t.experience = (SELECT MIN(tt.experience) FROM Teacher tt)";
	private static final String GET_ALL_STUDENTS = "SELECT t FROM Teacher t";
	public static final String EXPERIENCE = "experience";

	@PersistenceContext
	private EntityManager entityManager;

	public TeacherDaoImpl() {
	}

	@Transactional
	@Override
	public Teacher create(Teacher teacher) throws EntityExistsException {
		if (entityManager.find(Teacher.class, teacher.getId()) != null) {
			throw new EntityExistsException(teacher.toString());
		}

		try{
			entityManager.persist(teacher);
		} catch (Exception e) {
			return null;
		}

		return teacher;
	}

	@Transactional
	@Override
	public boolean delete(Teacher teacher) throws EntityNotFoundException {
		if (entityManager.find(Teacher.class, teacher.getId()) == null) {
			throw new EntityNotFoundException(teacher.toString());
		}

		try {
			teacher = entityManager.find(Teacher.class, teacher.getId());
			entityManager.remove(teacher);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	@Transactional
	@Override
	public boolean update(Teacher teacher) throws EntityNotFoundException {
		Teacher teacherFromDB = entityManager.find(Teacher.class, teacher.getId());

		if (teacherFromDB == null) {
			throw new EntityNotFoundException(teacher.toString());
		}

		Utils.copyTeacher(teacher, teacherFromDB);

		try {
			entityManager.persist(teacherFromDB);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	@Transactional
	@Override
	public Teacher findById(Object id) throws InvalidIdException, EntityNotFoundException {
		if (!(id instanceof Integer) || ((Integer)id < 0)) {
			throw new InvalidIdException(id.toString());
		}

		Teacher teacherFromDB = entityManager.find(Teacher.class, id);

		if (teacherFromDB == null) {
			throw new EntityNotFoundException((Integer)id);
		}

		return teacherFromDB;
	}

	@Transactional
	@Override
	public List<Teacher> getAll(int firstRow, int rowsAmount) {
		return entityManager.createQuery(GET_ALL_STUDENTS)
				.setFirstResult(firstRow)
				.setMaxResults(rowsAmount)
				.getResultList();
	}

	@Transactional
	@Override
	public Teacher getMostExperienceTeacher() throws EntityNotFoundException {
		List<Teacher> teachers = entityManager.createQuery(GET_MOST_EXPERIENCED_TEACHER).getResultList();

		if (teachers.size() != 0) {
			return teachers.get(0);
		} else {
			throw new EntityNotFoundException("Most experienced teacher ");
		}
	}

	@Transactional
	@Override
	public Teacher getLessExperienceTeacher() throws EntityNotFoundException {
		List<Teacher> teachers = entityManager.createQuery(GET_LESS_EXPERIENCED_TEACHER).getResultList();

		if (teachers.size() != 0) {
			return teachers.get(0);
		} else {
			throw new EntityNotFoundException("Less experienced teacher ");
		}
	}

	@Transactional
	@Override
	public List<Teacher> getTeachersWithMoreThanYearsExperience(int experience) throws InvalidNumberException, EntityNotFoundException {
		if (experience < 0) {
			throw new InvalidNumberException(experience);
		}

		List<Teacher> teachers = entityManager.createQuery(GET_TEACHERS_THAT_HAVE_EXPERIENCE_MORE_THAN_YEARS)
				.setParameter(EXPERIENCE, experience).getResultList();

		if (teachers != null) {
			return teachers;
		} else {
			throw new EntityNotFoundException("Teacher with " + experience + " years experience ");
		}
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
