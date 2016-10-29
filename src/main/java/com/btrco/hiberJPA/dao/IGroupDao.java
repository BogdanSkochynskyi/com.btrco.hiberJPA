package com.btrco.hiberJPA.dao;

import com.btrco.hiberJPA.entity.Group;
import com.btrco.hiberJPA.entity.Subject;
import com.btrco.hiberJPA.exceptions.EntityNotFoundException;

import java.util.List;

public interface IGroupDao extends CrudDao<Group> {

	List<Group> getGroupsThatStudySubject(Subject subject) throws EntityNotFoundException;
}
