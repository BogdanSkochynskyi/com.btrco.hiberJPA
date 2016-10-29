package com.btrco.hiberJPA.service;

import com.btrco.hiberJPA.entity.Subject;
import com.btrco.hiberJPA.exceptions.EntityExistsException;
import com.btrco.hiberJPA.exceptions.EntityNotFoundException;
import com.btrco.hiberJPA.exceptions.RowsAmountException;

import java.util.List;

public interface ISubjectService {

	List<Subject> getListOfSubjects() throws RowsAmountException, EntityNotFoundException;

	List<Subject> getListOfSubjects(int firstRow, int rowAmount) throws RowsAmountException, EntityNotFoundException;

	Subject addSubject(Subject subject) throws EntityExistsException;

	boolean updateSubject(Subject subject) throws EntityNotFoundException;

	List<Subject> getSubjectsThatStudyAllGroups();

	List<Subject> getGumanitariumSubjects() throws EntityNotFoundException;
}
