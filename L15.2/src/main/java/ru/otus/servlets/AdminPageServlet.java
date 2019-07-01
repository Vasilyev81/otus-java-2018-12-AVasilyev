package ru.otus.servlets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.otus.backend.dbServise.DBPreparation;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Collections;

public class AdminPageServlet extends HttpServlet {
	private final static String HTML_PAGE = "admin_page.html";

	@Autowired
	private TemplateProcessor templateProcessor;

	@Autowired
	private DBPreparation dbPreparation;

	public AdminPageServlet() { }

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=utf-8");
		response.getWriter().println(templateProcessor.getPage(HTML_PAGE, Collections.emptyMap()));
		response.setStatus(200);
	}
}
