package ru.otus.datasets;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@NoArgsConstructor
@Entity
//@Table(name = "address")
public class AddressDataSet extends DataSet {

	@Getter
	@Setter
	@Column
	private String street;

	public AddressDataSet(String street) {
		super();
		this.street = street;
	}

	@Override
	public String toString() {
		return "AddressDataSet{" +
				"street='" + street + '\'' +
				'}';
	}
}
