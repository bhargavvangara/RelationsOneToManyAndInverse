package com.jpademo.relations.onetomany.manytoone.demo.repos;

import org.springframework.data.repository.CrudRepository;

import com.jpademo.relations.onetomany.manytoone.demo.entities.Customer;

public interface CustomerRepo extends CrudRepository<Customer, Integer> {

}
