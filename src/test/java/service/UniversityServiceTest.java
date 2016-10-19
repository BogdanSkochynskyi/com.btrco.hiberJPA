package service;

import entity.Group;
import entity.Student;
import entity.Subject;
import entity.Teacher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class UniversityServiceTest {

	private static IUniversityService service;
	private Student testStudent;
	private Group testGroup;
	private Teacher testTeacher;
	private Subject testSubject;

	@Before
	public void initServiceAndData(){
		service = new UniversityService();
		this.testStudent = new Student();
		this.testGroup 	 = new Group();
		this.testTeacher = new Teacher();
		this.testSubject = new Subject();
	}

	@Test
	public void testGetListOfStudents(){
		List<Student> studentList = service.getListOfStudents();
		Assert.assertNotNull(studentList);
	}

	@Test
	public void testGetListOfSubjects(){
		List<Subject> subjectList = service.getListOfSubjects();
		Assert.assertNotNull(subjectList);
	}
	@Test
	public void testGetListOfTeachers(){
		List<Teacher> teachersList = service.getListOfTeachers();
		Assert.assertNotNull(teachersList);
	}

	@Test
	public void testGetListOfGroups(){
		List<Group> groupList = service.getListOfGroup();
		Assert.assertNotNull(groupList);
	}

	@Test
	public void testAddStudent(){
		Student student = service.addStudent(this.testStudent);
		Assert.assertNotNull(student);
	}

	@Test
	public void testAddSubject(){
		Subject subject = service.addSubject(this.testSubject);
		Assert.assertNotNull(subject);
	}
	@Test
	public void testAddTeacher(){
		Teacher teacher = service.addTeacher(this.testTeacher);
		Assert.assertNotNull(teacher);
	}

	@Test
	public void testAddGroup(){
		Group group = service.addGroup(this.testGroup);
		Assert.assertNotNull(group);
	}

	@Test
	public void testUpdateStudent(){
		boolean updated = service.updateStudent(this.testStudent);
		Assert.assertTrue(updated);
	}

	@Test
	public void testUpdateSubject(){
		boolean updated = service.updateSubject(this.testSubject);
		Assert.assertTrue(updated);
	}
	@Test
	public void testUpdateTeacher(){
		boolean updated = service.updateTeacher(this.testTeacher);
		Assert.assertTrue(updated);
	}

	@Test
	public void testUpdateGroup(){
		boolean updated = service.updateGroup(this.testGroup);
		Assert.assertTrue(updated);
	}

	@Test
	public void testGetStudentsByGroup(){
		List<Student> studentListInGroup = service.getStudentsByGroup(this.testGroup);
		Assert.assertNotNull(studentListInGroup);
	}

	@Test
	public void testGetGroupThatStudySubject(){
		List<Group> groupList = service.getGroupsThatStudySubject(this.testSubject);
		Assert.assertNotNull(groupList);
	}

	@Test
	public void testGetSubjectsThatStudyAllGroups(){
		List<Subject> subjectList = service.getSubjectsThatStudyAllGroups();
		Assert.assertNotNull(subjectList);
	}

	@Test
	public void testGetMostExperiencedTeacher(){
		Teacher teacher = service.getMostExperiencedTeacher();
		Assert.assertNotNull(teacher);
	}

	@Test
	public void testGetLessExperiencedTeacher(){
		Teacher teacher = service.getLessExperiencedTeacher();
		Assert.assertNotNull(teacher);
	}

	@Test
	public void testGetTeachersThatHaveExperienceMoreThanYears(){
		List<Teacher> teacherList = service.getTeachersThatHaveExperienceMoreThanYears(3);
		Assert.assertNotNull(teacherList);
	}

	@Test
	public void testGetGumanitariumSubjects(){
		List<Subject> subjectList = service.getHumanitariumSubjects();
		Assert.assertNotNull(subjectList);
	}

	@Test
	public void testGetAverageMarkBySubjectInGroup(){
		float averageMark = service.getAverageMarkBySubjectInGroup(this.testSubject, this.testGroup);
		Assert.assertNotEquals(0, averageMark);
	}

	@Test
	public void testGetAverageMarkBySubject(){
		float averageMark = service.getAverageMarkBySubject(this.testSubject);
		Assert.assertNotEquals(0, averageMark);
	}
}
