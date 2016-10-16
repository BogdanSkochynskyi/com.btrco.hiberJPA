package dao.impl.mysql;

import entity.Teacher;

import java.util.List;

public interface ITeacherDao extends CrudDao<Teacher> {

	Teacher getMostExperienceTeacher();

	Teacher getLessExperienceTeacher();

	List<Teacher> getTeachersWithMoreThanThreeYearsExperience(int experience);

}
