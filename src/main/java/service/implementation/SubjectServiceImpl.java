package service.implementation;

import dao.ISubjectDao;
import entity.Subject;
import exceptions.EntityExistsException;
import exceptions.EntityNotFoundException;
import exceptions.RowsAmountException;
import service.ISubjectService;
import utils.Utils;

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
