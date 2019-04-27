package ru.otus.serverconfig;

/*
 * Customise text color
 * */

import org.eclipse.jetty.security.*;
import org.eclipse.jetty.security.authentication.FormAuthenticator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.security.Constraint;
import org.eclipse.jetty.util.security.Credential;
import ru.otus.dao.DBService;
import ru.otus.servlets.*;

import java.util.ArrayList;
import java.util.List;

public class ServerConfiguration {
	private DBService dbService;
	private final static int PORT = 8080;
	private final static String PUBLIC_HTML = "/static";
	private final static String LOGIN_PROPERTIES = "/loginService.properties";

	public ServerConfiguration(DBService dbService) {
		this.dbService = dbService;
	}

	public void start() throws Exception {
		Server server = new Server(PORT);

		ResourceHandler resourceHandler = new ResourceHandler();
		resourceHandler.setBaseResource(Resource.newClassPathResource(PUBLIC_HTML));

		HashLoginService loginService = new HashLoginService( "loginService", LOGIN_PROPERTIES);

		UserStore userStore = new UserStore();
		userStore.addUser("admin", Credential.getCredential("password"),new String[]{"admin"});
		loginService.setUserStore(userStore);
		server.addBean(loginService);
		SessionHandler sessionHandler = new SessionHandler();
		sessionHandler.setMaxInactiveInterval(30);

		ConstraintSecurityHandler securityHandler = new ConstraintSecurityHandler();

		Constraint constraint = new Constraint();
		constraint.setName("auth");
		constraint.setAuthenticate(true);
		constraint.setRoles(new String[]{"admin"});

		ConstraintMapping constraintMapping = new ConstraintMapping();
		constraintMapping.setPathSpec("/admin_page/");
		constraintMapping.setConstraint(constraint);

		ConstraintMapping constraintMapping1 = new ConstraintMapping();
		constraintMapping1.setPathSpec("/admin_page/*");
		constraintMapping1.setConstraint(constraint);
		List<ConstraintMapping> mappings = new ArrayList<>();
		mappings.add(constraintMapping);
		mappings.add(constraintMapping1);

		FormAuthenticator formAuthenticator = new FormAuthenticator("/login.html", "/login-fail.html", false);

		securityHandler.setConstraintMappings(mappings);
		securityHandler.setAuthenticator(formAuthenticator);
		securityHandler.setLoginService(loginService);

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

		context.addServlet(new ServletHolder(new TimeServlet()), "/time");
		context.addServlet(new ServletHolder(new AdminPageServlet()), "/admin_page");
		context.addServlet(new ServletHolder(new SaveUserServlet(dbService)), "/admin_page/saveuser");
		context.addServlet(new ServletHolder(new FindUserServlet(dbService)), "/admin_page/finduser"); //TODO: Servlet and html-page are not created
		context.addServlet(new ServletHolder(new ListOfUsersServlet(dbService)), "/admin_page/listofusers");

		securityHandler.setHandler(context);

		HandlerList handlerList = new HandlerList();
		handlerList.addHandler(resourceHandler);
		handlerList.addHandler(sessionHandler);
		handlerList.addHandler(securityHandler);

		server.setHandler(handlerList);

		server.start();
		server.join();
	}
}
