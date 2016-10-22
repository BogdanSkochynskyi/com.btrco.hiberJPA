package service.implementation;

import dao.impl.mysql.IGroupDao;
import entity.Group;
import entity.Subject;
import service.IGroupService;
import service.annotations.ForInject;
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
	public List<Group> getListOfGroup() {
		return null;
	}

	@Override
	public Group addGroup(Group group) {
		if (!validator.isValid(group)) {
			return null;
		}

		Group created = groupDao.create(group);
		return created;
	}

	@Override
	public boolean updateGroup(Group group) {
		return false;
	}

	@Override
	public List<Group> getGroupsThatStudySubject(Subject subject) {
		return null;
	}
}
