package ru.otus.servlets;

import javax.servlet.http.*;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {

	public LogoutServlet() { }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.getSession().invalidate();
		response.sendRedirect(request.getContextPath());
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doGet(req, resp);
	}
}
