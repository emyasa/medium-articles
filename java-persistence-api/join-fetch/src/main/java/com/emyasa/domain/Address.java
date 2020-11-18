package com.emyasa.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Address {

    private String houseNumber;
    private String addressLine;

    public String getAddressLine() {
        return addressLine;
    }

    // Required by JPA
    protected Address() {
    }
}
