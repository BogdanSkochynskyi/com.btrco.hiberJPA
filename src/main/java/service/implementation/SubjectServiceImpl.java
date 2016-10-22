package service.implementation;

import dao.impl.mysql.ISubjectDao;
import entity.Subject;
import service.ISubjectService;
import utils.Utils;

import java.util.List;

public class SubjectServiceImpl implements ISubjectService {

	private ISubjectDao subjectDao;

	public SubjectServiceImpl(ISubjectDao subjectDao) {
		this.subjectDao = subjectDao;
	}

	@Override
	public List<Subject> getListOfSubjects() {
		List<Subject> subjects = getListOfSubjects(Utils.BASIC_FIRST_ROW, Utils.BASIC_ROW_AMOUNT);
		return subjects;
	}

	@Override
	public List<Subject> getListOfSubjects(int firstRow, int rowAmount) {
		List<Subject> subjects = subjectDao.getAll(firstRow, rowAmount);
		return subjects;
	}

	@Override
	public Subject addSubject(Subject subject) {
		Subject created = subjectDao.create(subject);
		return created;
	}

	@Override
	public boolean updateSubject(Subject subject) {
		boolean isSubjectUpdated = subjectDao.update(subject);
		return isSubjectUpdated;
	}

	@Override
	public List<Subject> getSubjectsThatStudyAllGroups() {
		return null;
	}

	@Override
	public List<Subject> getGumanitariumSubjects() {
		List<Subject> gumanitariumSubjects = subjectDao.getGumanitariumSubjects();
		return gumanitariumSubjects;
	}
}
