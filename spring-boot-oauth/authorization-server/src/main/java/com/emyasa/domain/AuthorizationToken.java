package com.emyasa.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.apache.commons.lang3.Validate;

@Embeddable
public class AuthorizationToken {

    @Column(nullable = false)
    private String token;

    private String tokenType;

    public String getToken() {
        return token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public AuthorizationToken(String token, String tokenType) {
        this.token = token;
        this.tokenType = tokenType;
    }

    protected AuthorizationToken() {}

}
