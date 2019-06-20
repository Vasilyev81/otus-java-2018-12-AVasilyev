package ru.otus.serverconfig;

import org.eclipse.jetty.security.*;
import org.eclipse.jetty.security.authentication.FormAuthenticator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.*;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.*;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.security.*;
import ru.otus.dao.DBService;
import ru.otus.servlets.*;

import java.util.ArrayList;
import java.util.List;

public class ServerConfiguration {
	private FrontendService frontendService;
	private final static int PORT = 8080;
	private final static String PUBLIC_HTML = "/static";
	private final static String LOGIN_PROPERTIES = "/loginService.properties";

	public ServerConfiguration(FrontendService frontendService) {
		this.frontendService = frontendService;
	}

	public void start() throws Exception {
		Server server = new Server(PORT);

		ResourceHandler resourceHandler = new ResourceHandler();
		resourceHandler.setBaseResource(Resource.newClassPathResource(PUBLIC_HTML));

		HashLoginService loginService = new HashLoginService("loginService", LOGIN_PROPERTIES);

		UserStore userStore = new UserStore();
		userStore.addUser("admin", Credential.getCredential("password"), new String[]{"admin"});
		loginService.setUserStore(userStore);
		server.addBean(loginService);
		SessionHandler sessionHandler = new SessionHandler();
		sessionHandler.setMaxInactiveInterval(300);

		ConstraintSecurityHandler securityHandler = new ConstraintSecurityHandler();

		Constraint constraint = new Constraint();
		constraint.setName("auth");
		constraint.setAuthenticate(true);
		constraint.setRoles(new String[]{"admin"});

		ConstraintMapping adminPath = new ConstraintMapping();
		adminPath.setPathSpec("/admin_page/");
		adminPath.setConstraint(constraint);

		ConstraintMapping pathsBelowAdmin = new ConstraintMapping();
		pathsBelowAdmin.setPathSpec("/admin_page/*");
		pathsBelowAdmin.setConstraint(constraint);
		List<ConstraintMapping> mappings = new ArrayList<>();
		mappings.add(adminPath);
		mappings.add(pathsBelowAdmin);

		FormAuthenticator formAuthenticator = new FormAuthenticator("/login.html", "/login_fail.html", false);

		securityHandler.setConstraintMappings(mappings);
		securityHandler.setAuthenticator(formAuthenticator);
		securityHandler.setLoginService(loginService);

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

		context.addServlet(new ServletHolder(new TimeServlet()), "/time");
		context.addServlet(new ServletHolder(new AdminPageServlet()), "/admin_page");
		context.addServlet(new ServletHolder(new SaveUserServlet(frontendService)), "/admin_page/saveuser");
		context.addServlet(new ServletHolder(new FindUserServlet(frontendService)), "/admin_page/finduser");
		context.addServlet(new ServletHolder(new ListOfUsersServlet(frontendService)), "/admin_page/listofusers");

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
