package ru.otus.servlets;

import org.hibernate.ObjectNotFoundException;
import ru.otus.dao.DBService;
import ru.otus.datasets.UserDataSet;
import ru.otus.processor.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FindUserServlet extends HttpServlet {
	private final static String HTML_DIR = "/admin_page/";
	private final static String FINDUSER_HTML = "finduser.html";
	private final static String FAIL_HTML = "failuser.html";
	private final static String FOUNDEDUSER_TEMPL = "foundeduser.ftl";
	private final TemplateProcessor processor;
	private final DBService dbService;

	public FindUserServlet(DBService dbService) throws IOException {
		processor = new TemplateProcessor(HTML_DIR);
		this.dbService = dbService;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=utf-8");
		response.getWriter().println(processor.getPage(FINDUSER_HTML, Collections.emptyMap()));
		response.setStatus(200);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		var id = Long.parseLong(request.getParameterValues("id")[0]);
		UserDataSet userDataSet = null;
		try {
			userDataSet = dbService.read(id);
		} catch (ObjectNotFoundException ex) {
			System.err.println("Exception while retrieving user with id " + id + " from database, " + ex.getMessage());
		}

		if (userDataSet != null) {
			Map<String, Object> root = new HashMap<>();
			root.put("user", userDataSet);
			response.setContentType("text/html; charset=utf-8");
			response.getWriter().println(processor.getPage(FOUNDEDUSER_TEMPL, root));
			response.setStatus(200);
		} else {
			response.setContentType("text/html; charset=utf-8");
			response.getWriter().println(processor.getPage(FAIL_HTML, Collections.singletonMap("id", id)));
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}
}
