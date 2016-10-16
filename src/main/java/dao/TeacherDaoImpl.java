package dao;

import dao.impl.mysql.ITeacherDao;
import entity.Teacher;
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

	public Teacher create(Teacher teacher) {
		LOG.info("User try to create teacher");
		EntityTransaction transaction = entityManager.getTransaction();
		try{
			transaction.begin();
			LOG.info("Transaction began");
			entityManager.persist(teacher);
			LOG.info("Teacher persisted");
			transaction.commit();
			LOG.info("Transaction commited");
		} catch (Exception e) {
			LOG.error("Operation failed");
			transaction.rollback();
			LOG.info("Transaction rolled back");
			return null;
		}
		return teacher;
	}

	public boolean delete(Teacher teacher) {
		LOG.info("User try to delete teacher");
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();
			LOG.info("Transaction began");
			teacher = entityManager.find(Teacher.class, teacher.getId());
			LOG.info("Add teacher into managed context");
			entityManager.remove(teacher);
			LOG.info("Teacher removed");
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

	public boolean update(Teacher teacher) {
		LOG.info("User try to update teacher");
		EntityTransaction transaction = entityManager.getTransaction();

		Teacher teacherFromDB = entityManager.find(Teacher.class, teacher.getId());
		LOG.info("Teacher found correct");
		Utils.copyTeacher(teacher, teacherFromDB);
		LOG.info("Teacher data updated correct");
		try {
			transaction.begin();
			LOG.info("Transaction began");
			entityManager.persist(teacherFromDB);
			LOG.info("Teacher added to managed context");
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

	public Teacher findById(Object id) {
		LOG.info("User try to find teacher by id");
		try {
			Teacher teacherFromDB = entityManager.find(Teacher.class, id);
			if (teacherFromDB != null)
			{
				LOG.info("Teacher " + teacherFromDB + " found correct");
			} else {
				LOG.info("Teacher with id " + id + " not found");
			}
			return teacherFromDB;
		} catch (Exception e) {
			LOG.error("Operation failed");
			return null;
		}
	}

	@Override
	public List<Teacher> getAll(int firstRow, int rowsAmount) {
		LOG.info("User try to find teachers from " + firstRow + " row and with row amount " + rowsAmount);
		return entityManager.createQuery(GET_ALL_STUDENTS)
				.setFirstResult(firstRow)
				.setMaxResults(rowsAmount)
				.getResultList();
	}

	public Teacher getMostExperienceTeacher() {
		LOG.info("User try to find most experienced teacher");
		List<Teacher> teachers = entityManager.createQuery(GET_MOST_EXPERIENCED_TEACHER).getResultList();
		if (teachers.size() != 0) {
			LOG.info("Teacher found correctly");
			return teachers.get(0);
		}
		LOG.info("Teacher not found");
		return null;
	}

	public Teacher getLessExperienceTeacher() {
		LOG.info("User try to find less experienced teacher");
		List<Teacher> teachers = entityManager.createQuery(GET_LESS_EXPERIENCED_TEACHER).getResultList();
		if (teachers.size() != 0) {
			LOG.info("Teacher found correctly");
			return teachers.get(0);
		}
		LOG.info("Teacher not found");
		return null;
	}

	public List<Teacher> getTeachersWithMoreThanThreeYearsExperience(int experience) {
		LOG.info("User try to find more than " + experience + " years experienced teacher");
		List<Teacher> teachers = entityManager.createQuery(GET_TEACHERS_THAT_HAVE_EXPERIENCE_MORE_THAN_YEARS)
				.setParameter(EXPERIENCE, experience).getResultList();
		if (teachers != null) {
			LOG.info("Teachers found correctly");
			return teachers;
		}
		LOG.info("Teacher not found");
		return null;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
