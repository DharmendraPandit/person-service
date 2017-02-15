package com.example.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Person;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

	Person findByEmailAddress(String emailAddress);
}
