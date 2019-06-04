package ru.otus.servlets;

import lombok.NoArgsConstructor;
import ru.otus.apputils.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

@NoArgsConstructor
public class SecurityFilter implements Filter {
	@Override
	public void init(FilterConfig fConfig) {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String servletPath = request.getServletPath();
		Account loginedUser = AppUtils.getLoginedUser(request.getSession());

		if (servletPath.equals("/logon")) {
			chain.doFilter(request, response);
			return;
		}
		HttpServletRequest wrapRequest = request;
		if (loginedUser != null) {
			String userName = loginedUser.getLogin();
			String role = loginedUser.getRole();
			wrapRequest = new UserRoleRequestWrapper(userName, role, request);
		}
		if (SecurityUtils.isSecurityPage(request)) {
			if (loginedUser == null) {
				String requestUri = request.getRequestURI();
				int redirectId = AppUtils.storeRedirectURL(requestUri);

				response.sendRedirect(wrapRequest.getContextPath() + "/logon?redirectId=" + redirectId);
				return;
			}
			boolean hasPermission = SecurityUtils.hasPermission(wrapRequest);
			if (!hasPermission) {
				RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("logonError.html");
				dispatcher.forward(request, response);
				return;
			}
		}
		chain.doFilter(wrapRequest, response);
	}

	@Override
	public void destroy() {
	}
}