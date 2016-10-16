package service;

import entity.Group;
import entity.Student;
import entity.Subject;
import entity.Teacher;

import java.util.List;

public class UniversityService implements IUniversityService{

	@Override
	public List<Student> getListOfStudents() {
		return null;
	}

	@Override
	public List<Subject> getListOfSubjects() {
		return null;
	}

	@Override
	public List<Group> getListOfGroup() {
		return null;
	}

	@Override
	public List<Teacher> getListOfTeachers() {
		return null;
	}

	@Override
	public Student addStudent(Student student) {
		return null;
	}

	@Override
	public Subject addSubject(Subject subject) {
		return null;
	}

	@Override
	public Group addGroup(Group group) {
		return null;
	}

	@Override
	public Teacher addTeacher(Teacher teacher) {
		return null;
	}

	@Override
	public boolean updateStudent(Student student) {
		return false;
	}

	@Override
	public boolean updateSubject(Subject subject) {
		return false;
	}

	@Override
	public boolean updateGroup(Group group) {
		return false;
	}

	@Override
	public boolean updateTeacher(Teacher teacher) {
		return false;
	}

	@Override
	public List<Student> getStudentsByGroup(Group group) {
		return null;
	}

	@Override
	public List<Group> getGroupsThatStudySubject(Subject subject) {
		return null;
	}

	@Override
	public List<Subject> getSubjectsThatStudyAllGroups() {
		return null;
	}

	@Override
	public Teacher getMostExperiencedTeacher() {
		return null;
	}

	@Override
	public Teacher getLessExperiencedTeacher() {
		return null;
	}

	@Override
	public List<Teacher> getTeachersThatHaveExperienceMoreThanYears(int years) {
		return null;
	}

	@Override
	public List<Subject> getHumanitariumSubjects() {
		return null;
	}

	@Override
	public float getAverageMarkBySubjectInGroup(Subject subject, Group group) {
		return 0;
	}

	@Override
	public float getAverageMarkBySubject(Subject subject) {
		return 0;
	}
}
