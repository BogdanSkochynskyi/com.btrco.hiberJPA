package com.btrco.hiberJPA.soap.endpoints;

import com.btrco.hiberJPA.dto.StudentDTO;
import com.btrco.hiberJPA.dto.StudentsList;
import com.btrco.hiberJPA.entity.Student;
import com.btrco.hiberJPA.exceptions.EntityExistsException;
import com.btrco.hiberJPA.exceptions.EntityNotFoundException;
import com.btrco.hiberJPA.exceptions.InvalidIdException;
import com.btrco.hiberJPA.exceptions.RowsAmountException;
import com.btrco.hiberJPA.service.IStudentService;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface IStudentEndpoint {

	@WebMethod
	StudentDTO addStudent(StudentDTO student) throws EntityExistsException;

	@WebMethod
	boolean updateStudent(StudentDTO student) throws EntityNotFoundException;

	@WebMethod
	StudentDTO getStudentById(int id) throws InvalidIdException, EntityNotFoundException;

	@WebMethod
	StudentsList getListOfStudents(int firstRow, int rowAmount) throws RowsAmountException, EntityNotFoundException;
}
