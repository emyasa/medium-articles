package com.emyasa;

import com.emyasa.domain.UserAccount;
import com.emyasa.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.UUID;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private RegisteredClientRepository registeredClientRepository;

    @Transactional
    @PostConstruct
    public void setupTestData() {
        UserAccount account = new UserAccount.Builder()
                .withUsername("username")
                .withPassword(new BCryptPasswordEncoder().encode("password"))
                .build();

        userAccountRepository.save(account);

        RegisteredClient registrationClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("registration-client")
                .clientSecret("secret")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope("client.create")
                .build();

        registeredClientRepository.save(registrationClient);

        ClientSettings clientSettings = ClientSettings.builder()
                .requireAuthorizationConsent(true)
                .requireProofKey(false)
                .build();

        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientSettings(clientSettings)
                .clientId("articles-client")
                .clientSecret("secret")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("http://127.0.0.1:8080/login/oauth2/code/articles-client-oidc")
                .scope(OidcScopes.OPENID)
                .scope("articles.read")
                .scope("articles.write")
                .build();

        registeredClientRepository.save(registeredClient);

    }
}
