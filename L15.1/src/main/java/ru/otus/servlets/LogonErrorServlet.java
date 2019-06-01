package ru.otus.servlets;

import ru.otus.apputils.SpringContextProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class LogonErrorServlet extends HttpServlet {
private final String HTML_PAGE = "logonError.html";
	private final TemplateProcessor templateProcessor;

	public LogonErrorServlet() {
		templateProcessor = (TemplateProcessor) SpringContextProvider.getContext().getBean("templateProcessor");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=utf-8");
		response.getWriter().println(templateProcessor.getPage(HTML_PAGE, Collections.emptyMap()));
		response.setStatus(200);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doGet(request, response);

	}
}
