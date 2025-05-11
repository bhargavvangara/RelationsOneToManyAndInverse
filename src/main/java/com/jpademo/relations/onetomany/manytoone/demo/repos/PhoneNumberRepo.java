package com.jpademo.relations.onetomany.manytoone.demo.repos;

import org.springframework.data.repository.CrudRepository;

import com.jpademo.relations.onetomany.manytoone.demo.entities.PhoneNumber;

public interface PhoneNumberRepo extends CrudRepository<PhoneNumber, Integer> {

}
