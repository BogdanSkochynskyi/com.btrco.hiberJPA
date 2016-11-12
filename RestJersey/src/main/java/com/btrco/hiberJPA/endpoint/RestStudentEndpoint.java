package com.btrco.hiberJPA.endpoint;

import com.btrco.hiberJPA.dto.StudentDTO;
import com.btrco.hiberJPA.dto.StudentsList;
import com.btrco.hiberJPA.entity.Student;
import com.btrco.hiberJPA.exceptions.EntityExistsException;
import com.btrco.hiberJPA.exceptions.EntityNotFoundException;
import com.btrco.hiberJPA.exceptions.InvalidIdException;
import com.btrco.hiberJPA.exceptions.RowsAmountException;
import com.btrco.hiberJPA.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/student")
public class RestStudentEndpoint implements com.btrco.hiberJPA.soap.endpoints.IStudentEndpoint {

	@Context
	private ServletContext servletContext;

	private IStudentService studentService;

	public RestStudentEndpoint() {
	}

	@PostConstruct
	public void init() {
		ApplicationContext app = new ClassPathXmlApplicationContext("app-context.xml");
		studentService = app.getBean(IStudentService.class);
	}

	@Path("/hello")
	@GET
	public String hello(){
		return "hello";
	}

	@Override
	@POST
	@Path("/add")
	@Consumes("application/json")
	@Produces("application/json")
	public StudentDTO addStudent(StudentDTO student) throws EntityExistsException {
		Student added = new Student(student.getName(), null);
		return new StudentDTO(studentService.addStudent(added).getName());
	}

	@Override
	public boolean updateStudent(StudentDTO student) throws EntityNotFoundException {
		return false;
	}

	@Override // student/info/23
	@Path("/info/{id}")
	@GET
	@Produces("application/json")
	public StudentDTO getStudentById(@PathParam("id") int id) throws InvalidIdException, EntityNotFoundException {
		Student student = studentService.getStudentById(id);
		return new StudentDTO(student.getName());
	}

	@Override
	@GET
	@Path("/all")
	@Consumes("application/json")
	@Produces("application/json")
	public StudentsList getListOfStudents(@QueryParam("firstRow") int firstRow, @QueryParam("rowAmount") int rowAmount) throws RowsAmountException, EntityNotFoundException {
		List<Student> students = studentService.getListOfStudents(firstRow, rowAmount);
		List<StudentDTO> studentDTOs = students.stream().map(student -> new StudentDTO(student.getName())).collect(Collectors.toList());

		return new StudentsList(studentDTOs);
	}
}
