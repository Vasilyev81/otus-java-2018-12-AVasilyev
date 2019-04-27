package ru.otus.servlets;

import ru.otus.processor.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class AdminPageServlet extends HttpServlet {
	private final static String HTML_DIR = "/admin_page/";
	private final static String HTML_PAGE = "admin_page.html";
	private static TemplateProcessor processor;

	public AdminPageServlet() throws IOException{
		processor = new TemplateProcessor(HTML_DIR);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=utf-8");
		resp.getWriter().println(processor.getPage(HTML_PAGE, Collections.emptyMap()));
		resp.setStatus(200);
	}
}
