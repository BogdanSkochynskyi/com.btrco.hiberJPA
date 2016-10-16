package service;

import entity.Group;
import entity.Student;
import entity.Subject;
import entity.Teacher;

import java.util.List;

public interface IUniversityService {

	List<Student> getListOfStudents();

	List<Subject> getListOfSubjects();

	List<Group> getListOfGroup();

	List<Teacher> getListOfTeachers();

	Student addStudent(Student student);

	Subject addSubject(Subject subject);

	Group addGroup(Group group);

	Teacher addTeacher(Teacher teacher);

	boolean updateStudent(Student student);

	boolean updateSubject(Subject subject);

	boolean updateGroup(Group group);

	boolean updateTeacher(Teacher teacher);

	List<Student> getStudentsByGroup(Group group);

	List<Group> getGroupsThatStudySubject(Subject subject);

	List<Subject> getSubjectsThatStudyAllGroups();

	Teacher getMostExperiencedTeacher();

	Teacher getLessExperiencedTeacher();

	List<Teacher> getTeachersThatHaveExperienceMoreThanYears(int years);

	List<Subject> getHumanitariumSubjects();

	float getAverageMarkBySubjectInGroup(Subject subject, Group group);

	float getAverageMarkBySubject(Subject subject);
}
