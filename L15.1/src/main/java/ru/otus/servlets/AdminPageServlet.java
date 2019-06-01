package ru.otus.servlets;

import ru.otus.apputils.SpringContextProvider;
import ru.otus.dao.DBPreparation;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class AdminPageServlet extends HttpServlet {
	private final static String HTML_PAGE = "admin_page.html";
	private final TemplateProcessor templateProcessor;

	public AdminPageServlet() {
		templateProcessor = (TemplateProcessor) SpringContextProvider.getContext().getBean("templateProcessor");
		Runnable fillInDB = DBPreparation::fillIn;
		fillInDB.run();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=utf-8");
		response.getWriter().println(templateProcessor.getPage(HTML_PAGE, Collections.emptyMap()));
		response.setStatus(200);
	}
}
