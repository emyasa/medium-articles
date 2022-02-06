package com.emyasa.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;

@Entity
public class AuthorizationConsentModel {

    @EmbeddedId
    private AuthorizationConsentId id;

    @Column(nullable = false)
    private byte[] serializedDomain;

    public OAuth2AuthorizationConsent getOAuth2AuthorizationConsent() {
        return SerializationUtils.deserialize(serializedDomain);
    }

    public AuthorizationConsentModel(OAuth2AuthorizationConsent authorizationConsent) {
        Validate.notNull(authorizationConsent, "authorizationConsent must not be null");

        this.id = new AuthorizationConsentId(authorizationConsent);
        this.serializedDomain = SerializationUtils.serialize(authorizationConsent);
    }

    protected AuthorizationConsentModel() {}
}
