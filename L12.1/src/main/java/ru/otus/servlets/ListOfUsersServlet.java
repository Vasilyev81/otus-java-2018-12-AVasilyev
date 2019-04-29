package ru.otus.servlets;

import ru.otus.dao.DBService;
import ru.otus.datasets.UserDataSet;
import ru.otus.processor.TemplateProcessor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListOfUsersServlet extends HttpServlet {
	private final static String HTML_DIR = "/admin_page/";
	private final static String TEMPLATE_PAGE = "users.ftl";
	private final TemplateProcessor templateProcessor;
	private final DBService dbService;

	public ListOfUsersServlet(DBService dbService) throws IOException {
		this.templateProcessor = new TemplateProcessor(HTML_DIR);
		this.dbService = dbService;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<UserDataSet> userDataSetList = dbService.readAll();
		Map<String, Object> root = new HashMap<>();
		root.put("users", userDataSetList);
		response.setContentType("text/html; charset=utf-8");
		response.getWriter().println(templateProcessor.getPage(TEMPLATE_PAGE, root));
		response.setStatus(HttpServletResponse.SC_OK);
	}
}
