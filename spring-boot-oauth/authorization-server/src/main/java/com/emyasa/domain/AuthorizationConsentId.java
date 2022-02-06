package com.emyasa.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.apache.commons.lang3.Validate;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;

@Embeddable
public class AuthorizationConsentId implements Serializable {

    @Column(nullable = false)
    private String registeredClientId;

    @Column(nullable = false)
    private String principalName;

    public AuthorizationConsentId(OAuth2AuthorizationConsent authorizationConsent) {
        this(authorizationConsent.getRegisteredClientId(), authorizationConsent.getPrincipalName());
    }

    public AuthorizationConsentId(String registeredClientId, String principalName) {
        Validate.notEmpty(registeredClientId, "registeredClientId must not be empty");
        Validate.notEmpty(principalName, "principalName must not be empty");

        this.registeredClientId = registeredClientId;
        this.principalName = principalName;
    }

    protected AuthorizationConsentId() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthorizationConsentId that = (AuthorizationConsentId) o;

        if (!registeredClientId.equals(that.registeredClientId)) return false;
        return principalName.equals(that.principalName);
    }

    @Override
    public int hashCode() {
        int result = registeredClientId.hashCode();
        result = 31 * result + principalName.hashCode();
        return result;
    }
}
