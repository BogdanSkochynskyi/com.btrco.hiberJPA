package com.btrco.hiberJPA.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "student")
public class StudentDTO {

	private String name;

	public StudentDTO() {
	}

	public StudentDTO(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "StudentDTO{" +
				"name='" + name + '\'' +
				'}';
	}
}
