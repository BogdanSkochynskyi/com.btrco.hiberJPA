package com.btrco.hiberJPA.servlets.groupServlets;

import com.btrco.hiberJPA.entity.Group;
import com.btrco.hiberJPA.exceptions.EntityExistsException;
import com.btrco.hiberJPA.exceptions.EntityNotFoundException;
import com.btrco.hiberJPA.exceptions.InvalidIdException;
import com.btrco.hiberJPA.service.IGroupService;
import com.btrco.hiberJPA.service.implementation.GroupServiceImpl;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/group/update"})
public class UpdateGroupServlet extends HttpServlet {

	private ApplicationContext applicationContext;
	private IGroupService groupService;

	@Override
	public void init() throws ServletException {
		this.applicationContext = (ApplicationContext) getServletContext().getAttribute("spring-context");
		this.groupService = (IGroupService) this.applicationContext.getBean("groupService");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		req.setAttribute("id", id);
		req.getRequestDispatcher("/WEB-INF/pages/group/groupUpdate.jsp").forward(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String newGroupName = req.getParameter("newGroupName");
		String idStr = req.getParameter("id");


		if (newGroupName == null || newGroupName.equals("") || idStr == null || idStr.equals("")) {
			req.setAttribute("errorTitle", "Update group error");
			req.setAttribute("errorMessage", "Id is empty.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req,resp);
		} else {
			int id = Integer.parseInt(idStr);
			Group group = null;
			try {
				group = groupService.getGroupById(id);
			} catch (InvalidIdException | EntityNotFoundException e) {
				e.printStackTrace();
			}

			if (group != null) {
				group.setName(newGroupName);
				try {
					groupService.updateGroup(group);
					resp.sendRedirect("info?id=" + group.getId());
				} catch (EntityNotFoundException e) {
					req.setAttribute("errorTitle", e.getClass().getSimpleName());
					req.setAttribute("errorMessage", e.getMessage());
					req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req,resp);
				}
			} else {
				req.setAttribute("errorTitle", "Group update error");
				req.setAttribute("errorMessage", "Group not found in DB");
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req,resp);
			}
		}
	}

}
