package dao.impl.mysql;

import entity.Group;
import entity.Student;

import java.util.List;

public interface IStudentDao extends CrudDao<Student> {

	List<Student> getListOfStudentsInGroup(Group group);
}
