package com.emyasa.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "authorization_token")
public class AuthorizationToken {

    @Id
    private String id;

    private String token;

    private String tokenType;

    protected AuthorizationToken() {}

}
