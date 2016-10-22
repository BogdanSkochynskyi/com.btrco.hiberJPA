package service;

import entity.Group;
import entity.Subject;
import exceptions.EntityExistsException;
import exceptions.EntityNotFoundException;
import exceptions.RowsAmountException;

import java.util.List;

public interface IGroupService {

	List<Group> getListOfGroup() throws RowsAmountException, EntityNotFoundException;

	List<Group> getListOfGroup(int firstRow, int rowAmount) throws RowsAmountException, EntityNotFoundException;

	Group addGroup(Group group) throws EntityExistsException;

	boolean updateGroup(Group group) throws EntityNotFoundException;

	List<Group> getGroupsThatStudySubject(Subject subject) throws EntityNotFoundException;

}
