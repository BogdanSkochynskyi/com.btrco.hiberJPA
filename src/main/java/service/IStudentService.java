package service;

import entity.Group;
import entity.Student;
import entity.Subject;
import exceptions.EntityExistsException;
import exceptions.EntityNotFoundException;
import exceptions.RowsAmountException;

import java.util.List;

public interface IStudentService {

	List<Student> getListOfStudents() throws RowsAmountException, EntityNotFoundException;

	List<Student> getListOfStudents(int firstRow, int rowAmount) throws RowsAmountException, EntityNotFoundException;

	Student addStudent(Student student) throws EntityExistsException;

	boolean updateStudent(Student student) throws EntityNotFoundException;

	List<Student> getStudentsByGroup(Group group) throws EntityNotFoundException;

	float getAverageMarkBySubjectInGroup(Subject subject, Group group);

	float getAverageMarkBySubject(Subject subject);

}
