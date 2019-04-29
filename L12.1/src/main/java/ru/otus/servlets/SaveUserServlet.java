package ru.otus.servlets;

import ru.otus.dao.DBService;
import ru.otus.datasets.AddressDataSet;
import ru.otus.datasets.CompanyDataSet;
import ru.otus.datasets.PhoneDataSet;
import ru.otus.datasets.UserDataSet;
import ru.otus.processor.TemplateProcessor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class SaveUserServlet extends HttpServlet {

	private final static String HTML_DIR = "/admin_page/";
	private final static String SAVEUSER_HTML = "saveuser.html";
	private final static String CONGRATULATION_HTML = "congrat_page.html";
	private final TemplateProcessor templateProcessor;
	private final DBService dbService;

	public SaveUserServlet(DBService dbService) throws IOException {
		this.templateProcessor = new TemplateProcessor(HTML_DIR);
		this.dbService = dbService;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=utf-8");
		response.getWriter().println(templateProcessor.getPage(SAVEUSER_HTML, Collections.emptyMap()));
		response.setStatus(200);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String name = request.getParameterValues("name")[0];
		String phone = request.getParameterValues("phone")[0];
		String address = request.getParameterValues("address")[0];
		String company = request.getParameterValues("company")[0];
		UserDataSet userDataSet = new UserDataSet(name, Collections.singletonList(new AddressDataSet(address)), new PhoneDataSet(phone));
		CompanyDataSet companyDataSet = new CompanyDataSet(company);
		dbService.save(companyDataSet);
		companyDataSet.addEmployee(userDataSet);
		dbService.save(userDataSet);
		response.setContentType("text/html; charset=utf-8");
		response.getWriter().println(templateProcessor.getPage(CONGRATULATION_HTML, Collections.emptyMap()));
		response.setStatus(200);
	}
}