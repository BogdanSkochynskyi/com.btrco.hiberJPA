package utils;

import entity.Group;
import entity.Student;
import entity.Subject;
import entity.Teacher;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Random;

public class TestUtils {
	private static final String HISTORY = "history";
	private static final String LITERATURE = "literature";
	private static final String PHYLOSOPHY = "phylosophy";

	private static EntityManager manager;

	/**
	 * Add test data into all tables
	 */
	public static void addDataIntoDB() {
		manager = HibernateUtils.getEntityManager();
		addDataIntoGroupsTable();
		addDataIntoStudentsTable();
		addDataIntoSubjectsTable();
		addDataIntoTeacherTable();
		manager.close();
	}

	/**
	 * Add test data into groups table
	 */
	private static void addDataIntoGroupsTable(){
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
		for (int i = 0; i < 20; i++) {
			manager.persist(new Group("Group #" + i+1));
		}
		transaction.commit();
	}

	/**
	 * Add test data into students table
	 */
	private static void addDataIntoStudentsTable(){
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
		int id = 1;
		for (int i = 0; i < 100; i++) {
			manager.persist(new Student("Student #" + i+1, manager.find(Group.class, id >= 20 ? id=1 : id++)));
		}
		transaction.commit();
	}

	/**
	 * Add test data into subjects table
	 */
	private static void addDataIntoSubjectsTable(){
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
		for (int i = 0; i < 30; i++) {
			manager.persist(new Subject("Subject #" + i+1, "Subject description #" + i+1));
		}
		transaction.commit();
	}

	/**
	 * Add test data into teachers table
	 */
	private static void addDataIntoTeacherTable(){
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
		for (int i = 0; i < 10; i++) {
			int id = new Random().nextInt(30) + 1;
			manager.persist(new Teacher("Teacher #" + i+1, new Random().nextInt(30), manager.find(Subject.class, id)));
		}
		transaction.commit();
	}

	/**
	 * Remove data from all tables
	 */
	public static void trancateTables () {
		manager = HibernateUtils.getEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
		manager.createQuery("DELETE FROM Student").executeUpdate();
		manager.createQuery("DELETE FROM Group").executeUpdate();
		manager.createQuery("DELETE FROM Teacher").executeUpdate();
		manager.createQuery("DELETE FROM Subject").executeUpdate();
		transaction.commit();
		manager.close();
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
}
