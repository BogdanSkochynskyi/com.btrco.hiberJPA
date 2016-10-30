package com.btrco.hiberJPA.service.implementation;

import com.btrco.hiberJPA.dao.IGroupDao;
import com.btrco.hiberJPA.entity.Group;
import com.btrco.hiberJPA.entity.Subject;
import com.btrco.hiberJPA.exceptions.EntityExistsException;
import com.btrco.hiberJPA.exceptions.EntityNotFoundException;
import com.btrco.hiberJPA.exceptions.RowsAmountException;
import com.btrco.hiberJPA.service.IGroupService;
import com.btrco.hiberJPA.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component("groupService")
public class GroupServiceImpl implements IGroupService {

	@Autowired
	private IGroupDao groupDao;

	public GroupServiceImpl() {

	}

	@Override
	@Transactional
	public List<Group> getListOfGroup() throws RowsAmountException, EntityNotFoundException {
		List<Group> groups = getListOfGroup(Utils.BASIC_FIRST_ROW, Utils.BASIC_ROW_AMOUNT);
		return groups;
	}

	@Override
	@Transactional
	public List<Group> getListOfGroup(int firstRow, int rowAmount) throws RowsAmountException, EntityNotFoundException {
		List<Group> groups = groupDao.getAll(firstRow, rowAmount);
		return groups;
	}

	@Override
	@Transactional
	public Group addGroup(Group group) throws EntityExistsException {
		Group created = groupDao.create(group);
		return created;
	}

	@Override
	@Transactional
	public boolean updateGroup(Group group) throws EntityNotFoundException {
		boolean isGroupUpdated = groupDao.update(group);
		return isGroupUpdated;
	}

	@Override
	@Transactional
	public List<Group> getGroupsThatStudySubject(Subject subject) throws EntityNotFoundException {
		List<Group> groupList = groupDao.getGroupsThatStudySubject(subject);  //Not DAO? rewrite by code
		return groupList;
	}

	public IGroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(IGroupDao groupDao) {
		this.groupDao = groupDao;
	}
}
