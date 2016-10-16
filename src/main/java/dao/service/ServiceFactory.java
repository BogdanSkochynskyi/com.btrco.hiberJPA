package dao.service;

import dao.GroupDaoImpl;
import dao.StudentDaoImpl;
import dao.SubjectDaoImpl;
import dao.TeacherDaoImpl;

public class ServiceFactory {

	private static IGroupDao iGroupDao;
	private static ITeacherDao iTeacherDao;
	private static IStudentDao iStudentDao;
	private static ISubjectDao iSubjectDao;

	private ServiceFactory(){}

	public static IGroupDao getGroupServiceInstance() {
		if (iGroupDao == null) {
			iGroupDao = (IGroupDao) new GroupDaoImpl();
		}
		return iGroupDao;
	}

	public static ITeacherDao getTeacherServiceInstance() {
		if (iTeacherDao == null) {
			iTeacherDao = (ITeacherDao) new TeacherDaoImpl();
		}
		return iTeacherDao;
	}

	public static IStudentDao getStudentServiceInstance() {
		if (iStudentDao == null) {
			iStudentDao = (IStudentDao) new StudentDaoImpl();
		}
		return iStudentDao;
	}

	public static ISubjectDao getSubjectServiceInstance() {
		if (iSubjectDao == null) {
			iSubjectDao = (ISubjectDao) new SubjectDaoImpl();
		}
		return iSubjectDao;
	}
}
