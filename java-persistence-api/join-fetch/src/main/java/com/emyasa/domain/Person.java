package com.emyasa.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Entity
public class Person {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String firstName;
    private String lastName;

    @ElementCollection
    private List<Address> addresses;

    public List<Address> getAddresses() {
        return addresses;
    }

    // Required by JPA
    protected Person() {
    }
}
