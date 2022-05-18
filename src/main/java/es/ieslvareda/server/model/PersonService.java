package es.ieslvareda.server.model;

import es.ieslvareda.model.Person;
import es.ieslvareda.model.Result;

import java.util.List;

public interface PersonService {

    List<Person> getAll();
    Result<Person> get(String dni);
    Result<Person> update(Person person);
    Result<Person> add(Person person);
    Result<Person> delete(String dni);
}
