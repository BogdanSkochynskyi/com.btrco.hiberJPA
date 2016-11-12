package com.btrco.hiberJPA.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "studentsList")
public class StudentsList {

	private List<StudentDTO> students;

	public StudentsList() {
	}

	public StudentsList(List<StudentDTO> students) {
		this.students = students;
	}

	@XmlElement(name = "students")
	public List<StudentDTO> getStudents() {
		return students;
	}

	public void setStudents(List<StudentDTO> students) {
		this.students = students;
	}
}
