package dao.implementation.mySQL;

import dao.ITeacherDao;
import entity.Subject;
import entity.Teacher;
import exceptions.EntityExistsException;
import exceptions.EntityNotFoundException;
import exceptions.InvalidIdException;
import exceptions.InvalidNumberException;
import org.apache.log4j.Logger;
import utils.HibernateUtils;
import utils.Utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class TeacherDaoImpl implements ITeacherDao {
	private static final Logger LOG = Logger.getLogger(TeacherDaoImpl.class.getName());
	private static final String GET_TEACHERS_THAT_HAVE_EXPERIENCE_MORE_THAN_YEARS = "SELECT t FROM Teacher t WHERE t.experience > :experience";
	private static final String GET_MOST_EXPERIENCED_TEACHER	= "SELECT t FROM Teacher t WHERE t.experience = (SELECT MAX(tt.experience) FROM Teacher tt)";
	private static final String GET_LESS_EXPERIENCED_TEACHER	= "SELECT t FROM Teacher t WHERE t.experience = (SELECT MIN(tt.experience) FROM Teacher tt)";
	private static final String GET_ALL_STUDENTS = "SELECT t FROM Teacher t";
	public static final String EXPERIENCE = "experience";
	private EntityManager entityManager;

	public TeacherDaoImpl() {
		this.entityManager = HibernateUtils.getEntityManager();
	}

	public TeacherDaoImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Teacher create(Teacher teacher) throws EntityExistsException {
		LOG.trace("User try to create teacher");

		if (entityManager.find(Teacher.class, teacher.getId()) != null) {
			throw new EntityExistsException(teacher.toString());
		}

		EntityTransaction transaction = entityManager.getTransaction();
		try{
			transaction.begin();
			LOG.trace("Transaction began");
			entityManager.persist(teacher);
			LOG.trace("Teacher persisted");
			transaction.commit();
			LOG.trace("Transaction commited");
		} catch (Exception e) {
			LOG.error("Operation failed");
			transaction.rollback();
			LOG.trace("Transaction rolled back");
			return null;
		}
		return teacher;
	}

	public boolean delete(Teacher teacher) throws EntityNotFoundException {
		LOG.trace("User try to delete teacher");

		if (entityManager.find(Teacher.class, teacher.getId()) == null) {
			throw new EntityNotFoundException(teacher.toString());
		}

		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();
			LOG.trace("Transaction began");
			teacher = entityManager.find(Teacher.class, teacher.getId());
			LOG.trace("Add teacher into managed context");
			entityManager.remove(teacher);
			LOG.trace("Teacher removed");
			transaction.commit();
			LOG.trace("Transaction commited");
		} catch (Exception e) {
			LOG.error("Operation failed");
			transaction.rollback();
			LOG.trace("Transaction rolled back");
			return false;
		}
		return true;
	}

	public boolean update(Teacher teacher) throws EntityNotFoundException {
		LOG.trace("User try to update teacher");
		EntityTransaction transaction = entityManager.getTransaction();

		Teacher teacherFromDB = entityManager.find(Teacher.class, teacher.getId());

		if (teacherFromDB == null) {
			throw new EntityNotFoundException(teacher.toString());
		}

		LOG.trace("Teacher found correct");
		Utils.copyTeacher(teacher, teacherFromDB);
		LOG.trace("Teacher data updated correct");
		try {
			transaction.begin();
			LOG.trace("Transaction began");
			entityManager.persist(teacherFromDB);
			LOG.trace("Teacher added to managed context");
			transaction.commit();
			LOG.trace("Transaction commited");
		} catch (Exception e) {
			LOG.error("Operation failed");
			transaction.rollback();
			LOG.trace("Transaction rolled back");
			return false;
		}
		return true;
	}

	public Teacher findById(Object id) throws InvalidIdException, EntityNotFoundException {
		LOG.trace("User try to find teacher by id");

		if (!(id instanceof Integer) || ((Integer)id < 0)) {
			throw new InvalidIdException(id.toString());
		}

		Teacher teacherFromDB = entityManager.find(Teacher.class, id);
		if (teacherFromDB == null) {
			throw new EntityNotFoundException((Integer)id);
		}

		return teacherFromDB;
	}

	@Override
	public List<Teacher> getAll(int firstRow, int rowsAmount) {
		LOG.trace("User try to find teachers from " + firstRow + " row and with row amount " + rowsAmount);
		return entityManager.createQuery(GET_ALL_STUDENTS)
				.setFirstResult(firstRow)
				.setMaxResults(rowsAmount)
				.getResultList();
	}

	public Teacher getMostExperienceTeacher() throws EntityNotFoundException {
		LOG.trace("User try to find most experienced teacher");
		List<Teacher> teachers = entityManager.createQuery(GET_MOST_EXPERIENCED_TEACHER).getResultList();
		if (teachers.size() != 0) {
			LOG.trace("Teacher found correctly");
			return teachers.get(0);
		} else {
			throw new EntityNotFoundException("Most experienced teacher ");
		}
	}

	public Teacher getLessExperienceTeacher() throws EntityNotFoundException {
		LOG.trace("User try to find less experienced teacher");
		List<Teacher> teachers = entityManager.createQuery(GET_LESS_EXPERIENCED_TEACHER).getResultList();
		if (teachers.size() != 0) {
			LOG.trace("Teacher found correctly");
			return teachers.get(0);
		} else {
			throw new EntityNotFoundException("Less experienced teacher ");
		}
	}

	public List<Teacher> getTeachersWithMoreThanYearsExperience(int experience) throws InvalidNumberException, EntityNotFoundException {
		LOG.trace("User try to find more than " + experience + " years experienced teacher");

		if (experience < 0) {
			throw new InvalidNumberException(experience);
		}

		List<Teacher> teachers = entityManager.createQuery(GET_TEACHERS_THAT_HAVE_EXPERIENCE_MORE_THAN_YEARS)
				.setParameter(EXPERIENCE, experience).getResultList();
		if (teachers != null) {
			LOG.trace("Teachers found correctly");
			return teachers;
		} else {
			throw new EntityNotFoundException("Teacher with " + experience + " years experience ");
		}
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
