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

@WebServlet(urlPatterns = {"/group/add"})
public class AddGroupServlet extends HttpServlet {

	private ApplicationContext applicationContext;
	private IGroupService groupService;

	@Override
	public void init() throws ServletException {
		this.applicationContext = (ApplicationContext) getServletContext().getAttribute("spring-context");
		this.groupService = (IGroupService) this.applicationContext.getBean("groupService");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/pages/group/groupAdd.jsp").forward(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("groupName");

		if (name == null || name.equals("")) {
			req.setAttribute("errorTitle", "Add group error");
			req.setAttribute("errorMessage", "Name is empty. PLease, write name of group.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req,resp);
		} else {
			Group group = new Group(name);
			try {
				Group created = this.groupService.addGroup(group);
				resp.sendRedirect("info?id=" + created.getId());
			} catch (EntityExistsException e) {
				req.setAttribute("errorTitle", e.getClass().getSimpleName());
				req.setAttribute("errorMessage", e.getMessage());
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req,resp);
			}
		}
	}
}
