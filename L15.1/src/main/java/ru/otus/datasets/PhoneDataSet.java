package ru.otus.datasets;

import lombok.*;

import javax.persistence.*;


@NoArgsConstructor
@Entity
@Table(name = "phone")
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
