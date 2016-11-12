package com.btrco.hiberjpa.soap.test;

import com.btrco.hiberjpa.soap.endpoints.*;

public class RunClient {

	public static void main(String[] args) {
		IStudentEndpoint service = new StudentEndpointImplService().getStudentEndpointImplPort();

		try {
			StudentsList list = service.getListOfStudents(1,5);
			list.getStudents().stream().forEach(System.out::println);
		} catch (EntityNotFoundException_Exception e) {
			e.printStackTrace();
		} catch (RowsAmountException_Exception e) {
			e.printStackTrace();
		}
	}
}
