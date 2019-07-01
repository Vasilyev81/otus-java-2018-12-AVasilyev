package ru.otus.servlets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.otus.datasets.AccountDataSet;
import ru.otus.security.AppUtils;
import ru.otus.security.SecurityDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.Objects;
import java.util.logging.Logger;

public class LogonServlet extends HttpServlet {
	private final int ALIVE_TIME = (int) (60 * 1.5);
	private final String HTML_PAGE = "logon.html";
	private HttpServletRequest requestWithRedirect;
	private String redirectIdValue;
	@Autowired
	private SecurityDAO securityDao;
	@Autowired
	private TemplateProcessor templateProcessor;
	private final Logger log = Logger.getLogger(LogoutServlet.class.getName());

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}

	public LogonServlet() {
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=utf-8");
		response.getWriter().println(templateProcessor.getPage(HTML_PAGE, Collections.emptyMap()));
		response.setStatus(200);
		redirectIdValue = request.getParameter("redirectId");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		AccountDataSet userAccount = securityDao.findUser(login, password);
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
			redirectId = Integer.parseInt(redirectIdValue);
		} catch (Exception e) {
			log.warning("Error during parsing redirect ID: \"" + redirectId + "\"");
		}
		String requestUri = AppUtils.getRedirectUrl(redirectId);
		response.sendRedirect(Objects.requireNonNullElseGet(requestUri, () -> request.getContextPath() + "/admin_page"));
	}
}
