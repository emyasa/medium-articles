package com.emyasa.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, String> {

    @Query("SELECT DISTINCT person FROM Person person " + "JOIN FETCH person.addresses addresses")
    List<Person> retrieveAll();

}
