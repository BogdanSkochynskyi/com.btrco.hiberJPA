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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component("studentService")
public class StudentServiceImpl implements IStudentService {

	@Autowired
	private IStudentDao studentDao;

	public StudentServiceImpl() {
	}

	@Transactional
	@Override
	public List<Student> getListOfStudents() throws RowsAmountException, EntityNotFoundException {
		List<Student> students = getListOfStudents(Utils.BASIC_FIRST_ROW, Utils.BASIC_ROW_AMOUNT);
		return students;
	}

	@Transactional
	@Override
	public List<Student> getListOfStudents(int firstRow, int rowAmount) throws RowsAmountException, EntityNotFoundException {
		List<Student> students = studentDao.getAll(firstRow, rowAmount);
		return students;
	}

	@Transactional
	@Override
	public Student addStudent(Student student) throws EntityExistsException {
		Student created = studentDao.create(student);
		return created;
	}

	@Transactional
	@Override
	public boolean updateStudent(Student student) throws EntityNotFoundException {
		boolean isStudentUpdated = studentDao.update(student);
		return isStudentUpdated;
	}

	@Transactional
	@Override
	public List<Student> getStudentsByGroup(Group group) throws EntityNotFoundException {
		List<Student> studentsInGroup = studentDao.getListOfStudentsInGroup(group);
		return studentsInGroup;
	}

	@Transactional
	@Override
	public float getAverageMarkBySubjectInGroup(Subject subject, Group group) {
		return 0;
	}

	@Transactional
	@Override
	public float getAverageMarkBySubject(Subject subject) {
		return 0;
	}

	public IStudentDao getStudentDao() {
		return studentDao;
	}

	public void setStudentDao(IStudentDao studentDao) {
		this.studentDao = studentDao;
	}
}
