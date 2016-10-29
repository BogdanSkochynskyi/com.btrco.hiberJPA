package com.btrco.hiberJPA.dao;

import com.btrco.hiberJPA.entity.Teacher;
import com.btrco.hiberJPA.exceptions.EntityNotFoundException;
import com.btrco.hiberJPA.exceptions.InvalidNumberException;

import java.util.List;

public interface ITeacherDao extends CrudDao<Teacher> {

	Teacher getMostExperienceTeacher() throws EntityNotFoundException;

	Teacher getLessExperienceTeacher() throws EntityNotFoundException;

	List<Teacher> getTeachersWithMoreThanYearsExperience(int experience) throws EntityNotFoundException, InvalidNumberException;

}
