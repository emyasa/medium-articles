package com.emyasa.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
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

        this.id = oAuth2Authorization.getId();
        this.tokens = new HashSet<>();
        this.addToken(oAuth2AuthorizationCodeToken, OAuth2ParameterNames.CODE);
        this.serializedDomain = SerializationUtils.serialize(oAuth2Authorization);
    }

    private void addToken(Token<?> oAuth2AuthorizationToken, String tokenType) {
        if (oAuth2AuthorizationToken != null) {
            OAuth2Token oAuth2Token = oAuth2AuthorizationToken.getToken();
            AuthorizationToken authorizationToken = new AuthorizationToken(oAuth2Token.getTokenValue(), tokenType);
            this.tokens.add(authorizationToken);
        }
    }

    protected AuthorizationModel() {}
}
