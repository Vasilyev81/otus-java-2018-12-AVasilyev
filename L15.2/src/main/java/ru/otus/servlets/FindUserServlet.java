package ru.otus.servlets;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class FindUserServlet extends HttpServlet {
	private static final String APPLICATION_JSON = "application/json;charset=UTF-8";
	private static final String TEXT_HTML = "text/html; charset=utf-8";
	private final static String FINDUSER_HTML = "find_user.html";
	@Autowired
	private TemplateProcessor templateProcessor;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}

	public FindUserServlet() {
	}

	//The main idea is to send request with needed userId in ws.send and sessionId in cookies, handle it in findUserSocket,
	//send response {JSON|null) from findUserSocket, and handle it on page with JS
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id = RandomStringUtils.randomAlphanumeric(20);
		Cookie sessionId = new Cookie("sessionId", id);
		response.addCookie(sessionId);
		response.setContentType(TEXT_HTML);
		response.getWriter().println(templateProcessor.getPage(FINDUSER_HTML, Collections.emptyMap()));
		response.setStatus(200);
	}
}
