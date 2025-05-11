package com.jpademo.relations.onetomany.manytoone.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.jpademo.relations.onetomany.manytoone.demo.entities.Customer;
import com.jpademo.relations.onetomany.manytoone.demo.entities.PhoneNumber;
import com.jpademo.relations.onetomany.manytoone.demo.repos.CustomerRepo;
import com.jpademo.relations.onetomany.manytoone.demo.repos.PhoneNumberRepo;

@SpringBootTest
class RelationsOneToManyAndInverseApplicationTests {
	@Autowired
	private CustomerRepo customerRepo;
	@Autowired
	private PhoneNumberRepo phoneNumberRepo;
	@Test
	void insertCustomerAndPhoneNumberRecords() {
		Customer customer = new Customer();
		customer.setName("Bhargav");
		PhoneNumber phoneNumber1 = new PhoneNumber();
		phoneNumber1.setNumber("1234567890");
		phoneNumber1.setType("home");
		PhoneNumber phoneNumber2 = new PhoneNumber();
		phoneNumber2.setNumber("0987654321");
		phoneNumber2.setType("work");
		customer.addPhoneNumber(phoneNumber1);
		customer.addPhoneNumber(phoneNumber2);
		customerRepo.save(customer);
	}
	
	@Test
	@Transactional
	@Rollback(false)
	void insertPhoneNumberRecords() {
		Customer customer = customerRepo.findById(2).orElse(null);
		if (customer != null) {
			PhoneNumber phoneNumber = new PhoneNumber();
			phoneNumber.setNumber("5555555555");
			phoneNumber.setType("mobile");
			customer.addPhoneNumber(phoneNumber);
			customerRepo.save(customer);
		}
	}
	
	@Test
	@Transactional(readOnly = true)
	void getCustomerAndPhoneNumbers() {
		Customer customer = customerRepo.findById(2).orElse(null);
		if (customer != null) {
			System.out.println("Customer: " + customer.getName());
			// Fetching phone numbers (lazy loading)
			for (PhoneNumber phoneNumber : customer.getPhoneNumbers()) {
				System.out.println("Phone Number: " + phoneNumber.getNumber() + ", Type: " + phoneNumber.getType());
			}
		}
	}
	
	//orphan removal
	@Test
	@Transactional
	@Rollback(false)
	void deletePhoneNumber() {
		Customer customer = customerRepo.findById(2).orElse(null);
		if (customer != null) {
			PhoneNumber phoneNumber = customer.getPhoneNumbers().stream().findFirst().orElse(null);
			customer.getPhoneNumbers().remove(phoneNumber);
			customerRepo.save(customer);
		}
	}
	
	@Test
	@Transactional
	@Rollback(false)
	//cascade delete
	void deleteCustomer() {
		Customer customer = customerRepo.findById(2).orElse(null);
		if (customer != null) {
			customerRepo.delete(customer);
		}
	}
	
	//inverse fetch
	@Test
	@Transactional(readOnly = true)
	void getPhoneNumberAndCustomer() {
		PhoneNumber phoneNumber = phoneNumberRepo.findById(5).orElse(null);
		if (phoneNumber != null) {
			System.out.println("Phone Number: " + phoneNumber.getNumber());
			System.out.println("Customer: " + phoneNumber.getCustomer().getName());
		}
	}

}
