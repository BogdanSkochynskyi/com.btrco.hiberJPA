package dao.impl.mysql;

import entity.Group;
import entity.Subject;

import java.util.List;

public interface IGroupDao extends CrudDao<Group> {

	List<Group> getGroupsThatStudySubject(Subject subject);
}
