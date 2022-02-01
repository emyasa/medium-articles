package com.emyasa.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.security.oauth2.core.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization.Token;

@Entity
public class AuthorizationModel {

    @Id
    private String id;

    @ElementCollection
    private Set<AuthorizationToken> tokens;

    @Column(nullable = false)
    @Lob
    private byte[] serializedDomain;

    public String getId() {
        return id;
    }

    public Set<AuthorizationToken> getTokens() {
        return tokens;
    }

    public byte[] getSerializedDomain() {
        return serializedDomain;
    }

    public OAuth2Authorization getOAuth2Authorization() {
        return SerializationUtils.deserialize(serializedDomain);
    }

    public AuthorizationModel(OAuth2Authorization oAuth2Authorization) {
        Validate.notNull(oAuth2Authorization, "oAuth2Authorization must not null");

        Token<OAuth2AuthorizationCode> oAuth2AuthorizationCodeToken = oAuth2Authorization.getToken(OAuth2AuthorizationCode.class);
        String authCodeTokenValue = Objects.nonNull(oAuth2AuthorizationCodeToken) ?
                oAuth2AuthorizationCodeToken.getToken().getTokenValue() : null;

        Object stateToken = oAuth2Authorization.getAttribute(OAuth2ParameterNames.STATE);
        String stateTokenValue = Objects.nonNull(stateToken) ? stateToken.toString() : null;


        this.id = oAuth2Authorization.getId();
        this.tokens = new HashSet<>();
        this.addToken(authCodeTokenValue, OAuth2ParameterNames.CODE);
        this.addToken(stateTokenValue, OAuth2ParameterNames.STATE);
        this.serializedDomain = SerializationUtils.serialize(oAuth2Authorization);
    }

    private void addToken(String token, String tokenType) {
        if (token != null) {
            AuthorizationToken authorizationToken = new AuthorizationToken(token, tokenType);
            this.tokens.add(authorizationToken);
        }
    }

    protected AuthorizationModel() {}
}
