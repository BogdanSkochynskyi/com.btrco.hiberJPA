package utils;

import entity.Group;
import entity.Student;
import entity.Subject;
import entity.Teacher;

public class Utils {
	
	public static final int BASIC_FIRST_ROW = 1;
	public static final int BASIC_ROW_AMOUNT = 10;

	public static void copyGroup(Group from, Group to){
		to.setName(from.getName());
	}

	public static void copyStudent(Student from, Student to){
		to.setName(from.getName());
		to.setGroup(from.getGroup());
	}

	public static void copySubject(Subject from, Subject to) {
		to.setName(from.getName());
		to.setDescription(from.getDescription());
	}

	public static void copyTeacher(Teacher from, Teacher to) {
		to.setName(from.getName());
		to.setExperience(from.getExperience());
	}
}
