package com.btrco.hiberJPA.tets.jaxb;

import com.btrco.hiberJPA.dto.StudentDTO;
import com.btrco.hiberJPA.dto.StudentsList;

import javax.xml.bind.JAXB;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;

public class TestJaxB {

	public static void main(String[] args) {

		StudentsList studentsList = new StudentsList();
		studentsList.setStudents(Arrays.asList(new StudentDTO("one"), new StudentDTO("two"), new StudentDTO("three")));

		StringWriter writer = new StringWriter();
		JAXB.marshal(studentsList, writer);

		String string = writer.toString();

		StudentsList studentsList1 = JAXB.unmarshal(new StringReader(string), StudentsList.class);
		System.out.println(studentsList1);
	}
}
