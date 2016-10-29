package com.btrco.hiberJPA.service;

import com.btrco.hiberJPA.entity.Group;
import com.btrco.hiberJPA.entity.Student;
import com.btrco.hiberJPA.entity.Subject;
import com.btrco.hiberJPA.exceptions.EntityExistsException;
import com.btrco.hiberJPA.exceptions.EntityNotFoundException;
import com.btrco.hiberJPA.exceptions.RowsAmountException;

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
