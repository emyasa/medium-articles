package com.emyasa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "oauth2_authorization")
public class AuthorizationToken {

    @Id
    private String id;

    @Column(name = "access_token_value")
    private String token;

    @Column(name = "access_token_type")
    private String tokenType;

    protected AuthorizationToken() {}

}
