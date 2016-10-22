package dao;

import entity.Group;
import entity.Subject;
import exceptions.EntityNotFoundException;

import java.util.List;

public interface IGroupDao extends CrudDao<Group> {

	List<Group> getGroupsThatStudySubject(Subject subject) throws EntityNotFoundException;
}
