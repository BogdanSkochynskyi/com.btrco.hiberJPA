package dao;

import entity.Teacher;
import exceptions.EntityExistsException;
import exceptions.EntityNotFoundException;
import exceptions.InvalidNumberException;
import exceptions.RowsAmountException;

import java.util.List;

public interface ITeacherDao extends CrudDao<Teacher> {

	Teacher getMostExperienceTeacher() throws EntityNotFoundException;

	Teacher getLessExperienceTeacher() throws EntityNotFoundException;

	List<Teacher> getTeachersWithMoreThanYearsExperience(int experience) throws EntityNotFoundException, InvalidNumberException;

}
