package ru.otus.servlets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.text.*;
import java.util.*;

public class TimeServlet extends HttpServlet {
	private static final String REFRESH_VARIABLE_NAME = "refreshPeriod";
	private static final String TIME_VARIABLE_NAME = "time";
	private static final String TIMER_PAGE_TEMPLATE = "time.html";
	private static final int PERIOD_MS = 1000;

	@Autowired
	private TemplateProcessor templateProcessor;

	public TimeServlet() { }

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> pageVariables = new HashMap<>();
		pageVariables.put(REFRESH_VARIABLE_NAME, String.valueOf(PERIOD_MS));
		pageVariables.put(TIME_VARIABLE_NAME, getTime());

		response.setContentType("text/html; charset=utf-8");
		response.getWriter().println(templateProcessor.getPage(TIMER_PAGE_TEMPLATE,pageVariables));
		response.setStatus(HttpServletResponse.SC_OK);
	}

	private String getTime() {
		DateFormat formatter = new SimpleDateFormat("HH.mm.ss");
		return formatter.format(new Date().getTime());
	}
}
