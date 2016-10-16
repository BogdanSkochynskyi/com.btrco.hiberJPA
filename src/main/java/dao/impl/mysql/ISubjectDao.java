package dao.impl.mysql;

import entity.Subject;

import java.util.List;

public interface ISubjectDao extends CrudDao<Subject> {

	List<Subject> getGumanitariumSubjects();

}
