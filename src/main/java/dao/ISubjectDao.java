package dao;

import entity.Subject;
import exceptions.EntityNotFoundException;

import java.util.List;

public interface ISubjectDao extends CrudDao<Subject> {

	List<Subject> getGumanitariumSubjects() throws EntityNotFoundException;

}
