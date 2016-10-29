package com.btrco.hiberJPA.dao;

import com.btrco.hiberJPA.entity.Group;
import com.btrco.hiberJPA.entity.Student;
import com.btrco.hiberJPA.exceptions.EntityNotFoundException;

import java.util.List;

public interface IStudentDao extends CrudDao<Student> {

	List<Student> getListOfStudentsInGroup(Group group) throws EntityNotFoundException;
}
