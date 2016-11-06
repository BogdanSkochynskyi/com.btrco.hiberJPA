package com.btrco.hiberJPA.servlets.groupServlets;

import com.btrco.hiberJPA.entity.Group;
import com.btrco.hiberJPA.exceptions.EntityNotFoundException;
import com.btrco.hiberJPA.exceptions.RowsAmountException;
import com.btrco.hiberJPA.service.IGroupService;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/group/list"})
public class GetListOfGroupsServlet extends HttpServlet {

	private ApplicationContext applicationContext;
	private IGroupService groupService;

	@Override
	public void init() throws ServletException {
		this.applicationContext = (ApplicationContext) getServletContext().getAttribute("spring-context");
		this.groupService = (IGroupService) this.applicationContext.getBean("groupService");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		int page = 1;
		int recordsOnPage = 5;

		if(req.getParameter("page") != null) {
			page = Integer.parseInt(req.getParameter("page"));
		}

		List<Group> groups = null;
		try {
			groups = groupService.getListOfGroup((page-1)*recordsOnPage, recordsOnPage);
		} catch (RowsAmountException e) {
			e.printStackTrace();
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}

		if (groups == null || groups.size() == 0) {
			//error
		} else {
			req.setAttribute("groupsList", groups);
			req.setAttribute("currentPage", page);
			req.getRequestDispatcher("/WEB-INF/pages/group/groupList.jsp").forward(req,resp);
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


	}
}
