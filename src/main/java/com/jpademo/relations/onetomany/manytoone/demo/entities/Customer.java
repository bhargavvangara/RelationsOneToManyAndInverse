package com.jpademo.relations.onetomany.manytoone.demo.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="customer_onetomany")
@Data
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<PhoneNumber> phoneNumbers;
	public void addPhoneNumber(PhoneNumber phoneNumber) {
		if (phoneNumbers == null) {
			phoneNumbers = new HashSet<>();
		}
		phoneNumbers.add(phoneNumber);
		phoneNumber.setCustomer(this);
	}
}
