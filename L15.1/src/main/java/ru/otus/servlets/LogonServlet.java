package ru.otus.servlets;

import ru.otus.apputils.Account;
import ru.otus.apputils.AppUtils;
import ru.otus.apputils.SpringContextProvider;
import ru.otus.apputils.SecurityDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;

public class LogonServlet extends HttpServlet {
	private final int ALIVE_TIME = (int) (60 * 1.5);
	private final String HTML_PAGE = "logon.html";
	private TemplateProcessor templateProcessor;

	public LogonServlet() {
		templateProcessor = (TemplateProcessor) SpringContextProvider.getContext().getBean("templateProcessor");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=utf-8");
		response.getWriter().println(templateProcessor.getPage(HTML_PAGE, Collections.emptyMap()));
		response.setStatus(200);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		Account userAccount = SecurityDAO.findUser(login, password);
		if (userAccount == null) {
			String errorMessage = "Invalid userName or Password";
			request.setAttribute("errorMessage", errorMessage);
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/logon_error");
			dispatcher.forward(request, response);
			return;
		}
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(ALIVE_TIME);
		AppUtils.storeLoginedUser(session, userAccount);
		int redirectId = -1;
		try {
			redirectId = Integer.parseInt(request.getParameter("redirectId"));
		} catch (Exception e) {
		}
		String requestUri = AppUtils.getRedirectUrl(redirectId);
		if (requestUri != null) {
			response.sendRedirect(requestUri);
		} else {
			response.sendRedirect(request.getContextPath() + "/admin_page");
		}
	}
}
