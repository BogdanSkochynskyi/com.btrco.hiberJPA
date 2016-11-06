package com.btrco.hiberJPA.servlets.groupServlets;

import com.btrco.hiberJPA.entity.Group;
import com.btrco.hiberJPA.exceptions.EntityExistsException;
import com.btrco.hiberJPA.service.IGroupService;
import com.btrco.hiberJPA.service.implementation.GroupServiceImpl;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet
public class GetGroupsThatStudySubjectServlet extends HttpServlet {

	private ApplicationContext applicationContext;
	private IGroupService groupService;

	@Override
	public void init() throws ServletException {
		this.applicationContext = (ApplicationContext) getServletContext().getAttribute("spring-context");
		this.groupService = this.applicationContext.getBean(GroupServiceImpl.class);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		req.getRequestDispatcher("/WEB-INF/pages/addGroup.jsp").forward(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String subjectName = req.getParameter("subjectName");

		if (subjectName == null || subjectName.equals("")) {
			req.setAttribute("errorTitle", "Subject error");
			req.setAttribute("errorMessage", "Subject name is empty. PLease, write name of subject.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req,resp);
		}
	}
}
