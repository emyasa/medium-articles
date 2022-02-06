package com.emyasa.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization.Token;

@Entity
public class AuthorizationModel {

    @Id
    private String id;

    @OneToMany(mappedBy = "authorizationModel", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
    private Set<AuthorizationToken> tokens;

    @Column(nullable = false)
    private byte[] serializedDomain;

    public OAuth2Authorization getOAuth2Authorization() {
        return SerializationUtils.deserialize(serializedDomain);
    }

    public AuthorizationModel(OAuth2Authorization oAuth2Authorization) {
        Validate.notNull(oAuth2Authorization, "oAuth2Authorization must not null");

        this.id = oAuth2Authorization.getId();
        this.tokens = new HashSet<>();
        this.configureTokens(oAuth2Authorization);
        this.serializedDomain = SerializationUtils.serialize(oAuth2Authorization);
    }

    public void update(OAuth2Authorization oAuth2Authorization) {
        Validate.notNull(oAuth2Authorization, "oAuth2Authorization must not be null");

        this.configureTokens(oAuth2Authorization);
        this.serializedDomain = SerializationUtils.serialize(oAuth2Authorization);
    }

    private void configureTokens(OAuth2Authorization oAuth2Authorization) {
        Validate.notNull(oAuth2Authorization, "oAuth2Authorization must not be null");

        this.tokens.clear();
        Token<OAuth2AuthorizationCode> oAuth2AuthorizationCodeToken = oAuth2Authorization.getToken(OAuth2AuthorizationCode.class);
        String authCodeTokenValue = Objects.nonNull(oAuth2AuthorizationCodeToken) ?
                oAuth2AuthorizationCodeToken.getToken().getTokenValue() : null;

        Object stateToken = oAuth2Authorization.getAttribute(OAuth2ParameterNames.STATE);
        String stateTokenValue = Objects.nonNull(stateToken) ? stateToken.toString() : null;

        Token<OAuth2AccessToken> oAuth2AccessToken = oAuth2Authorization.getToken(OAuth2AccessToken.class);
        String accessTokenValue = Objects.nonNull(oAuth2AccessToken)
                ? oAuth2AccessToken.getToken().getTokenValue() : null;

        this.addToken(authCodeTokenValue, OAuth2ParameterNames.CODE);
        this.addToken(stateTokenValue, OAuth2ParameterNames.STATE);
        this.addToken(accessTokenValue, OAuth2AccessToken.TokenType.BEARER.getValue());
    }

    private void addToken(String token, String tokenType) {
        if (token != null) {
            AuthorizationToken authorizationToken = new AuthorizationToken(this, token, tokenType);
            this.tokens.add(authorizationToken);
        }
    }

    protected AuthorizationModel() {}
}
