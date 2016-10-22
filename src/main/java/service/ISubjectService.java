package service;

import entity.Subject;
import exceptions.EntityExistsException;
import exceptions.EntityNotFoundException;
import exceptions.RowsAmountException;

import java.util.List;

public interface ISubjectService {

	List<Subject> getListOfSubjects() throws RowsAmountException, EntityNotFoundException;

	List<Subject> getListOfSubjects(int firstRow, int rowAmount) throws RowsAmountException, EntityNotFoundException;

	Subject addSubject(Subject subject) throws EntityExistsException;

	boolean updateSubject(Subject subject) throws EntityNotFoundException;

	List<Subject> getSubjectsThatStudyAllGroups();

	List<Subject> getGumanitariumSubjects() throws EntityNotFoundException;
}
