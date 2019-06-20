package ru.otus.servlets;

import org.hibernate.ObjectNotFoundException;
import ru.otus.dao.DBService;
import ru.otus.datasets.UserDataSet;
import ru.otus.processor.TemplateProcessor;

import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

public class FindUserServlet extends HttpServlet {
	private final static String HTML_DIR = "/admin_page/";
	private final static String FINDUSER_HTML = "finduser.html";
	private final static String FAIL_HTML = "failuser.html";
	private final static String FOUNDEDUSER_TEMPL = "foundeduser.ftl";
	private final TemplateProcessor processor;
	private final FrontendService frontendService;

	public FindUserServlet(FrontendService frontendService) throws IOException {
		processor = new TemplateProcessor(HTML_DIR);
		this.frontendService = frontendService;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=utf-8");
		response.getWriter().println(processor.getPage(FINDUSER_HTML, Collections.emptyMap()));
		response.setStatus(200);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		var id = Long.parseLong(request.getParameterValues("id")[0]);
		frontendService.findUserByID(id);
		UserDataSet userDataSet = getUser();
		if (userDataSet != null) {
			Map<String, Object> root = new HashMap<>();
			root.put("user", userDataSet);
			response.setContentType("text/html; charset=utf-8");
			response.getWriter().println(processor.getPage(FOUNDEDUSER_TEMPL, root));
			response.setStatus(200);
		} else {
			response.setContentType("text/html; charset=utf-8");
			response.getWriter().println(processor.getPage(FAIL_HTML, Collections.singletonMap("id", id)));
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	private UserDataSet getUser() {
		List<UserDataSet> users = frontendService.getResultList();
		return users.get(0);
	}
}
