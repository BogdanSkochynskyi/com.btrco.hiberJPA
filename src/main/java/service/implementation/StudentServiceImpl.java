package service.implementation;

import entity.Group;
import entity.Student;
import entity.Subject;
import service.IStudentService;

import java.util.List;

public class StudentServiceImpl implements IStudentService {

	@Override
	public List<Student> getListOfStudents() {
		return null;
	}

	@Override
	public Student addStudent(Student student) {
		return null;
	}

	@Override
	public boolean updateStudent(Student student) {
		return false;
	}

	@Override
	public List<Student> getStudentsByGroup(Group group) {
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
