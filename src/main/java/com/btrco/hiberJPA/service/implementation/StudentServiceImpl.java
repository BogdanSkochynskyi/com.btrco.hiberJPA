package com.btrco.hiberJPA.service.implementation;

import com.btrco.hiberJPA.dao.IStudentDao;
import com.btrco.hiberJPA.entity.Group;
import com.btrco.hiberJPA.entity.Student;
import com.btrco.hiberJPA.entity.Subject;
import com.btrco.hiberJPA.exceptions.EntityExistsException;
import com.btrco.hiberJPA.exceptions.EntityNotFoundException;
import com.btrco.hiberJPA.exceptions.RowsAmountException;
import com.btrco.hiberJPA.service.IStudentService;
import com.btrco.hiberJPA.utils.Utils;

import java.util.List;


public class StudentServiceImpl implements IStudentService {

	private IStudentDao studentDao;

	public StudentServiceImpl(IStudentDao studentDao) {
		this.studentDao = studentDao;
	}

	@Override
	public List<Student> getListOfStudents() throws RowsAmountException, EntityNotFoundException {
		List<Student> students = getListOfStudents(Utils.BASIC_FIRST_ROW, Utils.BASIC_ROW_AMOUNT);
		return students;
	}

	@Override
	public List<Student> getListOfStudents(int firstRow, int rowAmount) throws RowsAmountException, EntityNotFoundException {
		List<Student> students = studentDao.getAll(firstRow, rowAmount);
		return students;
	}

	@Override
	public Student addStudent(Student student) throws EntityExistsException {
		Student created = studentDao.create(student);
		return created;
	}

	@Override
	public boolean updateStudent(Student student) throws EntityNotFoundException {
		boolean isStudentUpdated = studentDao.update(student);
		return isStudentUpdated;
	}

	@Override
	public List<Student> getStudentsByGroup(Group group) throws EntityNotFoundException {
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
