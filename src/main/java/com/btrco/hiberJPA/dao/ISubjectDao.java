package com.btrco.hiberJPA.dao;

import com.btrco.hiberJPA.entity.Subject;
import com.btrco.hiberJPA.exceptions.EntityNotFoundException;

import java.util.List;

public interface ISubjectDao extends CrudDao<Subject> {

	List<Subject> getGumanitariumSubjects() throws EntityNotFoundException;

}
