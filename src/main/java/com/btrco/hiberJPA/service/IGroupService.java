package com.btrco.hiberJPA.service;

import com.btrco.hiberJPA.entity.Group;
import com.btrco.hiberJPA.entity.Subject;
import com.btrco.hiberJPA.exceptions.EntityExistsException;
import com.btrco.hiberJPA.exceptions.EntityNotFoundException;
import com.btrco.hiberJPA.exceptions.InvalidIdException;
import com.btrco.hiberJPA.exceptions.RowsAmountException;

import java.util.List;

public interface IGroupService {

	List<Group> getListOfGroup() throws RowsAmountException, EntityNotFoundException;

	List<Group> getListOfGroup(int firstRow, int rowAmount) throws RowsAmountException, EntityNotFoundException;

	Group addGroup(Group group) throws EntityExistsException;

	boolean updateGroup(Group group) throws EntityNotFoundException;

	List<Group> getGroupsThatStudySubject(Subject subject) throws EntityNotFoundException;

	Group getGroupByName(String oldGroupName);

	Group getGroupById(int id) throws InvalidIdException, EntityNotFoundException;
}
