package com.emyasa;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
class Book {

    @Id
    private String id;

    public String getId() {
        return id;
    }
}
