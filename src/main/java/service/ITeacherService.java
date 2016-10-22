package service;

import entity.Teacher;

import java.util.List;

public interface ITeacherService {

	List<Teacher> getListOfTeachers();

	List<Teacher> getListOfTeachers(int firstRow, int rowAnmount);

	Teacher addTeacher(Teacher teacher);

	boolean updateTeacher(Teacher teacher);

	Teacher getMostExperiencedTeacher();

	Teacher getLessExperiencedTeacher();

	List<Teacher> getTeachersThatHaveExperienceMoreThanYears(int years);

}
