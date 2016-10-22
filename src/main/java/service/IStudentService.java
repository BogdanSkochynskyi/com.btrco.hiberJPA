package service;

import entity.Group;
import entity.Student;
import entity.Subject;

import java.util.List;

public interface IStudentService {

	List<Student> getListOfStudents();

	Student addStudent(Student student);

	boolean updateStudent(Student student);

	List<Student> getStudentsByGroup(Group group);

	float getAverageMarkBySubjectInGroup(Subject subject, Group group);

	float getAverageMarkBySubject(Subject subject);

}
