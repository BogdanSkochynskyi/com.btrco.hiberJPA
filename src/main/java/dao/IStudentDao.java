package dao;

import entity.Group;
import entity.Student;
import exceptions.EntityNotFoundException;

import java.util.List;

public interface IStudentDao extends CrudDao<Student> {

	List<Student> getListOfStudentsInGroup(Group group) throws EntityNotFoundException;
}
