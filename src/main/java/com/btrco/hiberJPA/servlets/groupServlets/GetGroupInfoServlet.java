package com.btrco.hiberJPA.servlets.groupServlets;

import com.btrco.hiberJPA.entity.Group;
import com.btrco.hiberJPA.exceptions.EntityNotFoundException;
import com.btrco.hiberJPA.exceptions.InvalidIdException;
import com.btrco.hiberJPA.service.IGroupService;
import org.omg.CORBA.INTERNAL;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/group/info"})
public class GetGroupInfoServlet extends HttpServlet {

	private ApplicationContext applicationContext;
	private IGroupService groupService;

	@Override
	public void init() throws ServletException {
		this.applicationContext = (ApplicationContext) getServletContext().getAttribute("spring-context");
		this.groupService = (IGroupService) this.applicationContext.getBean("groupService");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idStr = req.getParameter("id");
		if (idStr == null || idStr.equals("")) {

		} else {
			int id = Integer.parseInt(idStr);
			try {
				Group group = groupService.getGroupById(id);
				req.setAttribute("group", group);
				req.getRequestDispatcher("/WEB-INF/pages/group/groupInfo.jsp").forward(req, resp);
			} catch (InvalidIdException | EntityNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

}
