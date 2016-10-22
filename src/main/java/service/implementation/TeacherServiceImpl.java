package service.implementation;

import entity.Teacher;
import service.ITeacherService;

import java.util.List;

public class TeacherServiceImpl implements ITeacherService {
	@Override
	public List<Teacher> getListOfTeachers() {
		return null;
	}

	@Override
	public Teacher addTeacher(Teacher teacher) {
		return null;
	}

	@Override
	public boolean updateTeacher(Teacher teacher) {
		return false;
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
}
