package service.implementation;

import dao.impl.mysql.IStudentDao;
import entity.Group;
import entity.Student;
import entity.Subject;
import service.IStudentService;
import utils.Utils;

import java.util.List;

public class StudentServiceImpl implements IStudentService {

	private IStudentDao studentDao;

	public StudentServiceImpl(IStudentDao studentDao) {
		this.studentDao = studentDao;
	}

	@Override
	public List<Student> getListOfStudents() {
		List<Student> students = getListOfStudents(Utils.BASIC_FIRST_ROW, Utils.BASIC_ROW_AMOUNT);
		return students;
	}

	@Override
	public List<Student> getListOfStudents(int firstRow, int rowAmount) {
		List<Student> students = studentDao.getAll(firstRow, rowAmount);
		return students;
	}

	@Override
	public Student addStudent(Student student) {
		Student created = studentDao.create(student);
		return created;
	}

	@Override
	public boolean updateStudent(Student student) {
		boolean isStudentUpdated = studentDao.update(student);
		return isStudentUpdated;
	}

	@Override
	public List<Student> getStudentsByGroup(Group group) {
		List<Student> studentsInGroup = studentDao.getListOfStudentsInGroup(group);
		return studentsInGroup;
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
