package ru.otus.servlets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.otus.dao.DBService;
import ru.otus.datasets.UserDataSet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

public class ListOfUsersServlet extends HttpServlet {
	private final static String TEMPLATE_PAGE = "users.ftl";

	@Autowired
	private DBService dbService;
	@Autowired
	private TemplateProcessor templateProcessor;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}


	public ListOfUsersServlet(){ }

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<UserDataSet> userDataSetList = dbService.readAll();
		Map<String, Object> root = new HashMap<>();
		root.put("users", userDataSetList);
		response.setContentType("text/html; charset=utf-8");
		response.getWriter().println(templateProcessor.getPage(TEMPLATE_PAGE, root));
		response.setStatus(HttpServletResponse.SC_OK);
	}
}
