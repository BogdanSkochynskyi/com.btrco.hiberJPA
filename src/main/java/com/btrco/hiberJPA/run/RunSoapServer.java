package com.btrco.hiberJPA.run;

import com.btrco.hiberJPA.service.IGroupService;
import com.btrco.hiberJPA.service.IStudentService;
import com.btrco.hiberJPA.service.implementation.StudentServiceImpl;
import com.btrco.hiberJPA.soap.endpoints.StudentEndpointImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.xml.ws.Endpoint;

public class RunSoapServer {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("app-context.xml");
		IStudentService studentService = (IStudentService) applicationContext.getBean("studentService");
		Endpoint.publish("http://localhost:9999/soap/student", new StudentEndpointImpl(studentService));
	}
}
