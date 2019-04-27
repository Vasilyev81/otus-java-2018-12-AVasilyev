package ru.otus.servlets;

import ru.otus.dao.DBService;
import ru.otus.datasets.UserDataSet;
import ru.otus.processor.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class FindUserServlet extends HttpServlet {
	private static final String HTML_DIR = "/admin_page/";
	private static final String HTML_PAGE = "finduser.html";
	private final TemplateProcessor processor;
	private final DBService dbService;



	public FindUserServlet(DBService dbService) throws IOException {
		processor = new TemplateProcessor(HTML_DIR);
		this.dbService = dbService;
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=utf-8");
		resp.getWriter().println(processor.getPage(HTML_PAGE, Collections.emptyMap()));
		resp.setStatus(200);
	}


	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Long id = Long.parseLong(req.getParameterValues("id")[0]);
		UserDataSet userDataSet = dbService.read(id);
		resp.setContentType("text/html; charset=utf-8");
		resp.getWriter().println(userDataSet.toString());
		resp.setStatus(200);
	}
}
