package ru.otus.datasets;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor
@Entity
public class UserDataSet extends DataSet {
	@Getter
	@Setter
	@Column(name = "name")
	private String name;
	@Getter
	@Setter
	@OneToOne(cascade = CascadeType.ALL)
	private AccountDataSet account;
	@Getter
	@Setter
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<PhoneDataSet> phones = new HashSet<>();
	@Getter
	@Setter
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<AddressDataSet> addresses = new HashSet<>();
	@Getter
	@Setter
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	private CompanyDataSet company;

	public UserDataSet(String name, AccountDataSet account, List<AddressDataSet> addresses, PhoneDataSet... phones) {
		this(-1, name, account, addresses, phones);
	}

	public UserDataSet(long id, String name, AccountDataSet account, List<AddressDataSet> addressDataSets, PhoneDataSet... phones) {
		super();
		this.setId(id);
		this.setName(name);
		this.setAccount(account);
		addresses.addAll(addressDataSets);
		this.phones.addAll(Arrays.asList(phones));
	}

	@Override
	public String toString() {
		return "UserDataSet{" +
				"id:" + getId() +
				", name: " + name +
				", role: " + account.getRole() +
				", addresses: " + getAddressesAsString() +
				", company: " + getCompany().getName() +
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

