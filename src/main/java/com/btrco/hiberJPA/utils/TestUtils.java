package com.btrco.hiberJPA.utils;

import com.btrco.hiberJPA.entity.Group;
import com.btrco.hiberJPA.entity.Student;
import com.btrco.hiberJPA.entity.Subject;
import com.btrco.hiberJPA.entity.Teacher;
import com.btrco.hiberJPA.exceptions.EntityNotFoundException;
import com.btrco.hiberJPA.exceptions.RowsAmountException;
import com.btrco.hiberJPA.service.IGroupService;
import com.btrco.hiberJPA.service.IStudentService;
import com.btrco.hiberJPA.service.ISubjectService;
import com.btrco.hiberJPA.service.ITeacherService;
import com.btrco.hiberJPA.service.implementation.GroupServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Random;

@Component("testUtils")
public class TestUtils {
	private static final String HISTORY = "history";
	private static final String LITERATURE = "literature";
	private static final String PHYLOSOPHY = "phylosophy";

	@Autowired
	private IGroupService groupService;

	@PersistenceContext
	private EntityManager manager;

	public TestUtils() {
	}

	/**
	 * Add test data into all tables
	 */
	@Transactional
	public void addDataIntoDB() {
//		manager = HibernateUtils.getEntityManager();
		addDataIntoGroupsTable();
		try {
			addDataIntoStudentsTable();
		} catch (RowsAmountException | EntityNotFoundException e) {
			e.printStackTrace();
		}
		addDataIntoSubjectsTable();
		addDataIntoTeacherTable();
		this.manager.close();
	}

	/**
	 * Add test data into groups table
	 */
	@Transactional
	private void addDataIntoGroupsTable(){
//		EntityTransaction transaction = manager.getTransaction();
//		transaction.begin();
		for (int i = 0; i < 20; i++) {
			this.manager.persist(new Group("Group #" + i+1));
		}
//		transaction.commit();
	}

	/**
	 * Add test data into students table
	 */
	@Transactional
	private void addDataIntoStudentsTable() throws RowsAmountException, EntityNotFoundException {
//		EntityTransaction transaction = manager.getTransaction();
//		transaction.begin();
		List<Group> groups = groupService.getListOfGroup(0, 20);
		int counter = 0;
		for (int i = 0; i < 100; i++) {
			this.manager.persist(new Student("Student #" + i+1, this.manager.find(Group.class, groups.get(counter++).getId())));
			if (counter >= 19) {
				counter = 0;
			}
		}
//		transaction.commit();
	}

	/**
	 * Add test data into subjects table
	 */
	@Transactional
	private void addDataIntoSubjectsTable(){
//		EntityTransaction transaction = manager.getTransaction();
//		transaction.begin();
		for (int i = 0; i < 30; i++) {
			this.manager.persist(new Subject("Subject #" + i+1, "Subject description #" + i+1));
		}
//		transaction.commit();
	}

	/**
	 * Add test data into teachers table
	 */
	@Transactional
	private void addDataIntoTeacherTable(){
//		EntityTransaction transaction = manager.getTransaction();
//		transaction.begin();
		for (int i = 0; i < 10; i++) {
			int id = new Random().nextInt(30) + 1;
			this.manager.persist(new Teacher("Teacher #" + i+1, new Random().nextInt(30), this.manager.find(Subject.class, id)));
		}
//		transaction.commit();
	}

	/**
	 * Remove data from all tables
	 */
	@Transactional
	public void trancateTables () {
//		manager = HibernateUtils.getEntityManager();
//		EntityTransaction transaction = manager.getTransaction();
//		transaction.begin();
		this.manager.createQuery("DELETE FROM Student").executeUpdate();
		this.manager.createQuery("DELETE FROM Group").executeUpdate();
		this.manager.createQuery("DELETE FROM Teacher").executeUpdate();
		this.manager.createQuery("DELETE FROM Subject").executeUpdate();
//		transaction.commit();
		this.manager.close();
	}

	/**
	 * Check is subject name refers to gumanitarium
	 * @param subject subject that is checked
	 * @return true if subject name refers to "history", "literature" or "phylosophy"
	 */
	public static boolean checkIsItGumanitariumSubject(Subject subject){
		return subject.getName().equals(HISTORY) ||
				subject.getName().equals(LITERATURE) ||
				subject.getName().equals(PHYLOSOPHY);

	}

	/**
	 * Check is teacher more\less experience than other teacher
	 * @param checkedTeacher teacher that checked
	 * @param teacher teacher with whom compare
	 * @param isMax if true - check is checkedTeacher more experienced than teacher
	 *              if else - check is checkedTeacher less experienced than teacher
	 * @return true if checkedTeacher more\less experienced than teacher
	 */
	public static boolean checkExperienceMinMax(Teacher checkedTeacher, Teacher teacher, boolean isMax) {
		return isMax ? checkedTeacher.getExperience() >= teacher.getExperience() : checkedTeacher.getExperience() <= teacher.getExperience();
	}

	public EntityManager getManager() {
		return this.manager;
	}

//	@PersistenceContext()
	public void setManager(EntityManager manager) {
		this.manager = manager;
	}
}
