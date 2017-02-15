package com.example.controller;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.entity.Person;
import com.example.repository.PersonRepository;

@RestController
@RequestMapping("/people")
public class PersonController {

	@Autowired
	PersonRepository personRepository;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Person>> findAll() {
		List<Person> personList = (List<Person>) this.personRepository.findAll();
		if (CollectionUtils.isEmpty(personList)) {
			return new ResponseEntity<List<Person>>(Collections.emptyList(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Person>>(personList, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Person> addPerson(@RequestBody Person person) {
		Person newPerson = this.personRepository.save(person);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create("/people/" + newPerson.getId()));
		return new ResponseEntity<Person>(newPerson, headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Person> getPerson(@PathVariable Long id) {
		Person person = this.personRepository.findOne(id);
		if (null == person) {
			return new ResponseEntity<Person>(person, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Person>(person, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody Person person) {
		person.setId(id);
		Person updatedPerson = this.personRepository.save(person);
		return new ResponseEntity<Person>(updatedPerson, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Person> deletePerson(@PathVariable Long id) {
		this.personRepository.delete(id);
		Person person = null;
		return new ResponseEntity<Person>(person, HttpStatus.RESET_CONTENT);
	}

}
