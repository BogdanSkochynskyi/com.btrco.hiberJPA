package service;

import entity.Subject;

import java.util.List;

public interface ISubjectService {

	List<Subject> getListOfSubjects();

	List<Subject> getListOfSubjects(int firstRow, int rowAmount);

	Subject addSubject(Subject subject);

	boolean updateSubject(Subject subject);

	List<Subject> getSubjectsThatStudyAllGroups();

	List<Subject> getGumanitariumSubjects();
}
