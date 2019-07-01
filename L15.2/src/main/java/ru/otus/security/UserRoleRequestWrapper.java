package ru.otus.security;

import javax.servlet.http.*;
import java.security.Principal;

public class UserRoleRequestWrapper extends HttpServletRequestWrapper {
	private String user;
	private String assignedRole;
	private HttpServletRequest realRequest;

	public UserRoleRequestWrapper(String user, String role, HttpServletRequest request) {
		super(request);
		this.user = user;
		assignedRole = role;
		this.realRequest = request;
	}

	@Override
	public boolean isUserInRole(String role) {
		if (role == null) {
			return realRequest.isUserInRole(role);
		}
		return assignedRole.equals(role);
	}

	@Override
	public Principal getUserPrincipal() {
		if (this.user == null) {
			return realRequest.getUserPrincipal();
		}
		return () -> user;
	}
}
