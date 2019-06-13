package ru.otus.l111.datasets;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Table(name = "address")
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
