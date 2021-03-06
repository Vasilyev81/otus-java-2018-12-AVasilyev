package ru.otus.apputils;

import lombok.*;

@NoArgsConstructor
public class Account {
	@Getter
	@Setter
	private String login;
	@Getter
	@Setter
	private String password;
	@Getter
	@Setter
	private String role;

	public Account(String login, String password, String role) {
		this.login = login;
		this.password = password;
		this.role = role;
	}
}
