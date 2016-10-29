package com.btrco.hiberJPA.service.implementation;

import com.btrco.hiberJPA.dao.ISubjectDao;
import com.btrco.hiberJPA.entity.Subject;
import com.btrco.hiberJPA.exceptions.EntityExistsException;
import com.btrco.hiberJPA.exceptions.EntityNotFoundException;
import com.btrco.hiberJPA.exceptions.RowsAmountException;
import com.btrco.hiberJPA.service.ISubjectService;
import com.btrco.hiberJPA.utils.Utils;

import java.util.List;

public class SubjectServiceImpl implements ISubjectService {

	private ISubjectDao subjectDao;

	public SubjectServiceImpl(ISubjectDao subjectDao) {
		this.subjectDao = subjectDao;
	}

	@Override
	public List<Subject> getListOfSubjects() throws RowsAmountException, EntityNotFoundException {
		List<Subject> subjects = getListOfSubjects(Utils.BASIC_FIRST_ROW, Utils.BASIC_ROW_AMOUNT);
		return subjects;
	}

	@Override
	public List<Subject> getListOfSubjects(int firstRow, int rowAmount) throws RowsAmountException, EntityNotFoundException {
		List<Subject> subjects = subjectDao.getAll(firstRow, rowAmount);
		return subjects;
	}

	@Override
	public Subject addSubject(Subject subject) throws EntityExistsException {
		Subject created = subjectDao.create(subject);
		return created;
	}

	@Override
	public boolean updateSubject(Subject subject) throws EntityNotFoundException {
		boolean isSubjectUpdated = subjectDao.update(subject);
		return isSubjectUpdated;
	}

	@Override
	public List<Subject> getSubjectsThatStudyAllGroups() {
		return null;
	}

	@Override
	public List<Subject> getGumanitariumSubjects() throws EntityNotFoundException {
		List<Subject> gumanitariumSubjects = subjectDao.getGumanitariumSubjects();
		return gumanitariumSubjects;
	}
}
