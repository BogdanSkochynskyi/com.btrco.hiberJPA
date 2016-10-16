package dao.service;

import entity.Teacher;

import java.util.List;

public interface ITeacherDao extends CrudDao<Teacher> {

	Teacher getMostExperienceTeacher();

	Teacher getLessExperienceTeacher();

	List<Teacher> getTeachersWithMoreThanThreeYearsExperience();

}
