package service.implementation;

import dao.ITeacherDao;
import entity.Teacher;
import exceptions.EntityExistsException;
import exceptions.EntityNotFoundException;
import exceptions.InvalidNumberException;
import exceptions.RowsAmountException;
import service.ITeacherService;
import utils.Utils;

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
