package com.btrco.hiberJPA.soap.endpoints;

import com.btrco.hiberJPA.dto.StudentDTO;
import com.btrco.hiberJPA.dto.StudentsList;
import com.btrco.hiberJPA.entity.Student;
import com.btrco.hiberJPA.exceptions.EntityExistsException;
import com.btrco.hiberJPA.exceptions.EntityNotFoundException;
import com.btrco.hiberJPA.exceptions.InvalidIdException;
import com.btrco.hiberJPA.exceptions.RowsAmountException;
import com.btrco.hiberJPA.service.IStudentService;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

@WebService(endpointInterface = "com.btrco.hiberJPA.soap.endpoints.IStudentEndpoint")
public class StudentEndpointImpl implements IStudentEndpoint {

	private IStudentService studentService;

	public StudentEndpointImpl() {
	}

	public StudentEndpointImpl(IStudentService studentService) {
		this.studentService = studentService;
	}

	@Override
	public StudentDTO addStudent(StudentDTO student) throws EntityExistsException {
		Student student1 = new Student(student.getName(), null);
		return new StudentDTO(studentService.addStudent(student1).getName());
	}

	@Override
	public boolean updateStudent(StudentDTO student) throws EntityNotFoundException {
		Student student1 = new Student(student.getName(), null);
		return studentService.updateStudent(student1);
	}

	@Override
	public StudentDTO getStudentById(int id) throws InvalidIdException, EntityNotFoundException {
		Student student = studentService.getStudentById(id);
		return new StudentDTO(student.getName());
	}

	@Override
	public StudentsList getListOfStudents(int firstRow, int rowAmount) throws RowsAmountException, EntityNotFoundException {

		List<Student> students = studentService.getListOfStudents(firstRow, rowAmount);
		List<StudentDTO> studentDTOs = new ArrayList<>();

		for (Student student : students) {
			studentDTOs.add(new StudentDTO(student.getName()));
		}

		return new StudentsList(studentDTOs);
	}
}
