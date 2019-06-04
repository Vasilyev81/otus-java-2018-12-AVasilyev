package ru.otus.servlets;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.otus.dao.DBService;
import ru.otus.datasets.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

public class FindUserServlet extends HttpServlet {
	private final static String FINDUSER_HTML = "finduser.html";
	private final static String FAIL_HTML = "failuser.html";
	private final static String FOUNDEDUSER_TEMPL = "foundeduser.ftl";

	@Autowired
	private DBService dbService;
	@Autowired
	private TemplateProcessor templateProcessor;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}

	public FindUserServlet(){ }

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=utf-8");
		response.getWriter().println(templateProcessor.getPage(FINDUSER_HTML, Collections.emptyMap()));
		response.setStatus(200);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		var id = Long.parseLong(request.getParameterValues("id")[0]);
		UserDataSet userDataSet = null;
		try {
			userDataSet = dbService.read(id);
		} catch (ObjectNotFoundException ex){
			System.err.println("Exception during retrieving user with id: " + id + " from database, exception: " + ex.getMessage());
		}
		if (userDataSet != null) {
			Map<String, Object> root = new HashMap<>();
			root.put("user", userDataSet);
			response.setContentType("text/html; charset=utf-8");
			response.getWriter().println(templateProcessor.getPage(FOUNDEDUSER_TEMPL, root));
			response.setStatus(200);
		} else {
			response.setContentType("text/html; charset=utf-8");
			response.getWriter().println(templateProcessor.getPage(FAIL_HTML, Collections.singletonMap("id", id)));
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}
}
