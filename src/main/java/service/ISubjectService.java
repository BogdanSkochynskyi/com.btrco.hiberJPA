package service;

import entity.Subject;

import java.util.List;

public interface ISubjectService {

	List<Subject> getListOfSubjects();

	Subject addSubject(Subject subject);

	boolean updateSubject(Subject subject);

	List<Subject> getSubjectsThatStudyAllGroups();

	List<Subject> getHumanitariumSubjects();
}
