package com.btrco.hiberJPA.service.implementation;

import com.btrco.hiberJPA.dao.IGroupDao;
import com.btrco.hiberJPA.entity.Group;
import com.btrco.hiberJPA.entity.Subject;
import com.btrco.hiberJPA.exceptions.EntityExistsException;
import com.btrco.hiberJPA.exceptions.EntityNotFoundException;
import com.btrco.hiberJPA.exceptions.RowsAmountException;
import com.btrco.hiberJPA.service.IGroupService;
import com.btrco.hiberJPA.service.annotations.ForInject;
import com.btrco.hiberJPA.utils.Utils;
import com.btrco.hiberJPA.utils.Validator;

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
