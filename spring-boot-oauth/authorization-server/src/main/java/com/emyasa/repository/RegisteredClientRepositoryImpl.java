package com.emyasa.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.stereotype.Repository;

// TODO: Implement actual repo implementation
@Repository
public class RegisteredClientRepositoryImpl implements RegisteredClientRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisteredClientRepositoryImpl.class);

    @Override
    public void save(RegisteredClient registeredClient) {
        LOGGER.info("[RegisteredClientRepositoryImpl][save]: " + registeredClient.getClientId());
    }

    @Override
    public RegisteredClient findById(String s) {
        LOGGER.info("[RegisteredClientRepositoryImpl][findById]: " + s);
        ClientSettings clientSettings = ClientSettings.builder()
                .requireAuthorizationConsent(true)
                .requireProofKey(false)
                .build();

        return RegisteredClient.withId("3cc506e8-c564-4869-8c76-ece5ae5f32cb")
                .clientId("articles-client")
                .clientSecret("{noop}secret")
                .clientSettings(clientSettings)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("http://127.0.0.1:8080/login/oauth2/code/articles-client-oidc")
                .redirectUri("http://127.0.0.1:8080/authorized")
                .scope(OidcScopes.OPENID)
                .scope("articles.read")
                .build();
    }

    @Override
    public RegisteredClient findByClientId(String s) {
        LOGGER.info("[RegisteredClientRepositoryImpl][findByClientId]: " + s);
        ClientSettings clientSettings = ClientSettings.builder()
                .requireAuthorizationConsent(true)
                .requireProofKey(false)
                .build();

        return RegisteredClient.withId("3cc506e8-c564-4869-8c76-ece5ae5f32cb")
                .clientId("articles-client")
                .clientSecret("{noop}secret")
                .clientSettings(clientSettings)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("http://127.0.0.1:8080/login/oauth2/code/articles-client-oidc")
                .redirectUri("http://127.0.0.1:8080/authorized")
                .scope(OidcScopes.OPENID)
                .scope("articles.read")
                .scope("articles.write")
                .scope("random")
                .build();
    }
}
