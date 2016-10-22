package service;

import entity.Group;
import entity.Subject;

import java.util.List;

public interface IGroupService {

	List<Group> getListOfGroup();

	List<Group> getListOfGroup(int firstRow, int rowAmount);

	Group addGroup(Group group);

	boolean updateGroup(Group group);

	List<Group> getGroupsThatStudySubject(Subject subject);

}
