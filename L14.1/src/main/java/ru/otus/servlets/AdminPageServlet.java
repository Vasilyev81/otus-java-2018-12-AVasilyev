package ru.otus.servlets;

import ru.otus.processor.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Collections;

public class AdminPageServlet extends HttpServlet {
	private final static String HTML_DIR = "/admin_page/";
	private final static String HTML_PAGE = "admin_page.html";
	private final TemplateProcessor processor;

	public AdminPageServlet() throws IOException {
		processor = new TemplateProcessor(HTML_DIR);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		response.getWriter().println(processor.getPage(HTML_PAGE, Collections.emptyMap()));
		response.setStatus(200);
	}
}
