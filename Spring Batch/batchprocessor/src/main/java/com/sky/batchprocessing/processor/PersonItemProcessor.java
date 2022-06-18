package com.sky.batchprocessing.processor;

import org.springframework.batch.item.ItemProcessor;

import com.sky.batchprocessing.model.Person;

public class PersonItemProcessor implements ItemProcessor<Person, Person>{

	@Override
	public Person process(Person person) throws Exception {
		final String firstName=person.getFirstName().toUpperCase();
		final String lastName=person.getLastName().toUpperCase();
		final Person transformaedPerson= new Person(firstName, lastName);
		System.out.println("Converting(" + person + ")into(" + transformaedPerson + " )");
		return transformaedPerson;
	}

}
