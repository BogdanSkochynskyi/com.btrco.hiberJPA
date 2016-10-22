package service.implementation;

import dao.IGroupDao;
import entity.Group;
import entity.Subject;
import exceptions.EntityExistsException;
import exceptions.EntityNotFoundException;
import exceptions.RowsAmountException;
import service.IGroupService;
import service.annotations.ForInject;
import utils.Utils;
import utils.Validator;

import java.util.List;

public class GroupServiceImpl implements IGroupService {

	@ForInject
	private IGroupDao groupDao;

	@ForInject
	private Validator validator;

	public GroupServiceImpl(IGroupDao groupDao) {
		this.groupDao = groupDao;
	}

	@Override
	public List<Group> getListOfGroup() throws RowsAmountException, EntityNotFoundException {
		List<Group> groups = getListOfGroup(Utils.BASIC_FIRST_ROW, Utils.BASIC_ROW_AMOUNT);
		return groups;
	}

	@Override
	public List<Group> getListOfGroup(int firstRow, int rowAmount) throws RowsAmountException, EntityNotFoundException {
		List<Group> groups = groupDao.getAll(firstRow, rowAmount);
		return groups;
	}

	@Override
	public Group addGroup(Group group) throws EntityExistsException {
		Group created = groupDao.create(group);
		return created;
	}

	@Override
	public boolean updateGroup(Group group) throws EntityNotFoundException {
		boolean isGroupUpdated = groupDao.update(group);
		return isGroupUpdated;
	}

	@Override
	public List<Group> getGroupsThatStudySubject(Subject subject) throws EntityNotFoundException {
		List<Group> groupList = groupDao.getGroupsThatStudySubject(subject);  //Not DAO? rewrite by code
		return groupList;
	}
}
