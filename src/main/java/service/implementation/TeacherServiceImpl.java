package service.implementation;

import dao.impl.mysql.ITeacherDao;
import entity.Teacher;
import service.ITeacherService;
import utils.Utils;

import java.util.List;

public class TeacherServiceImpl implements ITeacherService {

	private ITeacherDao teacherDao;

	public TeacherServiceImpl(ITeacherDao teacherDao) {
		this.teacherDao = teacherDao;
	}

	@Override
	public List<Teacher> getListOfTeachers() {
		List<Teacher> teachers = getListOfTeachers(Utils.BASIC_FIRST_ROW, Utils.BASIC_ROW_AMOUNT);
		return teachers;
	}

	@Override
	public List<Teacher> getListOfTeachers(int firstRow, int rowAmount) {
		List<Teacher> teachers = teacherDao.getAll(firstRow, rowAmount);
		return teachers;
	}

	@Override
	public Teacher addTeacher(Teacher teacher) {
		Teacher created = teacherDao.create(teacher);
		return created;
	}

	@Override
	public boolean updateTeacher(Teacher teacher) {
		boolean isTeacherUpdated = teacherDao.update(teacher);
		return isTeacherUpdated;
	}

	@Override
	public Teacher getMostExperiencedTeacher() {
		Teacher mostExperiencedTeacher = teacherDao.getMostExperienceTeacher();
		return mostExperiencedTeacher;
	}

	@Override
	public Teacher getLessExperiencedTeacher() {
		Teacher lessExperiencedTeacher = teacherDao.getLessExperienceTeacher();
		return lessExperiencedTeacher;
	}

	@Override
	public List<Teacher> getTeachersThatHaveExperienceMoreThanYears(int years) {
		List<Teacher> teachersThatHaveExperiencedMoreThanYears = teacherDao.getTeachersWithMoreThanThreeYearsExperience(years);
		return teachersThatHaveExperiencedMoreThanYears;
	}
}
