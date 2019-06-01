package ru.otus.servlets;

import org.hibernate.ObjectNotFoundException;
import ru.otus.apputils.SpringContextProvider;
import ru.otus.dao.DBService;
import ru.otus.dao.DBServiceH2Impl;
import ru.otus.datasets.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FindUserServlet extends HttpServlet {
	private final static String FINDUSER_HTML = "finduser.html";
	private final static String FAIL_HTML = "failuser.html";
	private final static String FOUNDEDUSER_TEMPL = "foundeduser.ftl";
	private final DBService dbService;
	private final TemplateProcessor templateProcessor;

	public FindUserServlet(){
		templateProcessor = (TemplateProcessor) SpringContextProvider.getContext().getBean("templateProcessor");
		dbService = (DBService) SpringContextProvider.getContext().getBean("dbService");

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=utf-8");
		response.getWriter().println(templateProcessor.getPage(FINDUSER_HTML, Collections.emptyMap()));
		response.setStatus(200);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		var id = Long.parseLong(request.getParameterValues("id")[0]);
		UserDataSet userDataSet = null;
		try {
			userDataSet = dbService.read(id);
		} catch (ObjectNotFoundException ex){
			System.err.println("Exception during retrieving user with id: " + id + " from database, exception: " + ex.getMessage());
		}
		if (userDataSet != null) {
			Map<String, Object> root = new HashMap<>();
			root.put("user", userDataSet);
			response.setContentType("text/html; charset=utf-8");
			response.getWriter().println(templateProcessor.getPage(FOUNDEDUSER_TEMPL, root));
			response.setStatus(200);
		} else {
			response.setContentType("text/html; charset=utf-8");
			response.getWriter().println(templateProcessor.getPage(FAIL_HTML, Collections.singletonMap("id", id)));
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}
}
