package ru.otus.datasets;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Entity
//@Table(name = "company")
public class CompanyDataSet extends DataSet {

	@Getter
	@Setter
	@Column(name = "name")
	private String name;

	@Getter
	@OneToMany(cascade = CascadeType.REFRESH, orphanRemoval = true)
	private Set<UserDataSet> employees = new HashSet<>();

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

	public void removeEmployees() {
		employees = null;
	}
}
