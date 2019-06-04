package ru.otus.servlets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.otus.apputils.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

public class LogonServlet extends HttpServlet {
	private final int ALIVE_TIME = (int) (60 * 1.5);
	private final String HTML_PAGE = "logon.html";
	@Autowired
	private TemplateProcessor templateProcessor;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}
	public LogonServlet() { }

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
			System.err.println("Error during parsing redirect ID!");
		}
		String requestUri = AppUtils.getRedirectUrl(redirectId);
		response.sendRedirect(Objects.requireNonNullElseGet(requestUri, () -> request.getContextPath() + "/admin_page"));
	}
}
