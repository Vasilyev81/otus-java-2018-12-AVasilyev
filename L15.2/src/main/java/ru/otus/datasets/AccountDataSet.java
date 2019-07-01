package ru.otus.datasets;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AccountDataSet extends DataSet {
	@Getter
	@Setter
	@Column
	private String login;
	@Getter
	@Setter
	@Column
	private String password;
	@Getter
	@Setter
	@Column
	private String role;



	public static boolean isValid(AccountDataSet account) {
		if (account == null) return false;
		else return account.login == null || account.password == null || account.role == null;
	}

	public static AccountDataSet getDefaultUserAccount() {
		return new AccountDataSet("user", "user", "USER");
	}
	public static AccountDataSet getUserAccount(String login, String password) {
		return new AccountDataSet(login,password, "USER");
	}
	public static AccountDataSet getAdminAccount(String login, String password) {
		return new AccountDataSet(login,password, "ADMINISTRATOR");
	}

}
