package com.btrco.hiberJPA.service;

import com.btrco.hiberJPA.entity.Teacher;
import com.btrco.hiberJPA.exceptions.EntityExistsException;
import com.btrco.hiberJPA.exceptions.EntityNotFoundException;
import com.btrco.hiberJPA.exceptions.InvalidNumberException;
import com.btrco.hiberJPA.exceptions.RowsAmountException;

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
