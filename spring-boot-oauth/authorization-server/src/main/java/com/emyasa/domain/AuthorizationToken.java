package com.emyasa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.apache.commons.lang3.Validate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
public class AuthorizationToken {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private AuthorizationModel authorizationModel;

    @Column(nullable = false)
    @Type(type="text")
    private String token;

    private String tokenType;

    public String getToken() {
        return token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public AuthorizationModel getAuthorizationModel() {
        return authorizationModel;
    }

    public AuthorizationToken(AuthorizationModel authorizationModel, String token, String tokenType) {
        Validate.notNull(authorizationModel, "authorizationModel must not be null");
        Validate.notNull(token, "token must not be null");

        this.authorizationModel = authorizationModel;
        this.token = token;
        this.tokenType = tokenType;
    }

    protected AuthorizationToken() {}

}
