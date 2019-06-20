package ru.otus.servlets;

import ru.otus.datasets.UserDataSet;
import ru.otus.processor.TemplateProcessor;

import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

public class ListOfUsersServlet extends HttpServlet {
	private final static String HTML_DIR = "/admin_page/";
	private final static String TEMPLATE_PAGE = "users.ftl";
	private final TemplateProcessor templateProcessor;
	private final FrontendService frontendService;

	public ListOfUsersServlet(FrontendService frontendService) throws IOException {
		this.templateProcessor = new TemplateProcessor(HTML_DIR);
		this.frontendService = frontendService;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		frontendService.readAll();
		List<UserDataSet> userDataSetList = getUsersListFromFrontendService();
		Map<String, Object> root = new HashMap<>();
		root.put("users", userDataSetList);
		response.setContentType("text/html; charset=utf-8");
		response.getWriter().println(templateProcessor.getPage(TEMPLATE_PAGE, root));
		response.setStatus(HttpServletResponse.SC_OK);
	}

	private List<UserDataSet> getUsersListFromFrontendService() {
		List<UserDataSet> userDataSetList = null;
		while (userDataSetList == null){
			userDataSetList = frontendService.getResultList();
		}
		return userDataSetList;
	}
}
