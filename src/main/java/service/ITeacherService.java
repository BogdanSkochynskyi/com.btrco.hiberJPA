package service;

import entity.Teacher;
import exceptions.EntityExistsException;
import exceptions.EntityNotFoundException;
import exceptions.InvalidNumberException;
import exceptions.RowsAmountException;

import java.util.List;

public interface ITeacherService {

	List<Teacher> getListOfTeachers() throws RowsAmountException, EntityNotFoundException;

	List<Teacher> getListOfTeachers(int firstRow, int rowAnmount) throws RowsAmountException, EntityNotFoundException;

	Teacher addTeacher(Teacher teacher) throws EntityExistsException;

	boolean updateTeacher(Teacher teacher) throws EntityNotFoundException;

	Teacher getMostExperiencedTeacher() throws EntityNotFoundException;

	Teacher getLessExperiencedTeacher() throws EntityNotFoundException;

	List<Teacher> getTeachersThatHaveExperienceMoreThanYears(int years) throws InvalidNumberException, EntityNotFoundException;

}
