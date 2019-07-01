package ru.otus.datasets;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@NoArgsConstructor
@Entity
//@Table(name = "phone")
public class PhoneDataSet extends DataSet {
	@Getter
	@Setter
	@Column(name = "number")
	private String number;
	@Getter
	@Setter
	@ManyToOne(fetch = FetchType.EAGER)
	private UserDataSet user;

	public PhoneDataSet(String number) {
		super();
		this.number = number;
	}

	@Override
	public String toString() {
		return "PhoneDataSet{" +
				"number='" + number + '\'' +
				'}';
	}
}
