package utils;

import entity.Group;
import entity.Student;
import entity.Subject;
import entity.Teacher;

public class Utils {

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
