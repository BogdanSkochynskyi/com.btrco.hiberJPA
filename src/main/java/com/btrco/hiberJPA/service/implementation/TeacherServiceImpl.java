package com.btrco.hiberJPA.service.implementation;

import com.btrco.hiberJPA.dao.ITeacherDao;
import com.btrco.hiberJPA.entity.Teacher;
import com.btrco.hiberJPA.exceptions.EntityExistsException;
import com.btrco.hiberJPA.exceptions.EntityNotFoundException;
import com.btrco.hiberJPA.exceptions.InvalidNumberException;
import com.btrco.hiberJPA.exceptions.RowsAmountException;
import com.btrco.hiberJPA.service.ITeacherService;
import com.btrco.hiberJPA.utils.Utils;

import java.util.List;

public class TeacherServiceImpl implements ITeacherService {

	private ITeacherDao teacherDao;

	public TeacherServiceImpl(ITeacherDao teacherDao) {
		this.teacherDao = teacherDao;
	}

	@Override
	public List<Teacher> getListOfTeachers() throws RowsAmountException, EntityNotFoundException {
		List<Teacher> teachers = getListOfTeachers(Utils.BASIC_FIRST_ROW, Utils.BASIC_ROW_AMOUNT);
		return teachers;
	}

	@Override
	public List<Teacher> getListOfTeachers(int firstRow, int rowAmount) throws RowsAmountException, EntityNotFoundException {
		List<Teacher> teachers = teacherDao.getAll(firstRow, rowAmount);
		return teachers;
	}

	@Override
	public Teacher addTeacher(Teacher teacher) throws EntityExistsException {
		return teacherDao.create(teacher);
	}

	@Override
	public boolean updateTeacher(Teacher teacher) throws EntityNotFoundException {
		return teacherDao.update(teacher);
	}

	@Override
	public Teacher getMostExperiencedTeacher() throws EntityNotFoundException {
		Teacher mostExperiencedTeacher = teacherDao.getMostExperienceTeacher();
		return mostExperiencedTeacher;
	}

	@Override
	public Teacher getLessExperiencedTeacher() throws EntityNotFoundException {
		Teacher lessExperiencedTeacher = teacherDao.getLessExperienceTeacher();
		return lessExperiencedTeacher;
	}

	@Override
	public List<Teacher> getTeachersThatHaveExperienceMoreThanYears(int years) throws InvalidNumberException, EntityNotFoundException {
		List<Teacher> teachersThatHaveExperiencedMoreThanYears = teacherDao.getTeachersWithMoreThanYearsExperience(years);
		return teachersThatHaveExperiencedMoreThanYears;
	}
}
