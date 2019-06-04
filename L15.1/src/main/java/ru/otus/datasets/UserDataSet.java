package ru.otus.datasets;

import lombok.*;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserDataSet extends DataSet {
	@Getter
	@Setter
	@Column(name = "name")
	private String name;
	@Getter
	@Setter
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<PhoneDataSet> phones = new ArrayList<>();
	@Getter
	@Setter
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "user_id", nullable = false)
	private List<AddressDataSet> addresses = new ArrayList<>();
	@Getter
	@Setter
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	private CompanyDataSet company;

	public UserDataSet(String name, List<AddressDataSet> addresses, PhoneDataSet... phones) {
		this(-1, name, addresses, phones);
	}

	public UserDataSet(long id, String name, List<AddressDataSet> addressDataSets, PhoneDataSet... phones) {
		super();
		this.setId(id);
		this.setName(name);
		addresses.addAll(addressDataSets);
		List<PhoneDataSet> userPhones = Arrays.asList(phones);
		this.setPhones(userPhones);
		userPhones.forEach(phone -> phone.setUser(this));
	}

	@Override
	public String toString() {
		return "UserDataSet{" +
				"id:" + getId() +
				", name: " + name +
				", addresses: " + getAddressesAsString() +
				", company: " + getCompany().getName()+
				", phones: " + getPhonesAsString() +
				'}';
	}

	private String getAddressesAsString() {
		return addresses.stream().map(AddressDataSet::getStreet).collect(Collectors.joining(", "));
	}

	private String getPhonesAsString() {
	return phones.stream().map(PhoneDataSet::getNumber).collect(Collectors.joining(", "));
	}
}

