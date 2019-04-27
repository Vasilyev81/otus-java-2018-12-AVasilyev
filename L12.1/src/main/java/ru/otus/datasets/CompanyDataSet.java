package ru.otus.datasets;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "company")
public class CompanyDataSet extends DataSet {

	@Getter
	@Setter
	@Column(name = "name")
	private String name;

	@Getter
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserDataSet> employees = new ArrayList<>();

	public CompanyDataSet(String name) {
		this.setName(name);
	}

	public void addEmployees(List<UserDataSet> employee) {
		this.employees.addAll(employee);
		employee.forEach(user -> user.setCompany(this));
	}

	public void addEmployee(UserDataSet employee){
		employees.add(employee);
		employee.setCompany(this);
	}
}
